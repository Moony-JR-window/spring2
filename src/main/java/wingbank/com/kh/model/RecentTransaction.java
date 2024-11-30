package wingbank.com.kh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table (name = "RecentTransaction")
public class RecentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recentTransactionId;

    private LocalDateTime accessTimestamp;
    @ManyToOne
    @JoinColumn (name = "transactionId",nullable = false)
    private Transaction transactions;



}
