package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.model.RecentTransaction;
import wingbank.com.kh.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class RecentTransactionDto {
    private LocalDate accessTimestamp;
    private Integer transactionId;
    private String password;

//
    public static RecentTransaction toEntity(Transaction transaction) {
        RecentTransaction rt = new RecentTransaction();
        rt.setTransactions(transaction);
        rt.setAccessTimestamp(LocalDateTime.now());
        return rt;
    }
}

