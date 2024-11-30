package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "Hashing")
public class Hashing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hashingId;
    private String hashing;
    @ManyToOne
    @JoinColumn(name = "transactionId", referencedColumnName = "transactionId")
    private Transaction transaction;
    @ManyToOne
    @JoinColumn (name = "recentTransactionId", referencedColumnName = "recentTransactionId")
    private RecentTransaction recentTransaction;

}
