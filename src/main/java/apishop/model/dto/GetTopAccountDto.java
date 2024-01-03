package apishop.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GetTopAccountDto {

    private Long id;
    private String fullName;
    private String image;
    private String username;
    private String email;
    private Double totalOrders;
    private Integer totalProducts;



}
