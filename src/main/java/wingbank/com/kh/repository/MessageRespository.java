package wingbank.com.kh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wingbank.com.kh.model.MessageResponse;

import java.util.List;

public interface MessageRespository extends JpaRepository<MessageResponse, Long> {
    List<MessageResponse> findByCode(String code);
}
