package apishop.util.expiration;

public class ConstantsToken {

    public static final Long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days
    public static final Long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24; // 1 days
    public static final Long VERIFY_REGISTER_TOKEN_EXPIRATION = 1000L * 60 * 60 * 2; // 2 hours
    public static final Long VERIFY_FORGOT_PASS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 2; // 2 hours
}
