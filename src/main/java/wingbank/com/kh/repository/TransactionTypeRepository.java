package wingbank.com.kh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wingbank.com.kh.model.TransactionType;

public interface TransactionTypeRepository extends JpaRepository <TransactionType, Integer> {
}
