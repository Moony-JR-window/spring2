package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class UnlockType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UnlockTypeId;
    private String UnlockTypeName;
    @ManyToOne
    @JoinColumn(name = "accountId",nullable = true)
    private Account account;

}
