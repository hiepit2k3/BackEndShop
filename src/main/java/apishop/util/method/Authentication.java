package apishop.util.method;

import apishop.entity.Account;
import apishop.exception.entity.EntityNotFoundException;
import apishop.exception.core.ArchitectureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authentication {

    public static Account getAccount() throws ArchitectureException {
        // Lấy thông tin người dùng từ SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            // Lấy thông tin người dùng từ đối tượng authenticationToken
        Account account = (Account) authenticationToken.getPrincipal();

        if (account == null) throw new EntityNotFoundException();

        return account;
    }
}
