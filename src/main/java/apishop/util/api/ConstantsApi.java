package apishop.util.api;


public class ConstantsApi {

    public static final String NAME_PROJECT = "/shop";

    public static final String BASE_URL = "/api" + NAME_PROJECT;

    public static final class Discount {
        public static final String DISCOUNT_PATH = BASE_URL + "/discount";
    }

    public static final class Product{
        public static final String PRODUCT_PATH = BASE_URL + "/product";
    }

    public static final class Category {
        public static final String CATEGORY_PATH = BASE_URL + "/category";
    }
    public static final class Feedback {
        public static final String FEEDBACK_PATH = BASE_URL + "/feedback";
    }

    public static final class Account {
        public static final String ACCOUNT_PATH = BASE_URL + "/account";
    }
    public static final class VoucherOfAccount {
        public static final String VOUCHER_OF_ACCOUNT_PATH = BASE_URL + "/voucherOfAccount";
    }
    public static final class Evaluate {
        public static final String EVALUATE_PATH = BASE_URL + "/evaluate";
    }
    public static final class DeliveryAddress {
        public static final String DELIVERY_ADDRESS_PATH = BASE_URL + "/deliveryAddress";
    }
    public static final class Order {
        public static final String ORDER_PATH = BASE_URL + "/order";
    }

    public static final class Voucher {
        public static final String VOUCHER_PATH = BASE_URL + "/voucher";
    }

    public static final class Color {
        public static final String COLOR_PATH = BASE_URL + "/color";
    }

    public static final class Payment {
        public static final String PAYMENT_PATH = BASE_URL + "/payment";
    }

    public static final class Brand {
        public static final String BRAND_PATH = BASE_URL + "/brand";
    }

    public static final class Hashtag {
        public static final String HASHTAG_PATH = BASE_URL + "/hashtag";
    }

    public static final class Auth {
        public static final String AUTH_PATH = BASE_URL + "/auth";
    }
    public static final class Problem{
        public static final String PROBLEM_PATH = BASE_URL + "/problem";
    }

    public static final class Statistic {
        public static final String STATISTIC_PATH = BASE_URL + "/statistic";
    }
    public static final class CartItem{
        public static final String CART_ITEM_PATH = BASE_URL + "/cart-item";
    }
}
