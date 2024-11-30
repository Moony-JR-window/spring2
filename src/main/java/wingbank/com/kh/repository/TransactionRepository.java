package wingbank.com.kh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wingbank.com.kh.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
