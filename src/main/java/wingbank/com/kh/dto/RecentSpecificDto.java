package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.enums.TransactionStatus;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RecentSpecificDto {
    private BigDecimal transactionAmount;
    private TransactionStatus transactionStatus;
    private LocalDate transactionDate;
    private Account account;
    private Integer transactionTypeId;

    // new feature
//    private Integer hashing;
    private Integer accountNumber;
    public static RecentSpecificDto Mapper(Transaction transaction, Account account) {
        RecentSpecificDto rs = new RecentSpecificDto();
        rs.setTransactionAmount(transaction.getTransactionAmount());
        rs.setTransactionStatus(transaction.getTransactionStatus());
        rs.setTransactionDate(transaction.getTransactionDate());
        rs.setAccount(account);
        rs.setTransactionTypeId(transaction.getTransactionId());
        rs.setAccountNumber(transaction.getToReceiveNumber().getAccountNumber());
        return rs;
    }
}
