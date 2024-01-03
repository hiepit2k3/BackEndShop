package apishop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// lớp này nhằm để thu gọn code vì page, size,... sử dụng nhiều
public class SearchCriteria {
    private Integer page;
    private Integer size;
    private String columSort;

}
