package ru.baevdev.practica2025.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TranslationResponseDTO {
    private UUID id;
    private String value;
}
