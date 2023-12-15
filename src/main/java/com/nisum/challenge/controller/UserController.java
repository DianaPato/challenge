package com.nisum.challenge.controller;

import com.nisum.challenge.dto.UserRegistryRequest;
import com.nisum.challenge.dto.UserUpdateRequest;
import com.nisum.challenge.exception.InvalidPasswordException;
import com.nisum.challenge.exception.UserAlreadyExistException;
import com.nisum.challenge.model.User;
import com.nisum.challenge.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> registry(@Valid @RequestBody UserRegistryRequest userRegistryRequest)
            throws InvalidPasswordException, UserAlreadyExistException {

        User UserSave = service.register(userRegistryRequest);
        return new ResponseEntity<User>(UserSave, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<User>> read() {
        List<User> list = service.read();
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody UserUpdateRequest request) throws UserAlreadyExistException,
            InvalidPasswordException {

        User UserSave = service.update(request);
        return new ResponseEntity<User>(UserSave, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> mesage = new HashMap<>();
        final StringBuilder errorMsg = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                errorMsg.append(fieldName + ": " + error.getDefaultMessage() + ". ");
            } else if (error instanceof ObjectError) {
                errorMsg.append(error.getObjectName() + ": " + error.getDefaultMessage() + ". ");
            }
        });
        mesage.put("mensaje", errorMsg.toString());
        return mesage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleExceptions(Exception ex) {
        Map<String, String> mesage = new HashMap<>();
        mesage.put("mensaje", "Error inesperado: " + ex.getMessage());
        return mesage;
    }
}
