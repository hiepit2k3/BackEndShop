package apishop.service;

import org.springframework.stereotype.Service;

@Service
public interface EncodePassword {

    String encodePassword(String password);


    boolean matches(String password, String encodePassword);
}
