package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.TransactionDto;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.Hashing;
import wingbank.com.kh.model.RecentTransaction;
import wingbank.com.kh.model.Transaction;
import wingbank.com.kh.repository.AccountRepository;
import wingbank.com.kh.repository.HashingRepository;
import wingbank.com.kh.repository.TransactionRepository;
import wingbank.com.kh.service.HashingService;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class HashingServiceImp implements HashingService {
    final HashingRepository hashingRepository;
    final TransactionRepository transactionRepository;
    final AccountRepository accountRepository;

    public HashingServiceImp(HashingRepository hashingRepository, TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.hashingRepository = hashingRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public String hashWithSHA256(BigDecimal Amount, LocalDate date, LocalDateTime timestamp) {
        try {
            String Hex = Amount + date.toString() + timestamp.toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(Hex.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            String fullHash = hexString.toString();
            return fullHash.length() > 10 ? fullHash.substring(0, 10) : fullHash;
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException("Error ", HttpStatus.BAD_REQUEST);
        }
    }

    public String processTransaction(TransactionDto transactionDto) {
        String hashedValue = this.hashWithSHA256(
                transactionDto.getTransactionAmount(),
                transactionDto.getTransactionDate(),
                LocalDateTime.now());

        log.info("Hashing result: {}", hashedValue);

        if (hashedValue == null) {
            throw new CustomException("Hashing is: ", HttpStatus.BAD_REQUEST);
        }
        return hashedValue;
    }

    @Override
    public String processRecentTransaction(BigDecimal recentAmount) {
        String hashedValue = this.hashWithSHA256(
                recentAmount,
                LocalDate.now(),
                LocalDateTime.now());

        log.info("Hashing result: {}", hashedValue);

        if (hashedValue == null) {
            throw new CustomException("Hashing is: ", HttpStatus.BAD_REQUEST);
        }
        return hashedValue;
    }


    @Override
    public TransactionDto getTransaction(String hash) {
        if (hash == null || hash.isEmpty()) {
            log.info("Hash is null or empty");
            throw new CustomException("Hash is null or empty", HttpStatus.BAD_REQUEST);
        }
        Hashing hashing = hashingRepository.findByHashing(hash);
        if(hashing==null){
            throw new CustomException("Not fund on Hashing is "+ hash,HttpStatus.NOT_FOUND);
        }
        Optional<Transaction> transaction = transactionRepository.findById(hashing.getTransaction().getTransactionId());
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionAmount(transaction.get().getTransactionAmount());
        transactionDto.setTransactionDate(transaction.get().getTransactionDate());
        transactionDto.setTransactionStatus(transaction.get().getTransactionStatus());
        transactionDto.setToReceiveNumber(transaction.get().getToReceiveNumber().getAccountNumber());
        transactionDto.setSenderId(transaction.get().getAccountId());
        transactionDto.setTransactionTypeId(transaction.get().getTransactionTypeId().getTransactionTypeId());
        transactionDto.setCurrencyToPay(transaction.get().getToReceiveNumber().getCurrency());
        return transactionDto;
    }


    @Override
    public void postHashing(Transaction transaction, String hex) {
        log.info("Hashing result: Service {}", hex);

        if (transaction == null || hex == null || hex.isEmpty()) {
            log.error("Transaction or hex value is null/empty");
            throw new CustomException("Invalid transaction or hex value provided", HttpStatus.BAD_REQUEST);
        }
        log.info("ID=============================" + transaction.getTransactionId());
        Hashing hashing = new Hashing();
        hashing.setHashing(hex);
        hashing.setTransaction(transaction);
        hashingRepository.save(hashing);

    }
    @Override
    public void postHashingRecent(RecentTransaction recentTransaction, String hex) {
        log.info("Hashing result: Service {}", hex);

        if (recentTransaction == null || hex == null || hex.isEmpty()) {
            log.error("Transaction or hex value is null/empty");
            throw new CustomException("Invalid transaction or hex value provided", HttpStatus.BAD_REQUEST);
        }
        log.info("ID=============================" + recentTransaction.getRecentTransactionId());
        Hashing hashing = new Hashing();
        hashing.setHashing(hex);
        hashing.setRecentTransaction(recentTransaction);
        hashing.setTransaction(recentTransaction.getTransactions());
        hashingRepository.save(hashing);

    }

}
