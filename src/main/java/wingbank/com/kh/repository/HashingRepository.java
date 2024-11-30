package wingbank.com.kh.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import wingbank.com.kh.model.Hashing;

public interface HashingRepository extends JpaRepository<Hashing, Integer> {


    Hashing findByHashing(String hashing);
    @Modifying
    @Transactional
    @Query("DELETE FROM Hashing h WHERE h.recentTransaction.transactions.transactionId = :recentTransactionId")
    int deleteByRecentTransactionId(Integer recentTransactionId);

}
