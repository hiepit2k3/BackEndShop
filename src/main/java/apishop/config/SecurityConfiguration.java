package apishop.config;

import apishop.service.JwtService;
import apishop.service.UserDetailService;
//import edu.poly.duantotnghiep.oauth2.CustomOAuth2User;
//import edu.poly.duantotnghiep.service.UserDetailService;
//import edu.poly.duantotnghiep.oauth2.CustomOAuth2UserService;
//import edu.poly.duantotnghiep.util.enums.Role;
//import jakarta.mail.MessagingException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/*
 *  Lớp này sẽ cấu hình thư viện mã hóa mật khẩu là BCryptPasswordEncoder
 *  và cấu hình AuthenticationManager để Spring Security có thể xác thực thông tin người dùng.
 * Ngoài ra, lớp này cũng sẽ cấu hình SecurityFilterChain để xác thực token của người dùng.
 * Phân quyền truy cập cho các API
 * */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailService userDetailService;
//    private final CustomOAuth2UserService oauthUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)

//                .authorizeHttpRequests(
//                        request -> request
//                                .requestMatchers(
//                                        AUTH_PATH + "/**",
//                                        BRAND_PATH,
//                                        BRAND_PATH + "/id/**",
//                                        BRAND_PATH + "/name/**",
//                                        CATEGORY_PATH,
//                                        CATEGORY_PATH + "/id/**",
//                                        COLOR_PATH,
//                                        COLOR_PATH + "/id/**",
//                                        EVALUATE_PATH + "/id/**",
//                                        EVALUATE_PATH + "/product/**",
//                                        FEEDBACK_PATH + "/create",
//                                        PROBLEM_PATH,
//                                        PROBLEM_PATH + "/id/**",
//                                        PRODUCT_PATH + "/id/**",
//                                        PRODUCT_PATH + "/search",
//                                        PRODUCT_PATH,
//                                        STATISTIC_PATH + "/top-product",
//                                        VOUCHER_PATH + "/id/**",
//                                        VOUCHER_PATH + "/is-active",
//                                        DISCOUNT_PATH + "/id/**",
//                                        DISCOUNT_PATH + "/is-active"
//                                )
//                                .permitAll()
//                                .requestMatchers(
//                                        ACCOUNT_PATH + "/id",
//                                        "/upload/**",
//                                        DELIVERY_ADDRESS_PATH + "/id/**",
//                                        ORDER_PATH + "/id/**",
//                                        PAYMENT_PATH,
//                                        PAYMENT_PATH + "/id/**",
//                                        PAYMENT_PATH + "/name/**"
//                                )
//                                .authenticated()
//                                .requestMatchers(
//                                        DELIVERY_ADDRESS_PATH + "/create",
//                                        DELIVERY_ADDRESS_PATH + "/update/**",
//                                        DELIVERY_ADDRESS_PATH + "/delete/**",
//                                        DELIVERY_ADDRESS_PATH + "/account",
//                                        EVALUATE_PATH + "/create",
//                                        EVALUATE_PATH + "/update/**",
//                                        EVALUATE_PATH + "/account",
//                                        ORDER_PATH + "/create",
//                                        ORDER_PATH + "/return",
//                                        ORDER_PATH + "/payment",
//                                        VOUCHER_PATH + "/account",
//                                        VOUCHER_PATH + "/add"
//                                )
//                                .hasAuthority(Role.CUSTOMER.name())
//                                .requestMatchers(
//                                        ACCOUNT_PATH + "/update-profile",
//                                        ACCOUNT_PATH + "/change-password"
//                                )
//                                .hasAnyAuthority(Role.ADMIN.name(), Role.CUSTOMER.name())
//                                .requestMatchers(
//                                        ACCOUNT_PATH + "/create",
//                                        ACCOUNT_PATH + "/ADMIN",
//                                        ACCOUNT_PATH + "/ADMIN/**",
//                                        ACCOUNT_PATH + "/STAFF",
//                                        ACCOUNT_PATH + "/STAFF/**",
//                                        ACCOUNT_PATH + "/lock/**",
//                                        BRAND_PATH + "/create",
//                                        BRAND_PATH + "/update/**",
//                                        BRAND_PATH + "/delete/**",
//                                        CATEGORY_PATH + "/create",
//                                        CATEGORY_PATH + "/update/**",
//                                        CATEGORY_PATH + "/delete/**",
//                                        COLOR_PATH + "/create",
//                                        COLOR_PATH + "/update/**",
//                                        COLOR_PATH + "/delete/**",
//                                        DISCOUNT_PATH + "/create",
//                                        DISCOUNT_PATH + "/update/**",
//                                        DISCOUNT_PATH + "/delete/**",
//                                        HASHTAG_PATH + "/create",
//                                        HASHTAG_PATH + "/update/**",
//                                        HASHTAG_PATH + "/delete/**",
//                                        PAYMENT_PATH + "/create",
//                                        PAYMENT_PATH + "/update/**",
//                                        PAYMENT_PATH + "/delete/**",
//                                        PROBLEM_PATH + "/create",
//                                        PROBLEM_PATH + "/update/**",
//                                        PROBLEM_PATH + "/delete/**",
//                                        PRODUCT_PATH + "/create",
//                                        PRODUCT_PATH + "/update/**",
//                                        PRODUCT_PATH + "/delete/**",
//                                        STATISTIC_PATH + "/income",
//                                        VOUCHER_PATH + "/create",
//                                        VOUCHER_PATH + "/update/**",
//                                        VOUCHER_PATH + "/delete/**"
//                                )
//                                .hasAuthority(Role.ADMIN.name())
//                                .requestMatchers(
//                                        ACCOUNT_PATH + "/CUSTOMER",
//                                        ACCOUNT_PATH + "/CUSTOMER/**",
//                                        DELIVERY_ADDRESS_PATH,
//                                        DISCOUNT_PATH,
//                                        EVALUATE_PATH,
//                                        FEEDBACK_PATH,
//                                        FEEDBACK_PATH + "/id/**",
//                                        FEEDBACK_PATH + "/email/**",
//                                        FEEDBACK_PATH + "/phone/**",
//                                        FEEDBACK_PATH + "/status/**",
//                                        FEEDBACK_PATH + "/set-status/**",
//                                        FEEDBACK_PATH + "/problem/**",
//                                        HASHTAG_PATH,
//                                        HASHTAG_PATH + "/id/**",
//                                        HASHTAG_PATH + "/name/**",
//                                        ORDER_PATH,
//                                        ORDER_PATH + "/update/**",
//                                        STATISTIC_PATH + "/top-account",
//                                        VOUCHER_PATH
//                                        )
//                                .hasAnyAuthority(Role.ADMIN.name(), Role.STAFF.name())
//                                )
//                .exceptionHandling(
//                        exception -> exception
//                                .authenticationEntryPoint(
//                                        (request, response, ex) -> {
//                                            response.sendRedirect(URL_LOGIN_NON_TOKEN);
//                                        }
//                                )
//                                .accessDeniedHandler(
//                                        (request, response, ex) -> {
//                                            response.sendRedirect(URL_NOT_FOUND);
//                                        }
//                                )
//                )
//                .oauth2Login(
//                        oauth2 -> oauth2
//                                .userInfoEndpoint(
//                                        userInfo -> userInfo
//                                                .userService(oauthUserService)
//
//                                )
//                                .successHandler(new AuthenticationSuccessHandler() {
//
//                                                    @Override
//                                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                                                                        Authentication authentication) throws IOException {
//
//                                                        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
//                                                        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oauthUser);
//                                                        try {
//                                                            String token = oauthUserService.processOAuthPostLogin(customOAuth2User, passwordEncoder());
//                                                            response.sendRedirect(URL_LOGIN + token);
//                                                        } catch (MessagingException e) {
//                                                            throw new RuntimeException(e);
//                                                        }
//                                                    }
//                                                }
//                                )
//
//                )
                .sessionManagement(
                        manager -> manager
                                .sessionCreationPolicy(STATELESS)
                )
                .authenticationProvider(
                        authenticationProvider()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Phương thức này dùng để cấu hình AuthenticationManager
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("*");
            }
        };
    }
}
