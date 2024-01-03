package apishop.service.impl;

import apishop.entity.Account;
import apishop.service.JwtService;
import apishop.util.enums.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import static apishop.util.expiration.ConstantsToken.*;

/*
 * Lớp này sẽ xử lý token
 * Có 2 chữ ký gồm chữ ký server và từ password người dùng
 * Vì tránh trường hợp người dùng vô lại link cũ để đổi pass
 * Nên set chữ ký là pass cũ của người dùng
 * */

@Service
public class JwtServiceImpl implements JwtService {
    // Đây là token của trang web, nên nó sẽ được lưu trong file application.properties
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    // Phương thức này sẽ trích xuất tên người dùng từ token
    @Override
    public Account extractAccount(String token, String password) {
        Claims claims = extractAllClaims(token, password);
        return getAccount(claims);
    }

    @Override
    public String extractUsername(String token) throws IOException {
        String[] jwtParts = token.split("\\.");
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = new String(Base64.decodeBase64(jwtParts[1]));
        JsonNode jsonNode = objectMapper.readTree(payload);
        return jsonNode.get("username").asText();
    }

    private Account getAccount(Claims claims) {
        String username = claims.get("username", String.class);
        String email = claims.get("email", String.class);
        String accountId = claims.get("id", String.class);
        Integer role = claims.get("role", Integer.class);

        return new Account(accountId, username, email, Role.values()[role]);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers, String password) {
        final Claims claims = extractAllClaims(token, password);
        return claimsResolvers.apply(claims);
    }

    // Lấy thông tin người dùng cuối cùng từ token
    private Claims extractAllClaims(String token, String password) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey(password)).build().parseClaimsJws(token)
                .getBody();
    }

    // Phương thức này sẽ tạo ra token từ thông tin người dùng
    @Override
    public String generateAccessToken(Account account) {
        return generateAccessToken(getExtraClaims(account));
    }

    @Override
    public String generateRefreshToken(Account account) {
        return generateRefreshToken(getExtraClaims(account));
    }

    @Override
    public String generateVerifyRegisterToken(Account account) {
        return generateVerifyRegisterToken(getExtraClaims(account));
    }

    @Override
    public String generateVerifyForgotPassToken(Account account) {
        return generateVerifyForgotPassToken(getExtraClaims(account), account.getPassword());
    }

    public Map<String, Object> getExtraClaims(Account account) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", account.getUsername());
        extraClaims.put("id", account.getId());
        extraClaims.put("email", account.getEmail());
        extraClaims.put("role", account.getRole().ordinal());
        return extraClaims;
    }

    private String generateVerifyForgotPassToken(Map<String, Object> extraClaims, String password) {
        return generateToken(extraClaims, VERIFY_FORGOT_PASS_TOKEN_EXPIRATION, password);
    }

    // Tạo ra token để thực hiện request trong vòng 1 ngày
    private String generateAccessToken(Map<String, Object> extraClaims) {
        return generateToken(extraClaims, ACCESS_TOKEN_EXPIRATION, null);
    }

    // Tạo ra token để lấy lại token trong vòng 7 ngày
    private String generateRefreshToken(Map<String, Object> extraClaims) {
        return generateToken(extraClaims, REFRESH_TOKEN_EXPIRATION, null);
    }

    // Tạo ra token để xác thực email trong vòng 2h
    private String generateVerifyRegisterToken(Map<String, Object> extraClaims) {
        return generateToken(extraClaims, VERIFY_REGISTER_TOKEN_EXPIRATION, null);
    }


    private String generateToken(Map<String, Object> extraClaims, Long expiration, String password) {
        return Jwts.builder().setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                // Đoạn này sẽ tạo ra chữ ký cho token của người dùng bằng thuật toán HS256
                .signWith(getSigningKey(password), SignatureAlgorithm.HS256).compact();
    }

    /* Phương thức này sẽ kiểm tra xem token có hợp lệ hay không
     *  Thông qua tên người dùng và thời gian hết hạn của token
     */
    @Override
    public boolean isTokenValid(String token, String password) {
        try {
            return !isTokenExpired(token, password);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "EJE");
        }
    }

    @Override
    public Account extractAndValid(String token, String password) {
        isTokenValid(token, password);
        return extractAccount(token, password);
    }

    // Phương thức này sẽ kiểm tra xem token có hết hạn hay không
    private boolean isTokenExpired(String token, String password) {
        return extractExpiration(token, password).before(new Date());
    }

    private Date extractExpiration(String token, String password) {
        return extractClaim(token, Claims::getExpiration, password);
    }

    // Phương thức này sẽ trả về đối tượng Key dùng để tạo ra token
    private Key getSigningKey(String password) {
        if (password == null) {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }
        byte[] keyBytes = Decoders.BASE64.decode(Encoders.BASE64.encode(password.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
