package wingbank.com.kh.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.enums.AccountStatus;
import wingbank.com.kh.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    @Column(unique = true)
    private Integer accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private BigDecimal balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    //    @Column(nullable = false)
    private LocalDate createdAt;
    private String password;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "unlockTypeId",referencedColumnName = "unlockTypeId")
    private UnlockType unlockTypeId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    @JsonIgnore
    private Customer customer;
    private Integer wrongPassword=0;
}
