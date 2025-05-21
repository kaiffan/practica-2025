package ru.baevdev.practica2025.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.baevdev.practica2025.models.DictionaryType;
import ru.baevdev.practica2025.models.WordKey;
import ru.baevdev.practica2025.models.WordTranslation;
import ru.baevdev.practica2025.repository.WordKeyRepository;
import ru.baevdev.practica2025.repository.WordTranslationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final WordKeyRepository wordKeyRepository;
    private final WordTranslationRepository wordTranslationRepository;

    public DictionaryServiceImpl(
            WordKeyRepository wordKeyRepository,
            WordTranslationRepository wordTranslationRepository
    ) {
        this.wordKeyRepository = wordKeyRepository;
        this.wordTranslationRepository = wordTranslationRepository;
    }

    @Transactional
    @Override
    public void addTranslation(String key, String type, String value) {
        DictionaryType dictionaryType = convertToDictionaryType(type);
        Optional<WordKey> optionalWordKey = wordKeyRepository.findByKeyAndDictionaryType(key, dictionaryType);
        WordKey wordKey;

        validateKey(key, dictionaryType, value);

        wordKey = optionalWordKey.orElseGet(() -> wordKeyRepository.save(
                WordKey.builder()
                        .key(key)
                        .dictionaryType(dictionaryType)
                        .build()
                )
        );

        WordTranslation translation = WordTranslation.builder()
                .value(value)
                .wordKey(wordKey)
                .build();

        wordTranslationRepository.save(translation);
    }

    private void validateKey(String key, DictionaryType dictionaryType, String value) {
        if (dictionaryType == DictionaryType.LANG_NUMBERS) {
            if (key.length() != 5 || !key.matches("\\d{5}")) {
                throw new IllegalArgumentException("Key must be exactly 5 digits for LANG_NUMBERS.");
            }
            if (value.length() != 4 || !value.matches("[a-zA-Z]{4}")) {
                throw new IllegalArgumentException("Value must be exactly 4 Latin letters for LANG_NUMBERS.");
            }
        } else if (dictionaryType == DictionaryType.LANG_DIGITS) {
            if (key.length() != 4 || !key.matches("[a-zA-Z]{4}")) {
                throw new IllegalArgumentException("Key must be exactly 4 Latin letters for LANG_DIGITS.");
            }
            if (value.length() != 5 || !value.matches("\\d{5}")) {
                throw new IllegalArgumentException("Value must be exactly 5 digits for LANG_DIGITS.");
            }
        }
    }

    @Override
    public List<WordTranslation> getTranslations(String key, String type) {
        DictionaryType dictionaryType = convertToDictionaryType(type);
        return wordKeyRepository.findByKeyAndDictionaryType(key, dictionaryType)
                .map(wordKey -> wordTranslationRepository.findByWordKeyId(wordKey.getId()))
                .orElseGet(Collections::emptyList);
    }

    @Override
    @Transactional
    public boolean deleteTranslation(UUID translationId) {
        if (!wordTranslationRepository.existsById(translationId)) {
            return false;
        }

        wordTranslationRepository.deleteById(translationId);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteAllTranslations(String key, String type) {
        DictionaryType dictionaryType = convertToDictionaryType(type);
        Optional<WordKey> optionalWordKey = wordKeyRepository.findByKeyAndDictionaryType(key, dictionaryType);
        if (optionalWordKey.isEmpty()) {
            return false;
        }

        WordKey wordKey = optionalWordKey.get();

        wordTranslationRepository.deleteByWordKeyId(wordKey.getId());

        wordKeyRepository.delete(wordKey);

        return true;
    }

    private DictionaryType convertToDictionaryType(String typeStr) {
        try {
            return DictionaryType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid dictionary type: " + typeStr);
        }
    }
}
