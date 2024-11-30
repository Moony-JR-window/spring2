package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table (name = "Transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    private BigDecimal transactionAmount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private LocalDate transactionDate;
//
//    @ManyToOne
//    @JoinColumn(name = "get_transaction", referencedColumnName = "account_number") // Use account_id instead of account_number
    @ManyToOne
    @JoinColumn(referencedColumnName = "accountNumber")
    private Account toReceiveNumber;

    @ManyToOne
    @JoinColumn (name = "transactionTypeId",nullable = false)
    private TransactionType transactionTypeId;
    private Integer accountId;

//
//    @ManyToOne
//    @JoinColumn(name = "account_id", nullable = false)
//    private Account account;
}
