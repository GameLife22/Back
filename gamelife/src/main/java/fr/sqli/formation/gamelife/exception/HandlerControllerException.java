package fr.sqli.formation.gamelife.exception;

import fr.sqli.formation.gamelife.dto.response.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerControllerException {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerControllerException.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception pException){
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(ParameterException pException){
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(ExistingUserException pException){
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(NonExistentUserException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(NonExistentUserException pException){
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(DisableAccountException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(DisableAccountException pException){
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(AccessDeniedException pException){
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(BadCredentialsException pException){
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(OldPasswordException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(OldPasswordException pException){
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(ItemOrderNotFoundException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(ItemOrderNotFoundException pException) {
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(OrderNotFoundException pException) {
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(SellerGameException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(SellerGameException pException) {
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(InvalidStatusOrderException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(InvalidStatusOrderException pException) {
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(GameNotFoundException pException) {
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(pException.getMessage()));
    }

    @ExceptionHandler(GameExistsException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(GameExistsException pException) {
        LOGGER.error("Exception Handler", pException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(pException.getMessage()));
    }
}