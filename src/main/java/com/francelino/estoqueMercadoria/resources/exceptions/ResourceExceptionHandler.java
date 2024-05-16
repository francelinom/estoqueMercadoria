package com.francelino.estoqueMercadoria.resources.exceptions;

import com.francelino.estoqueMercadoria.services.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServlet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServlet resquest) {
        StandardError error = new StandardError();
        error.setTimestamp(System.currentTimeMillis());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Resource not found");
        error.setMessage(e.getMessage());
        error.setPath(resquest.getServletInfo());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
