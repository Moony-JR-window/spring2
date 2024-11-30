package wingbank.com.kh.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.DailyTransactionLog;
import wingbank.com.kh.model.TransactionType;

import java.util.Optional;

public interface DailyTransactionLogRepository extends JpaRepository<DailyTransactionLog, Integer> {

    Optional<DailyTransactionLog> findByAccountIdAndTransactionTypeId(Account account, TransactionType transactionType);
}
