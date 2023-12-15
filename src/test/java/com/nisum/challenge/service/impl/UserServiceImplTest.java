package com.nisum.challenge.service.impl;

import com.nisum.challenge.dto.UserRegistryRequest;
import com.nisum.challenge.exception.InvalidPasswordException;
import com.nisum.challenge.exception.UserAlreadyExistException;
import com.nisum.challenge.model.User;
import com.nisum.challenge.repository.UserRepository;
import com.nisum.challenge.service.JwtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    private final String regex = "\\d+";
    @InjectMocks
    private UserServiceImpl subject;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    //= new UserServiceImpl(userRepository, jwtService, regex);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        subject.setRegex(regex);

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        when(userRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        when(jwtService.getToken(anyString())).thenReturn("TOKEN");
    }

    @Test
    public void register_validRequest_validResult() throws UserAlreadyExistException, InvalidPasswordException {
        UserRegistryRequest request = createRegistryRequest();
        User result = subject.register(request);
        assertNotNull(result);
        assertEquals(request.getName(), result.getName());
        assertNotNull(result.getToken());
    }

    @Test(expected = InvalidPasswordException.class)
    public void register_InvalidPassword_exception() throws UserAlreadyExistException, InvalidPasswordException {
        UserRegistryRequest request = createRegistryRequest();
        request.setPassword("InvalidPassword");
        subject.register(request);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void register_AlreadyRegisteredEmail_exception() throws UserAlreadyExistException, InvalidPasswordException {
        UserRegistryRequest request = createRegistryRequest();

        when(userRepository.findByEmail(Mockito.eq(request.getEmail()))).thenReturn(Optional.of(new User()));

        subject.register(request);
    }

    private UserRegistryRequest createRegistryRequest() {
        UserRegistryRequest request = new UserRegistryRequest();
        request.setName("name");
        request.setEmail("name@gmail.com");
        request.setPassword("1232");
        request.setPhones(new ArrayList<>());
        return request;
    }
}