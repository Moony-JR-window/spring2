package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.DailyTransactionLog;
import wingbank.com.kh.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DailyTransactionLogDto {
    private BigDecimal totalAmount;
    private Integer transactionTypeId;
    private Integer accountId;

    public static DailyTransactionLog toEntity(DailyTransactionLogDto dto, Account account, TransactionType transactionType) {
        DailyTransactionLog dailyTransactionLog = new DailyTransactionLog();
        dailyTransactionLog.setTotalAmount(dto.getTotalAmount());
        dailyTransactionLog.setTransactionDate(LocalDate.now());
        dailyTransactionLog.setTransactionTypeId(transactionType);
        dailyTransactionLog.setAccountId(account);
        dailyTransactionLog.setCreateAt(LocalDateTime.now());
        return dailyTransactionLog;
    }

    public static DailyTransactionLog UpdateTotalAmount(DailyTransactionLog dailyTransactionLog, BigDecimal totalAmount) {
        BigDecimal total=dailyTransactionLog.getTotalAmount().add(totalAmount);
        dailyTransactionLog.setTotalAmount(total);
        return dailyTransactionLog;
    }

}
