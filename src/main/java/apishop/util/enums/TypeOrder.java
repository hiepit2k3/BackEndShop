package apishop.util.enums;

public enum TypeOrder {
    PENDING("Đơn hàng đang chờ xử lý", "waiting for processing!"),
    WAIT_TO_PAY("Đơn hàng đang chờ thanh toán", "waiting for payment!"),
    PROCESSING("Đơn hàng đang được xử lý", "has processing!"),
    DELIVERING("Đơn hàng đang được vận chuyển", "has delivering!"),
    SUCCESSFUL("Đơn hàng đã thành công", "has successful!"),
    CANCELLED("Đơn hàng đã bị hủy", "has cancelled!"),
    RETURNED("Đơn hàng đã được trả lại", "has returned!");

    private final String vietnameseDescription;
    private final String englishDescription;

    TypeOrder(String vietnameseDescription, String englishDescription) {
        this.vietnameseDescription = vietnameseDescription;
        this.englishDescription = englishDescription;
    }

    public String getVietnameseDescription() {
        return vietnameseDescription;
    }

    public String getEnglishDescription() {
        return englishDescription;
    }
}
