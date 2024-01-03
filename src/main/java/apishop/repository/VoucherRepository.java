package apishop.repository;

import apishop.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VoucherRepository extends MongoRepository<Voucher, String> {

//    Page<Voucher> findAllVoucher(Pageable pageable);


    @Query("{" +
            "  'expirationDate': { $gte: new Date() }," +
            "  'quantity': { $gt: 0 }," +
            "  '_id': { $nin: {" +
            "    $map: {" +
            "      input: {" +
            "        $filter: {" +
            "          input: '$voucherOfAccounts'," +
            "          as: 'voa'," +
            "          cond: {" +
            "            $eq: ['$$voa.account.id', ?1]" +
            "          }" +
            "        }" +
            "      }," +
            "      as: 'filteredVoa'," +
            "      in: '$$filteredVoa.voucher.id'" +
            "    }" +
            "  }}" +
            "}")
    Page<Voucher> findAllIsActived(Pageable pageable, String accountId);

    @Query("{" +
            "  'voucherOfAccounts': {" +
            "    $elemMatch: {" +
            "      'account.id': ?1," +
            "      'isUsed': false" +
            "    }" +
            "  }," +
            "  'expirationDate': { $gte: new Date() }" +
            "}")
    List<Voucher> findAllByVoucherOfAccountsAccountId(String accountId);
}
