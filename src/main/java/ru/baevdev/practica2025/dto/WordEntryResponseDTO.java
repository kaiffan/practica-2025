package ru.baevdev.practica2025.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.baevdev.practica2025.models.DictionaryType;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordEntryResponseDTO {
    private UUID id;
    private DictionaryType type;
    private String key;
    private List<String> values;
}