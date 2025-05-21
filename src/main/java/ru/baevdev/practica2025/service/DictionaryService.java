package ru.baevdev.practica2025.service;

import ru.baevdev.practica2025.dto.TranslationResponseDTO;
import ru.baevdev.practica2025.dto.WordEntryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DictionaryService {

    void addTranslation(String key, String type, String value);

    List<TranslationResponseDTO> getTranslations(String key, String type);

    boolean deleteTranslation(UUID translationId);

    boolean deleteAllTranslations(String keyId);

    List<WordEntryResponseDTO> getAllEntries();
}
