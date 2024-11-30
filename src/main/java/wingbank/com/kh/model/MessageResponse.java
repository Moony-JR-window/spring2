package wingbank.com.kh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table (name = "Message")
@Entity
@Getter
@Setter

public class MessageResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String message;       // Default language message
    private String messageKh;     // Khmer message
    private String messageEn;     // English message
}
