package ru.baevdev.practica2025.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DictionaryRequestDTO {
    @NotBlank(message = "Key cannot be null")
    private String key;

    @NotBlank(message = "Dictionary type cannot be null")
    private String dictionaryType;

    @NotBlank(message = "Value cannot be null")
    private String value;
}
