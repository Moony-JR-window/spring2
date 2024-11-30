package wingbank.com.kh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.enums.AccountStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table (name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String fullName;
    @Column (name = "email",unique = true)
    @Email()
    private String email;
    @Column (name = "phone_number",unique = true)
    private String phoneNumber;
    @Column (name = "date_of_birth ")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column (name = "account_status")
    private AccountStatus accountStatus;
    @Column (name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

}
