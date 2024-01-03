package apishop.util.api.frontend;

public class FrontEndApis {
    public static final String BASE_URL_FRONT_END = "http://127.0.0.1:5173/";

    public static final String URL_LOGIN = BASE_URL_FRONT_END + "login?token=";

    public static final String URL_FOR_GOT_PASS = BASE_URL_FRONT_END + "enterNewPW?token=";

    public static final String URL_ORDER_USER = BASE_URL_FRONT_END + "user/order";

    public static final String URL_ADMIN = BASE_URL_FRONT_END + "admin";


    public static final String URL_LOGIN_NON_TOKEN = BASE_URL_FRONT_END + "login";

    public static final String URL_NOT_FOUND = BASE_URL_FRONT_END + "not-found";


}
