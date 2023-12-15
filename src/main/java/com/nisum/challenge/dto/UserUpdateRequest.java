package com.nisum.challenge.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRequest extends UserRegistryRequest {
    @NotNull
    private Long id;
}
