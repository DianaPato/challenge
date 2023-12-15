package com.nisum.challenge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserRegistryRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;
    private List<PhoneDto> phones;

}


