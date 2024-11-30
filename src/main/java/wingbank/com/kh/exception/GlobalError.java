package wingbank.com.kh.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wingbank.com.kh.model.MessageResponse;
import wingbank.com.kh.service.serviceImp.MessageCacheImp;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalError {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {

        MessageResponse messageResponse = MessageCacheImp.getDataFromCache(ex.getStatus().toString());
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        errorDetails.put("status", ex.getStatus());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("time", LocalDateTime.now());

        errorDetails.put("kh", messageResponse.getMessageKh());
        errorDetails.put("ch ", messageResponse.getMessage());
        errorDetails.put("en", messageResponse.getMessageEn());
        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        MessageResponse messageResponse = MessageCacheImp.getDataFromCache(HttpStatus.BAD_REQUEST.toString());
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        errorDetails.put("status", HttpStatus.BAD_REQUEST.toString());
        errorDetails.put("message", "Invalid data format: ");
        errorDetails.put("time", LocalDateTime.now());

        log.info("Data is ============== "+ messageResponse.getCode());
        errorDetails.put("kh", messageResponse.getMessageKh());
        errorDetails.put("default", messageResponse.getMessage());
        errorDetails.put("en", messageResponse.getMessageEn());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(InternalServerErrorException.class)
//    public ResponseEntity<Map<String, Object>> handleInternalServerError(InternalServerErrorException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", ex.getStatus().value());
//        errorDetails.put("message", ex.getMessage());
//        errorDetails.put("time", LocalDateTime.now());
//
//        return new ResponseEntity<>(errorDetails, ex.getStatus());
//    }


//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", HttpStatus.NOT_FOUND.value());
//        errorDetails.put("message", "The requested route was not found.");
//        errorDetails.put("time", LocalDateTime.now());
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }
}

