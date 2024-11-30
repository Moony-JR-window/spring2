package wingbank.com.kh.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.UnlockType;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.accountNumber = ?1")
    Account findByAccount_number(Integer account_number);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.unlockTypeId = :unlockType WHERE a.accountId = :accountId")
    void updateUnlockTypeByAccountId(@Param("unlockType") UnlockType unlockType,
                                     @Param("accountId") Integer accountId);




}
