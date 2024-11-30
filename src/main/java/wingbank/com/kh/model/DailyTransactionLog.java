package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "DailyTransactionLog")
public class DailyTransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;
    private BigDecimal totalAmount;
    private LocalDate transactionDate;
    @ManyToOne
    @JoinColumn(name = "transactionTypeId", referencedColumnName = "transactionTypeId", nullable = false)
    private TransactionType transactionTypeId;
    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "accountId", nullable = false)
    private Account accountId;
    private LocalDateTime createAt;

}
