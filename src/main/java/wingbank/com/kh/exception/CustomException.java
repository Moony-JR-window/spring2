package wingbank.com.kh.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
