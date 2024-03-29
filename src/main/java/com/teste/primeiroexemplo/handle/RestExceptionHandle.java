package com.teste.primeiroexemplo.handle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.teste.primeiroexemplo.model.error.ErrorMessage;
import com.teste.primeiroexemplo.model.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandle {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage error = new ErrorMessage("Not Found", HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
}
