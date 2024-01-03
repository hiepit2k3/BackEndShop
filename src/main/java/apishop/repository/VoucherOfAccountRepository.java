package apishop.repository;

import apishop.entity.VoucherOfAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface    VoucherOfAccountRepository extends MongoRepository<VoucherOfAccount, String> {
    Page<VoucherOfAccount> findAllByAccountId(String accountId, Pageable pageable);

    Optional<VoucherOfAccount> findByAccountIdAndVoucherId(String accountId, String voucherId);
}
