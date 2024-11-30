package wingbank.com.kh.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table (name = "TransactionType")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer transactionTypeId;
    private String transactionName;
    @Column (length = 500)
    private String description;
    private LocalDate createAt;
}
