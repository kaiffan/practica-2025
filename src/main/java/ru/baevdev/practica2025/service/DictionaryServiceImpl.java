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

        validateEntry(key, value, dictionaryType);

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

    private void validateEntry(String key, String value, DictionaryType dictionaryType) {
        switch (dictionaryType) {
            case LANG_NUMBERS -> {
                validateFormat(key, "\\d{5}", "Key must be exactly 5 digits for LANG_NUMBERS.");
                validateFormat(value, "[a-zA-Z]{4}", "Value must be exactly 4 Latin letters for LANG_NUMBERS.");
            }
            case LANG_DIGITS -> {
                validateFormat(key, "[a-zA-Z]{4}", "Key must be exactly 4 Latin letters for LANG_DIGITS.");
                validateFormat(value, "\\d{5}", "Value must be exactly 5 digits for LANG_DIGITS.");
            }
            default -> throw new IllegalArgumentException("Unsupported dictionary type.");
        }
    }

    private void validateFormat(String input, String regex, String errorMessage) {
        if (!input.matches(regex)) {
            throw new IllegalArgumentException(errorMessage);
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

    private DictionaryType convertToDictionaryType(String typeStr) throws IllegalArgumentException {
        try {
            return DictionaryType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid dictionary type: " + typeStr + " must be: " + DictionaryType.LANG_NUMBERS + " " + DictionaryType.LANG_DIGITS);
        }
    }
}
