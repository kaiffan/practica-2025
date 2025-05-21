package ru.baevdev.practica2025.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteRequestDTO {
    @NotBlank(message = "KeyId cannot be null")
    private String keyId;
}
