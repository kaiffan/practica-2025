package ru.baevdev.practica2025.dto;

import lombok.Data;

@Data
public class DictionaryRequestDTO {
    private String key;
    private String dictionaryType;
    private String value;
}
