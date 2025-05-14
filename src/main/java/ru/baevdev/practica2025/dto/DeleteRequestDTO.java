package ru.baevdev.practica2025.dto;

import lombok.Data;

@Data
public class DeleteRequestDTO {
    private String key;
    private String dictionaryType;
}
