package wingbank.com.kh.service;

import wingbank.com.kh.dto.TransactionDto;
import wingbank.com.kh.model.RecentTransaction;
import wingbank.com.kh.model.Transaction;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface HashingService {

    String hashWithSHA256(BigDecimal Amount, LocalDate date, LocalDateTime timestamp) throws NoSuchAlgorithmException;

    String processTransaction(TransactionDto transactionDto) throws NoSuchAlgorithmException;

    String processRecentTransaction(BigDecimal recentAmount) throws NoSuchAlgorithmException;

    TransactionDto getTransaction(String hash);

    void postHashing(Transaction transaction, String hash);

    void postHashingRecent(RecentTransaction recentTransaction, String hash);
}
