package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table (name = "account_limt")
public class AccountLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountLimitId;
    @ManyToOne
    @JoinColumn(name = "accountID",referencedColumnName = "accountId")
    private Account accountID;
    @ManyToOne
    @JoinColumn(name = "transactionTypeId", referencedColumnName = "transactionTypeId")
    private TransactionType transactionTypeId;
    private BigDecimal dailyLimit;
    private BigDecimal transactionLimit;
    private String currency;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
