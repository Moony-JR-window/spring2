package wingbank.com.kh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.PaymentLimit;
import wingbank.com.kh.model.TransactionType;

import java.util.Optional;

public interface PaymentLimitRepository extends JpaRepository<PaymentLimit, Integer> {
    Optional<PaymentLimit> findByAccountIdAndTransactionType(Account account, TransactionType transactionType);

}
