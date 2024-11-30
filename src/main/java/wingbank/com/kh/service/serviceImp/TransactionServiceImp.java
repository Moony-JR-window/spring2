package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.DailyTransactionLogDto;
import wingbank.com.kh.dto.TransactionDto;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.*;
import wingbank.com.kh.repository.AccountRepository;
import wingbank.com.kh.repository.HashingRepository;
import wingbank.com.kh.repository.TransactionRepository;
import wingbank.com.kh.repository.TransactionTypeRepository;
import wingbank.com.kh.service.PasswordEncryption;
import wingbank.com.kh.service.RecentTransactionService;
import wingbank.com.kh.service.TransactionService;

import java.math.BigDecimal;

@Slf4j
@Service
public class TransactionServiceImp implements TransactionService {

    final AccountRepository accountRepository;
    final TransactionRepository transactionRepository;
    final TransactionTypeRepository transactionTypeRepository;
    final HashingServiceImp hashingService;
    final HashingRepository hashingRepository;
    final RecentTransactionService recentTransactionService;
    final PasswordEncryption passwordEncryption;
    final PaymentLimitImp paymentLimitImp;
    final DailyTransactionLogServiceImp dailyTransactionLogServiceImp;

    public TransactionServiceImp(AccountRepository accountRepository, TransactionRepository transactionRepository,
                                 TransactionTypeRepository transactionTypeRepository, HashingServiceImp hashingService,
                                 HashingRepository hashingRepository, RecentTransactionService recentTransactionService,
                                 PasswordEncryption passwordEncryption,
                                 PaymentLimitImp paymentLimitImp,
                                 DailyTransactionLogServiceImp dailyTransactionLogServiceImp) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.hashingService = hashingService;
        this.hashingRepository = hashingRepository;
        this.recentTransactionService = recentTransactionService;
        this.passwordEncryption = passwordEncryption;
        this.paymentLimitImp = paymentLimitImp;
        this.dailyTransactionLogServiceImp = dailyTransactionLogServiceImp;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {

        try {

            if (transactionDto == null) {
                throw new CustomException("Error Json rep", HttpStatus.BAD_REQUEST);
            }
            String check = this.checkPWTransaction(transactionDto.getSenderId(), transactionDto.getPassword());
            log.info("====================={}", check);
            String hex = hashingService.processTransaction(transactionDto);
            if (hex == null) {
                throw new CustomException("Transaction already exists", HttpStatus.BAD_REQUEST);
            }

            Account account = accountRepository.findById(transactionDto.getSenderId())
                    .orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));

            TransactionType transactionType = transactionTypeRepository.findById(transactionDto.getTransactionTypeId())
                    .orElseThrow(() -> new CustomException("TransactionType not found", HttpStatus.NOT_FOUND));

            checkSmallThan(transactionDto.getTransactionAmount());

            Account accountToPay = accountRepository.findByAccount_number(transactionDto.getToReceiveNumber());
            if (accountToPay == null) {
                log.info("error");
                throw new CustomException("Account_Number not found " + transactionDto.getToReceiveNumber(), HttpStatus.NOT_FOUND);
            }
            PaymentLimit paymentLimit = paymentLimitImp.checkPaymentLimit(account, transactionType);

            boolean checkCurrency = ValidateCarrency(transactionDto.getCurrencyToPay(), accountToPay.getCurrency());
            if (!checkCurrency) {
                throw new CustomException("Currency not supported", HttpStatus.BAD_REQUEST);
            }

