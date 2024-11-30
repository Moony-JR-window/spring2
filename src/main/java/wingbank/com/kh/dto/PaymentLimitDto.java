package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.PaymentLimit;
import wingbank.com.kh.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentLimitDto {
    private Integer accountId;
    private BigDecimal faceidThreshold;
    private BigDecimal pinidThreshold;
    private String password;
    private Integer transactionTypeId;

    public static PaymentLimit toEntity(PaymentLimitDto paymentLimitDto, Account account, TransactionType transactionType) {
        PaymentLimit paymentLimit = new PaymentLimit();
        paymentLimit.setAccountId(account);
        paymentLimit.setTransactionType(transactionType);
        paymentLimit.setCurrency(account.getCurrency());
        paymentLimit.setPinThreshold(paymentLimitDto.pinidThreshold);
        paymentLimit.setFaceidThreshold(paymentLimitDto.faceidThreshold);
        paymentLimit.setCreatedAt(LocalDateTime.now());
        paymentLimit.setUpdatedAt(LocalDateTime.now());
        return paymentLimit;
    }
}
