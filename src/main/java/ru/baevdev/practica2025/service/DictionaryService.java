package ru.baevdev.practica2025.service;

import ru.baevdev.practica2025.dto.WordEntryResponseDTO;
import ru.baevdev.practica2025.models.DictionaryType;
import ru.baevdev.practica2025.models.WordTranslation;

import java.util.List;
import java.util.UUID;

public interface DictionaryService {

    void addTranslation(String key, String type, String value);

    List<WordTranslation> getTranslations(String key, String type);

    boolean deleteTranslation(UUID translationId);

    boolean deleteAllTranslations(String key, String type);

    List<WordEntryResponseDTO> getAllEntries();
}
