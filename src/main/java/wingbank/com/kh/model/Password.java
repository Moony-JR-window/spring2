package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "password")
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passwordId;
    private String pin;
    private String faceId;
    @ManyToOne
    @JoinColumn (name = "accountId",referencedColumnName = "accountId")
    private Account account;

}