            if (accountToPay.getCurrency().equals("USD")) {
                log.info("to pay USD =============== ");
                if (account.getCurrency().equals("USD")) {
                    log.info("from {} ============== ", account.getCurrency());
                    BigDecimal toPayAccountBalance = accountToPay.getBalance().add(transactionDto.getTransactionAmount());
                    accountToPay.setBalance(toPayAccountBalance);
                    BigDecimal subBalance = subBalance(account.getBalance(), transactionDto.getTransactionAmount());
                    if (transactionDto.getTransactionAmount().compareTo(account.getBalance()) > 0) {
                        log.info("error");
                        throw new CustomException("Not Balance ", HttpStatus.BAD_REQUEST);
                    }
                    this.checkPaymentLimit(paymentLimit,transactionDto.getTransactionAmount());
                    this.SaveTransactionLog(transactionDto.getTransactionAmount(),account,transactionType);
                    account.setBalance(subBalance);
                    accountRepository.save(accountToPay);
                    accountRepository.save(account);
                } else if (account.getCurrency().equals("KHR")) {
                    log.info("from = {} ============== ", account.getCurrency());
                    BigDecimal KHR = new BigDecimal("4000");
                    BigDecimal calculateBalance = transactionDto.getTransactionAmount().multiply(KHR);
                    log.info("===== sub Money Total current = {} ", calculateBalance);
                    BigDecimal toPayAccountBalance = accountToPay.getBalance().add(transactionDto.getTransactionAmount()); // Mean Total add to Pay
                    BigDecimal subBalance = subBalance(account.getBalance(), calculateBalance);
                    if (calculateBalance.compareTo(account.getBalance()) > 0) {
                        throw new CustomException("Not Balance ", HttpStatus.BAD_REQUEST);
                    }
                    this.checkPaymentLimit(paymentLimit,calculateBalance);
                    this.SaveTransactionLog(subBalance,account,transactionType);
                    accountToPay.setBalance(toPayAccountBalance);
                    account.setBalance(subBalance);
                    accountRepository.save(accountToPay);
                    accountRepository.save(account);
                }
            } else if (accountToPay.getCurrency().equals("KHR")) {
                log.info("to pay KHR =============== ");
                if (account.getCurrency().equals("KHR")) {
                    boolean CheckKHR = CheckKHR(transactionDto.getTransactionAmount());
                    if (!CheckKHR) {
                        throw new CustomException("Amount low 100 ", HttpStatus.BAD_REQUEST);
                    }
                    log.info("from = {} ============== ", account.getCurrency());
                    BigDecimal toPayAccountBalance = accountToPay.getBalance().add(transactionDto.getTransactionAmount()); // Mean Total add to Pay
                    log.info("===== add Money Total toPay = {} ", toPayAccountBalance);
                    if (transactionDto.getTransactionAmount().compareTo(account.getBalance()) > 0) {
                        log.error("Transaction amount {} exceeds account balance {} for account ID: {}",
                                transactionDto.getTransactionAmount(), account.getBalance(), account.getAccountId());
                        throw new CustomException("Insufficient balance to perform the transaction", HttpStatus.BAD_REQUEST);
                    }
                    BigDecimal currentBalance = subBalance(account.getBalance(), transactionDto.getTransactionAmount()); //Sub Balance
                    account.setBalance(currentBalance);
                    accountToPay.setBalance(toPayAccountBalance);
                    this.checkPaymentLimit(paymentLimit,transactionDto.getTransactionAmount());
                    this.SaveTransactionLog(currentBalance,account,transactionType);
                    accountRepository.save(accountToPay);
                    accountRepository.save(account);
                } else if (account.getCurrency().equals("USD")) {
                    log.info("from = {} ============== ", account.getCurrency());
                    boolean CheckKHR = CheckKHR(transactionDto.getTransactionAmount());
                    if (!CheckKHR) {
                        throw new CustomException("Amount low 100 ", HttpStatus.BAD_REQUEST);
                    }
                    BigDecimal KHR = new BigDecimal("4000");
//                    BigDecimal calculateBalance = transactionDto.getTransactionAmount().multiply(KHR);
                    BigDecimal calculateBalance = transactionDto.getTransactionAmount().divide(KHR, 2, BigDecimal.ROUND_HALF_UP);
                    log.info("===== sub Money Total currentAcc = {} ", calculateBalance);
                    BigDecimal toPayAccountBalance = accountToPay.getBalance().add(transactionDto.getTransactionAmount());
                    accountToPay.setBalance(toPayAccountBalance);
                    if (calculateBalance.compareTo(account.getBalance()) > 0) {
                        log.info("error1{} ", calculateBalance);
                        throw new CustomException("Not Balance ", HttpStatus.BAD_REQUEST);
                    }
                    this.checkPaymentLimit(paymentLimit,calculateBalance);
                    BigDecimal subBalance = subBalance(account.getBalance(), calculateBalance);
                    this.SaveTransactionLog(subBalance,account,transactionType);
                    account.setBalance(subBalance);
                    accountRepository.save(accountToPay);
                    accountRepository.save(account);
                }

            }

