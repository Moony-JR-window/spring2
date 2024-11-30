package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.RecentSpecificDto;
import wingbank.com.kh.dto.RecentTransactionDto;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.RecentTransaction;
import wingbank.com.kh.model.Transaction;
import wingbank.com.kh.repository.AccountRepository;
import wingbank.com.kh.repository.HashingRepository;
import wingbank.com.kh.repository.RecentTransactionRepository;
import wingbank.com.kh.repository.TransactionRepository;
import wingbank.com.kh.service.HashingService;
import wingbank.com.kh.service.PasswordEncryption;
import wingbank.com.kh.service.RecentTransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class RecentTransactionServiceImp implements RecentTransactionService {

    final AccountRepository accountRepository;
    final RecentTransactionRepository recentTransactionRepository;
    final TransactionRepository transactionRepository;
    final HashingService hashingService;
    final HashingRepository hashingRepository;
    final PasswordEncryption passwordEncryption;

    public RecentTransactionServiceImp(AccountRepository accountRepository, RecentTransactionRepository recentTransactionRepository,
                                       TransactionRepository transactionRepository, HashingServiceImp hashingServiceImp,
                                       HashingService hashingService, HashingRepository hashingRepository, PasswordEncryption passwordEncryption) {
        this.accountRepository = accountRepository;
        this.recentTransactionRepository = recentTransactionRepository;
        this.transactionRepository = transactionRepository;
        this.hashingService = hashingService;
        this.hashingRepository = hashingRepository;
        this.passwordEncryption = passwordEncryption;
    }

    @Override
    public RecentTransaction createRecentTransaction(RecentTransactionDto recentTransactionDto) {
        try {
            Transaction transaction = transactionRepository.findById(recentTransactionDto.getTransactionId()).get();

            Account account = accountRepository.findById(transaction.getAccountId())
                    .orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));
            CheckPassword(account.getAccountId(), recentTransactionDto.getPassword());
            Account accountToPay = accountRepository.findByAccount_number(transaction.getToReceiveNumber().getAccountNumber());
            if (accountToPay.getCurrency().equals("USD")) {
                log.info("to pay USD =============== ");
                if (account.getCurrency().equals("USD")) {
                    log.info("from {} ============== ", account.getCurrency());
                    BigDecimal toPayAccountBalance = accountToPay.getBalance().add(transaction.getTransactionAmount());
                    accountToPay.setBalance(toPayAccountBalance);
                    BigDecimal subBalance = subBalance(account.getBalance(), transaction.getTransactionAmount());
                    if (transaction.getTransactionAmount().compareTo(account.getBalance()) > 0) {
                        log.info("error");
                        throw new CustomException("Not Balance ", HttpStatus.BAD_REQUEST);
                    }
                    account.setBalance(subBalance);
                    accountRepository.save(accountToPay);
                    accountRepository.save(account);
                } else if (account.getCurrency().equals("KHR")) {
                    log.info("from = {} ============== ", account.getCurrency());
                    BigDecimal KHR = new BigDecimal("4000");
                    BigDecimal calculateBalance = transaction.getTransactionAmount().multiply(KHR);
                    log.info("===== sub Money Total current = {} ", calculateBalance);
                    BigDecimal toPayAccountBalance = accountToPay.getBalance().add(transaction.getTransactionAmount()); // Mean Total add to Pay
                    BigDecimal subBalance = subBalance(account.getBalance(), calculateBalance);
                    if (calculateBalance.compareTo(account.getBalance()) > 0) {
                        throw new CustomException("Not Balance ", HttpStatus.BAD_REQUEST);
                    }
                    accountToPay.setBalance(toPayAccountBalance);
                    account.setBalance(subBalance);
                    accountRepository.save(accountToPay);
                    accountRepository.save(account);
                }
            } else if (accountToPay.getCurrency().equals("KHR")) {
                log.info("to pay KHR =============== ");
                if (account.getCurrency().equals("KHR")) {
                    log.info("from = {} ============== ", account.getCurrency());
                    BigDecimal toPayAccountBalance = accountToPay.getBalance().add(transaction.getTransactionAmount()); // Mean Total add to Pay
                    log.info("===== add Money Total toPay = {} ", toPayAccountBalance);
                    if (transaction.getTransactionAmount().compareTo(account.getBalance()) > 0) {
                        log.error("Transaction amount {} exceeds account balance {} for account ID: {}",
                                transaction.getTransactionAmount(), account.getBalance(), account.getAccountId());
                        throw new CustomException("Insufficient balance to perform the transaction", HttpStatus.BAD_REQUEST);
                    }
                    BigDecimal currentBalance = subBalance(account.getBalance(), transaction.getTransactionAmount()); //Sub Balance
                    account.setBalance(currentBalance);
                    accountToPay.setBalance(toPayAccountBalance);
                    accountRepository.save(accountToPay);
                    accountRepository.save(account);
                } else if (account.getCurrency().equals("USD")) {
                    log.info("from = {} ============== ", account.getCurrency());
                    BigDecimal KHR = new BigDecimal("4000");
                    BigDecimal calculateBalance = transaction.getTransactionAmount().divide(KHR, 2, BigDecimal.ROUND_HALF_UP);
                    log.info("===== sub Money Total currentAcc = {} ", calculateBalance);
                    BigDecimal toPayAccountBalance = accountToPay.getBalance().add(transaction.getTransactionAmount());
                    accountToPay.setBalance(toPayAccountBalance);
                    if (calculateBalance.compareTo(account.getBalance()) > 0) {
                        log.info("error{} ", calculateBalance);
                        throw new CustomException("Not Balance ", HttpStatus.BAD_REQUEST);
                    }
                    BigDecimal subBalance = subBalance(account.getBalance(), calculateBalance);
                    account.setBalance(subBalance);
                    accountRepository.save(accountToPay);
                    accountRepository.save(account);
                }

            }
            log.info("Creating new RecentTransaction");
            transactionRepository.save(transaction);
            return this.autoCreateRecentTransaction(transaction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Page<RecentSpecificDto> getRecentTransactionsByAccountId(Integer accountId, int page, int limit) {
        try {
            Page<RecentTransaction> recentTransactions = recentTransactionRepository.findRecentTransactionsByAccountId(accountId, PageRequest.of(page, limit));
            Account account = accountRepository.findById(accountId).orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));
            if (recentTransactions.isEmpty()) {
                log.info("Not found Account");
                throw new CustomException(" Not Found on Account ", HttpStatus.NOT_FOUND);
            }
            Page<RecentSpecificDto> recentSpecifics = new PageImpl<>(
                    recentTransactions.stream()
                            .map(recentTransaction -> RecentSpecificDto.Mapper(recentTransaction.getTransactions(), account))
                            .toList(), // Collect the Stream into a List
                    PageRequest.of(page, limit), // Include the current page and limit details
                    recentTransactions.getTotalElements() // Retain the total count from the original Page
            );

            return recentSpecifics;
        } catch (RuntimeException e) {
            log.error("Error on Service Get Recent Transactions {} ", e.getMessage());
            throw new CustomException("Not Fund Data ", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public String DeleteRecentTransaction(Integer transactionId) {
        try {
            hashingRepository.deleteByRecentTransactionId(transactionId);
            log.info("Attempting to delete transaction with ID: {}", transactionId);
            int rowsDeleted = recentTransactionRepository.deleteByTransactionsTransactionId(transactionId);
            if (rowsDeleted == 0) {
                throw new CustomException("No records deleted. Transaction ID may not exist.", HttpStatus.NOT_FOUND);
            }
            return "success";
        } catch (Exception e) {
            log.error("Error deleting transaction with ID: {}", transactionId, e);
            throw new CustomException("Error during deletion operation", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RecentTransaction autoCreateRecentTransaction(Transaction transaction) {
        RecentTransaction recentTransaction = RecentTransactionDto.toEntity(transaction);
        recentTransaction.setAccessTimestamp(LocalDateTime.now());
        recentTransaction.setTransactions(transaction);
        recentTransactionRepository.save(recentTransaction);
        return recentTransaction;
    }

    public BigDecimal subBalance(BigDecimal totalAmount, BigDecimal toPay) {
        BigDecimal currentBalance = totalAmount.subtract(toPay);
        return currentBalance;
    }

    public String CheckPassword(Integer accountId, String password) {

        Account account = accountRepository.findById(accountId).get();
        if (account == null) {
            throw new CustomException("Account not found", HttpStatus.NOT_FOUND);
        }
        boolean encrypt = passwordEncryption.verifyPassword(password, account.getPassword());
        if (encrypt) {
            return "success";
        }
        throw new CustomException("Password not correct", HttpStatus.BAD_REQUEST);
    }

}
