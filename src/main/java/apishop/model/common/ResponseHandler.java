package apishop.model.common;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Lớp này tạo với mục đích lấy các thông tin trả về map vào API
// thông báo cho người dùng biết là đã thực hiện thành công hay thất bại
public class ResponseHandler {
    public static ResponseEntity<Object> response(HttpStatus status, Object data, boolean result){
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("result", result);
            if(data instanceof Page<?> page){
                map.put("page", page.getNumber());
                map.put("size", page.getSize());
                map.put("data", page.getContent());
                map.put("all-item", page.getTotalElements());
            } else {
                map.put("data", data);
            }
            return new ResponseEntity<>(map, status);
        } catch (Exception e){
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("result", false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<>(map, status);
        }
    }
}
