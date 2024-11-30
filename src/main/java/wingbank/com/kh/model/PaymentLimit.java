package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PaymentLimits")
@Getter
@Setter
public class PaymentLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentLimitId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "accountId",nullable = false)
    private Account accountId;
    private BigDecimal faceidThreshold;
    private BigDecimal pinThreshold;
    @ManyToOne
    @JoinColumn (name = "transactionTypeId", referencedColumnName = "transactionTypeId",nullable = false)
    private TransactionType transactionType;
    private String currency ;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
