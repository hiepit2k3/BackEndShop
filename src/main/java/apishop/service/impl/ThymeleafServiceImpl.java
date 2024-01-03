package apishop.service.impl;

import apishop.entity.Order;
import apishop.model.dto.OrderDetailDto;
import apishop.service.ThymeleafService;
import apishop.util.enums.TypeOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Calendar;
import java.util.List;

import static apishop.util.api.frontend.FrontEndApis.*;
import static apishop.util.mail.ConstantsMail.Mail.Template.*;
import static apishop.util.mail.ConstantsMail.Thymeleaf.*;

@Service
@RequiredArgsConstructor
public class ThymeleafServiceImpl implements ThymeleafService {

    private static TemplateEngine templateEngine;

    static {
        templateEngine = emailTemplateEngine();
    }

    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }

    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Override
    public String getVerifyEmailContent(String token, String api) {
        final Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("url", api);
        return templateEngine.process(TEMPLATE_VERIFY_NAME, context);
    }

    @Override
    public String getWelcomeEmailContent(String username) {
        final Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("url", BASE_URL_FRONT_END);
        return templateEngine.process(TEMPLATE_WELCOME_NAME, context);
    }

    @Override
    public String getChangePassEmailContent(String username) {
        final Context context = new Context();
        context.setVariable("url", BASE_URL_FRONT_END);
        context.setVariable("username", username);
        return templateEngine.process(TEMPLATE_CHANGE_PASS_NAME, context);
    }

    @Override
    public String getOrderEmailContent(String email,
                                       Order order,
                                       List<OrderDetailDto> orderDetailsDto,
                                       Double discount) {
        Calendar deliveryDate = Calendar.getInstance();
        deliveryDate.setTime(order.getPurchaseDate());
        deliveryDate.add(Calendar.DATE, 4);

        double subtotal = orderDetailsDto.stream()
                .mapToDouble(orderDetail ->
                        orderDetail.getQuantity() *
                                (orderDetail.getPrice() -
                                        (
                                                orderDetail.getDiscount() == null ? 0 : orderDetail.getDiscount()
                                        )
                                )
                )
                .sum();

        final Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("expected", deliveryDate.getTime());
        context.setVariable("orderDetails", orderDetailsDto);
        context.setVariable("email", email);
        context.setVariable("subtotal", subtotal);
        context.setVariable("discount", discount);
        context.setVariable("url", URL_ORDER_USER);
        return templateEngine.process(TEMPLATE_ORDER_NAME, context);
    }

    @Override
    public String updateOrderEmailContent(String email,
                                          String orderId,
                                          TypeOrder typeOrder) {
        final Context context = new Context();
        context.setVariable("orderId", orderId);
        context.setVariable("typeOrder", typeOrder.getEnglishDescription());
        context.setVariable("email", email);
        context.setVariable("url", URL_ORDER_USER);
        return templateEngine.process(TEMPLATE_UPDATE_ORDER_NAME, context);
    }
}
