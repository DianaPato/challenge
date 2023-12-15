package com.nisum.challenge.service;

import com.nisum.challenge.dto.UserRegistryRequest;
import com.nisum.challenge.dto.UserUpdateRequest;
import com.nisum.challenge.exception.InvalidPasswordException;
import com.nisum.challenge.exception.UserAlreadyExistException;
import com.nisum.challenge.model.User;

import java.util.List;

public interface UserService {

    User register(UserRegistryRequest userRegistryRequest) throws UserAlreadyExistException, InvalidPasswordException;

    List<User> read();

    User update(UserUpdateRequest request) throws UserAlreadyExistException, InvalidPasswordException;

    void delete(Long id);

}
