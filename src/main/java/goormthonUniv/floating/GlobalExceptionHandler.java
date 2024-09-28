package goormthonUniv.floating;

import exception.SeverErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeverErrorException.class)
    public ResponseEntity<Map<String,Object>> handleCustomInternalServerError(SeverErrorException ex){
        Map<String,Object> response = new HashMap<>();
        response.put("status",500);
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
