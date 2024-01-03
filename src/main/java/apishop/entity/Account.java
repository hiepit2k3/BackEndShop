package  apishop.entity;

import apishop.util.enums.Role;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import apishop.util.enums.TypeAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "users")
public class Account implements UserDetails {
    @Id
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Binary image;
    private Date birthday;
    private Date lastAccessDate;
    private Boolean sex;
    private TypeAccount typeAccount;
    private String activatedToken;
    private Role role;

    @DBRef
    private Set<DeliveryAddress> deliveryAddresses;

    @DBRef
    private Set<VoucherOfAccount> voucherOfAccounts;

    @DBRef
    private Set<Order> orders;

    @DBRef
    private Set<Evaluate> evaluates;

    public Account(String accountId, String username, String email, Role value) {
        this.id = accountId;
        this.username = username;
        this.email = email;
        this.role = value;
    }

    public Account(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return typeAccount == TypeAccount.LOCKED ? false : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return typeAccount == TypeAccount.UNVERIFIED ? false : true;
    }
}
