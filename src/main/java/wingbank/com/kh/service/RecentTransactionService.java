package wingbank.com.kh.service;


import org.springframework.data.domain.Page;
import wingbank.com.kh.dto.RecentSpecificDto;
import wingbank.com.kh.dto.RecentTransactionDto;
import wingbank.com.kh.model.RecentTransaction;
import wingbank.com.kh.model.Transaction;

public interface RecentTransactionService {

    RecentTransaction createRecentTransaction(RecentTransactionDto recentTransactionDto);
    Page<RecentSpecificDto> getRecentTransactionsByAccountId(Integer transactionId, int page, int limit);
    String DeleteRecentTransaction(Integer transactionId);
    RecentTransaction autoCreateRecentTransaction(Transaction transaction);
}
