package com.nisum.challenge.service.impl;

import com.nisum.challenge.dto.PhoneDto;
import com.nisum.challenge.dto.UserRegistryRequest;
import com.nisum.challenge.dto.UserUpdateRequest;
import com.nisum.challenge.exception.InvalidPasswordException;
import com.nisum.challenge.exception.UserAlreadyExistException;
import com.nisum.challenge.model.Phone;
import com.nisum.challenge.model.User;
import com.nisum.challenge.repository.UserRepository;
import com.nisum.challenge.service.JwtService;
import com.nisum.challenge.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
@Setter
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;

    @Value("${password.regex}")
    private String regex;

    private boolean emailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public User register(UserRegistryRequest userRegistryRequest) throws
            UserAlreadyExistException, InvalidPasswordException {

        validatePassword(userRegistryRequest.getPassword());

        if (emailExists(userRegistryRequest.getEmail())) {
            UserAlreadyExistException e = new UserAlreadyExistException("There is an account with that email address: "
                    + userRegistryRequest.getEmail());
            log.info("Email already used, registration failed", e);
            throw e;
        }
        Instant now = Instant.now();

        User user = User.builder()
                .name(userRegistryRequest.getName())
                .email(userRegistryRequest.getEmail())
                .password(encodePassword(userRegistryRequest.getPassword()))
                .phones(new ArrayList<>())
                .token(jwtService.getToken(userRegistryRequest.getName()))
                .createdOn(now)
                .lastUpdatedOn(now)
                .lastLogin(now)
                .isActive(true)
                .build();

        userRegistryRequest.getPhones().stream().forEach(p -> user.getPhones().add(getPhone(p)));

        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> read() {
        return repository.findAll();
    }

    @Override
    public User update(UserUpdateRequest request) throws InvalidPasswordException, UserAlreadyExistException {
        validatePassword(request.getPassword());

        Optional<User> sameEmailUserOpt = repository.findByEmail(request.getEmail());
        if (sameEmailUserOpt.isPresent()) {
            if (!sameEmailUserOpt.get().getId().equals(request.getId())) {
                UserAlreadyExistException e = new UserAlreadyExistException("There is an account with that email address: "
                        + request.getEmail());
                log.info("Email already used, registration failed", e);
                throw e;
            }
        }

        User user = repository.getReferenceById(request.getId());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodePassword(request.getPassword()));
        user.getPhones().clear();
        request.getPhones().stream().forEach(p -> user.getPhones().add(getPhone(p)));

        Instant now = Instant.now();
        user.setLastUpdatedOn(now);

        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private Phone getPhone(PhoneDto dto) {
        Phone phone = new Phone();
        //TODO use a mapper
        phone.setNumber(dto.getNumber());
        phone.setCountryCode(dto.getContrycode());
        phone.setCityCode(dto.getCitycode());
        return phone;
    }

    /**
     * Avoid storing the password flat in the DB. TODO
     *
     * @param password
     * @return
     */
    private String encodePassword(String password) {
        return password;
    }

    private void validatePassword(String password) throws InvalidPasswordException {
        if (!Pattern.matches(regex, password)) {
            InvalidPasswordException e = new InvalidPasswordException("Password format must follow: " + regex);
            throw e;
        }
    }

}
