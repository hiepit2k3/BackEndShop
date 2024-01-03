package apishop.service.impl;

import apishop.entity.Order;
import apishop.model.dto.OrderDetailDto;
import apishop.service.MailService;
import apishop.service.ThymeleafService;
import apishop.util.enums.TypeOrder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static apishop.util.mail.ConstantsMail.Mail.CONTENT_TYPE_TEXT_HTML;
import static apishop.util.mail.ConstantsMail.Mail.Subject.*;

@Service
@RequiredArgsConstructor
public class MailerServiceImpl implements MailService {


    private final ThymeleafService thymeleafService;

    private final Message message;

    @Override
    public void sendVerifyEmail(String receiverEmail, String token, String api) throws MessagingException {
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[]{new InternetAddress(receiverEmail)});

        message.setSubject(VERIFY_EMAIL_SUBJECT);
        message.setContent(thymeleafService.getVerifyEmailContent(token, api), CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
    }

    @Override
    public void sendWelcomeEmail(String receiverEmail, String username) throws MessagingException {
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[]{new InternetAddress(receiverEmail)});

        message.setSubject(WELCOME_EMAIL_SUBJECT);
        message.setContent(thymeleafService.getWelcomeEmailContent(username), CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
    }

    @Override
    public void sendOrderEmail(String receiverEmail,
                               Order order,
                               List<OrderDetailDto> orderDetailsDto,
                               Double discount) throws MessagingException {
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[]{new InternetAddress(receiverEmail)});

        message.setSubject(ORDER_EMAIL_SUBJECT);
        message.setContent(thymeleafService
                .getOrderEmailContent(
                        receiverEmail, order, orderDetailsDto, discount),
                        CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
    }

    @Override
    public void sendChangePassEmail(String receiverEmail, String username) throws MessagingException {
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[]{new InternetAddress(receiverEmail)});

        message.setSubject(CHANGE_PASS_EMAIL_SUBJECT);
        message.setContent(thymeleafService
                .getChangePassEmailContent(username),
                CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
    }

    @Override
    public void sendUpdateOrderEmail(String receiverEmail, String orderId, TypeOrder typeOrder) throws MessagingException {
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[]{new InternetAddress(receiverEmail)});
        message.setSubject(UPDATE_ORDER_EMAIL_SUBJECT);
        message.setContent(thymeleafService
                .updateOrderEmailContent(receiverEmail, orderId, typeOrder),
                CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
    }


}