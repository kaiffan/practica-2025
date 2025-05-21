package ru.baevdev.practica2025.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AllTranslationsRequestDTO {
    @NotBlank(message = "Value cannot be null")
    private String key;

    @NotBlank(message = "Value cannot be null")
    private String dictionaryType;
}
