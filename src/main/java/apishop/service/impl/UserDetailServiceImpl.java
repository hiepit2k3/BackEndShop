package apishop.service.impl;

import apishop.repository.AccountRepository;
import apishop.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final AccountRepository accountRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String usernameOrEmail) {
                return accountRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("Account not found"));
            }
        };
    }
}
