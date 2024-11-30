package wingbank.com.kh.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wingbank.com.kh.model.RecentTransaction;

import java.util.Optional;

public interface RecentTransactionRepository extends JpaRepository <RecentTransaction, Integer>  {

    @Query("SELECT rt FROM RecentTransaction rt JOIN rt.transactions t WHERE t.accountId = :accountId ORDER BY rt.accessTimestamp DESC")
    Page<RecentTransaction> findRecentTransactionsByAccountId(@Param("accountId") Integer account_id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM RecentTransaction rt WHERE rt.transactions.transactionId = :transactionId")
    int deleteByTransactionsTransactionId(@Param("transactionId") Integer transactionId);

}
