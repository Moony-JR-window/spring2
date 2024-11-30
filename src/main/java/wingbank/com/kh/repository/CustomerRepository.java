package wingbank.com.kh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wingbank.com.kh.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Integer> {
//    @Query("SELECT a FROM Account a WHERE a.customer.customerId = :customerId")
//    List<Account> findAccountsByCustomerId(@Param("customerId") String customerId);
}
