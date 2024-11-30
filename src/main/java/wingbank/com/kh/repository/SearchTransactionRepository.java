package wingbank.com.kh.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wingbank.com.kh.dto.FilterDto;
import wingbank.com.kh.model.SearchTransaction;

import java.util.List;

public interface SearchTransactionRepository extends JpaRepository<SearchTransaction, Integer> {

//    @Query("SELECT rt FROM SearchTransaction rt JOIN rt.transaction t WHERE t.accountId = :accountId AND t.transactionAmount BETWEEN :minAmount AND :maxAmount ORDER BY rt.transactionDate DESC")
//    List<SearchTransaction> findRecentTransactionsByAccountIdAndAmountRange(
//            @Param("account_id") Integer account_id,
//            @Param("minAmount") BigDecimal minAmount,
//            @Param("maxAmount") BigDecimal maxAmount);

//    @Query("SELECT rt FROM SearchTransaction rt WHERE rt.account.accountId = :accountId AND rt.transactionAmount BETWEEN :minAmount AND :maxAmount  ORDER BY rt.transactionDate DESC")
//    List<SearchTransaction> findRecentTransactionsByAccountIdAndAmountRange(
//            @Param("accountId") Integer accountId,
//            @Param("minAmount") BigDecimal minAmount,
//            @Param("maxAmount") BigDecimal maxAmount
//            );

    @Query("SELECT rt FROM SearchTransaction rt WHERE rt.account.accountId = :#{#filterDto.accountId} " +
            "AND rt.transactionAmount BETWEEN :#{#filterDto.minAmount} AND :#{#filterDto.maxAmount} " +
            "AND rt.transactionDate BETWEEN :#{#filterDto.startDate} AND :#{#filterDto.endDate} " +
            "ORDER BY rt.transactionDate DESC")
    List<SearchTransaction> findRecentTransactionsByFilterDto(@Param("filterDto") FilterDto filterDto);
//    Success



//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO search_transaction (transaction_id, account_id, transaction_type_id, transaction_amount, transaction_date) " +
//            "SELECT transaction_id, account_id, transaction_type_id, transaction_amount, transaction_date " +
//            "FROM Transaction", nativeQuery = true)
//    void populateSearchTransactions();
//    //Success



    @Modifying
    @Transactional
    @Query(value = "INSERT INTO search_transaction (transaction_id, account_id, transaction_type_id, transaction_amount, transaction_date) " +
            "SELECT t.transaction_id, t.account_id, t.transaction_type_id, t.transaction_amount, t.transaction_date " +
            "FROM Transaction t " +
            "LEFT JOIN search_transaction st ON t.transaction_id = st.transaction_id " +
            "WHERE st.transaction_id IS NULL", nativeQuery = true)
    void populateSearchTransactions();


//    @Query(name = "SearchTransaction.findRecentTransactionsByFilterDto")
//    List<SearchTransaction> findRecentTransactionsByFilterDto(
//            @Param("account_id") Integer account_id,
//            @Param("minAmount") BigDecimal minAmount,
//            @Param("maxAmount") BigDecimal maxAmount,
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate
//    );







//    @Query("SELECT st FROM SearchTransaction st JOIN st.transactionType tt " +
//            "WHERE st.account.accountId = :accountId AND tt.transactionName = :transactionName " +
//            "ORDER BY st.transactionDate DESC")
//    List<SearchTransaction> findByAccountIdAndTransactionTypeName(
//            @Param("accountId") Integer accountId,
//            @Param("transactionName") String transactionName);

//    @Query("SELECT st FROM SearchTransaction st " +
//            "WHERE st.account.accountId = :accountId " +
//            "AND st.transaction.transactionTypeId = :transactionName " +
//            "ORDER BY st.transactionDate DESC")
//    List<SearchTransaction> findByAccountIdAndTransactionTypeName(
//            @Param("accountId") Integer accountId,
//            @Param("transactionName") String transactionName);


//    @Query("SELECT st FROM SearchTransaction st " +
//            "JOIN st.transaction t " +
//            "JOIN st.transaction_type tt " +
//            "WHERE st.account.account_id = :account_id " +
//            "AND tt.transaction_name = :transaction_name ")
//    List<SearchTransaction> findByAccountIdAndTransactionTypeName(
//            @Param("account_id") Integer account_id,
//            @Param("transaction_name") String transaction_name);

    @Query("SELECT st FROM SearchTransaction st " +
            "JOIN st.transaction t " +
            "JOIN st.transactionType tt " +
            "WHERE st.account.accountId = :account_id " +
            "AND LOWER(tt.transactionName) = LOWER(:transaction_name)")
    List<SearchTransaction> findByAccountIdAndTransactionTypeName(
            @Param("account_id") Integer account_id,
            @Param("transaction_name") String transaction_name);


}
