package apishop.repository;

import apishop.entity.Account;
import apishop.util.enums.Role;
import apishop.util.enums.TypeAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByUsernameOrEmail(String username, String email);

    Optional<Account> findByRoleAndEmailOrUsername(Role role, String username, String email);

    Optional<Account> findByPhoneNumber(String phone);

    Page<Account> findAllByRoleAndFullNameContains(Role role, String fullName, Pageable pageable);


    Optional<Account> findByEmailOrUsernameOrPhoneNumber(String email, String username, String phoneNumber);

    @Query("{ 'role': ?0 }")
    Page<Account> findAllByRole(Role role, Pageable pageable);
    Optional<Account> findById(String id);

//    Object[] getTopAccounts(@Param("TopCount") Integer topCount);
    Optional<Account> findByEmail(String email);

}
