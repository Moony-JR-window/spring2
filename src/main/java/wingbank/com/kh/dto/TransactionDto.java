package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.enums.TransactionStatus;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.Transaction;
import wingbank.com.kh.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionDto {
    private BigDecimal transactionAmount;
    private TransactionStatus transactionStatus;
    private LocalDate transactionDate;
    private Integer senderId;
    private Integer transactionTypeId;
    private String currencyToPay;
    private String password;
    // new feature
//    private Integer hashing;
    private Integer toReceiveNumber;

    public static Transaction toEntity(TransactionDto transactionDto, TransactionType transactionType, Account accountToPay) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(transactionDto.getTransactionAmount());
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionStatus(transactionDto.getTransactionStatus() != null ? transactionDto.getTransactionStatus() : TransactionStatus.completed);
        transaction.setTransactionTypeId(transactionType);
        transaction.setAccountId(transactionDto.getSenderId());

//        transaction.setAccount(account);
        transaction.setToReceiveNumber(accountToPay);
        return transaction;
    }

    public static TransactionDto fromEntity(Transaction transaction,Account account) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionAmount(transaction.getTransactionAmount());
        transactionDto.setTransactionStatus(transaction.getTransactionStatus());
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        transactionDto.setSenderId(transaction.getAccountId());
        transactionDto.setCurrencyToPay(account.getCurrency());
        transactionDto.setTransactionTypeId(transaction.getTransactionTypeId().getTransactionTypeId());
        transactionDto.setToReceiveNumber(transaction.getToReceiveNumber().getAccountNumber());
//        transactionDto.setPassword(account.getPassword());
        transactionDto.setPassword("Not Provide Password");
        return transactionDto;
    }
}
