package apishop.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class OrderDetailDto {

    private String id;
    @NonNull
    private String name;
    @NonNull
    private Integer quantity;
    @NonNull
    private Double price;
    @NonNull
    private String image;
    @NonNull
    private String size;
    @NonNull
    private String color;
    private Double discount;
    private Boolean isEvaluate;
    private String orderId;
    @NonNull
    private String productVariantId;
    private String productId;
}
