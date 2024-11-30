package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table (name = "SearchTransaction")
public class SearchTransaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer searchTransactionId;
    private BigDecimal transactionAmount;
    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "transactionId", nullable = false,unique = true)
    private Transaction transaction;

     @ManyToOne
     @JoinColumn(name = "accountId",  nullable = false)
     private Account account;

     @ManyToOne
     @JoinColumn(name = "transactionTypeId",nullable = false)
     private TransactionType transactionType;

}
