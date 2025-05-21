package ru.baevdev.practica2025.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AllTranslationsRequestDTO {
    @NotBlank(message = "Key cannot be null")
    private String key;

    @NotBlank(message = "DictionaryType cannot be null")
    private String dictionaryType;
}
