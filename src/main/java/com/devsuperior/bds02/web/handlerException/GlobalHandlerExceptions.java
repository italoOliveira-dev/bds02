package com.devsuperior.bds02.web.handlerException;

import com.devsuperior.bds02.exceptions.DatabaseIntegrityException;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerExceptions {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageError> handlerEntityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(new MessageError(request, status, e.getMessage()));
    }

    @ExceptionHandler(DatabaseIntegrityException.class)
    public ResponseEntity<MessageError> handlerDatabaseIntegrityException(DatabaseIntegrityException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new MessageError(request, status, e.getMessage()));
    }
}
