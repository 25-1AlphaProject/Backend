package alpha.chupchup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "서버 오류가 발생했습니다.";

        if (ex instanceof ResponseStatusException responseEx) {
            status = HttpStatus.valueOf(responseEx.getStatusCode().value());
            message = responseEx.getReason();
        }

        error.put("status", status.value());
        error.put("message", message);

        return new ResponseEntity<>(error, status);
    }
}

