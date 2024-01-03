package apishop.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface VnPayService {

    String create(Integer total, String orderId, HttpServletRequest request) throws UnsupportedEncodingException;

    int orderReturn(HttpServletRequest request);
}