            Transaction transaction = TransactionDto.toEntity(transactionDto, transactionType, accountToPay);
            log.info("Creating new transaction");
//            BigDecimal currentBalance = account.getBalance().subtract(transactionDto.getTransactionAmount());
//            account.setBalance(currentBalance);

            transactionRepository.save(transaction);
            RecentTransaction recentTransaction = recentTransactionService.autoCreateRecentTransaction(transaction);
            hashingService.postHashingRecent(recentTransaction, hex);
            TransactionDto transactionDto1 = TransactionDto.fromEntity(transaction, account);
            return transactionDto1;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkSmallThan(BigDecimal Number) {
        if (Number.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException("Transaction amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }
    }

    public static Boolean ValidateCarrency(String carrency, String carrencyToPay) {
        if (carrency.equals(carrencyToPay)) {
            return true;
        }
        return false;
    }

    public BigDecimal subBalance(BigDecimal totalAmount, BigDecimal toPay) {
        BigDecimal currentBalance = totalAmount.subtract(toPay);
        return currentBalance;
    }

    @Override
    public String CheckPassword(Integer AccountNumber, String password) {

        Account account = accountRepository.findByAccount_number(AccountNumber);
        boolean encrypt = passwordEncryption.verifyPassword(password, account.getPassword());
        if (encrypt) {
            return "success";
        }
        throw new CustomException("Password not correct", HttpStatus.BAD_REQUEST);
    }

    public String checkPWTransaction(Integer accountId, String password) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));
        boolean encrypt = passwordEncryption.verifyPassword(password, account.getPassword());
        if (encrypt) {
            return "success";
        }
        throw new CustomException("Password not correct", HttpStatus.BAD_REQUEST);
    }

    public static Boolean CheckKHR(BigDecimal KHR) {
        if (KHR.compareTo(new BigDecimal("100")) < 0) {
            return false;
        }
        return true;
    }

    public void SaveTransactionLog(BigDecimal amount,Account account,TransactionType transactionType) {
        DailyTransactionLog dailyTransactionLog= dailyTransactionLogServiceImp.CheckValidate(account, transactionType);
        if(dailyTransactionLog == null) {
            DailyTransactionLogDto dailyTransactionLogDto = new DailyTransactionLogDto();
            dailyTransactionLogDto.setTotalAmount(amount);
            dailyTransactionLogServiceImp.createDailyTransactionLog(dailyTransactionLogDto,account,transactionType);
            log.info("===================Create in Method SaveTransactionLog {}",amount);
        }else {
            dailyTransactionLogServiceImp.UpdateAmount(dailyTransactionLog,amount);
            log.info("===================Update in Method SaveTransactionLog {}",amount);
        }
    }

    public void checkPaymentLimit(PaymentLimit paymentLimit,BigDecimal allowTransaction){

        if (paymentLimit != null) {
            // Check if the allowed transaction exceeds the payment limit
            log.info("Transaction payment limit: ");
            if (allowTransaction.compareTo(paymentLimit.getPinThreshold()) > 0) {
                throw new CustomException("Transaction amount exceeds the allowed limit", HttpStatus.BAD_REQUEST);
            }
        }
    }


}
