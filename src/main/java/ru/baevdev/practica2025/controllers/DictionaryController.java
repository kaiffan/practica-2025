package ru.baevdev.practica2025.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.baevdev.practica2025.dto.*;
import ru.baevdev.practica2025.service.DictionaryService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTranslation(@Valid @RequestBody DictionaryRequestDTO request) {
        dictionaryService.addTranslation(request.getKey(), request.getDictionaryType(), request.getValue());
        return ResponseEntity.ok("Translation added");
    }

    @GetMapping("/get")
    public ResponseEntity<List<TranslationResponseDTO>> getTranslations(
            @Valid @RequestBody AllTranslationsRequestDTO allTranslationsRequestDTO
    ) {
        List<TranslationResponseDTO> translations = dictionaryService.getTranslations(
                allTranslationsRequestDTO.getKey(),
                allTranslationsRequestDTO.getDictionaryType()
        );
        return ResponseEntity.ok(translations);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WordEntryResponseDTO>> getAllEntries() {
        return ResponseEntity.ok(dictionaryService.getAllEntries());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTranslation(@PathVariable UUID id) {
        boolean deleted = dictionaryService.deleteTranslation(id);
        if (deleted) {
            return ResponseEntity.ok("Translation deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllTranslations(@Valid @RequestBody DeleteRequestDTO request) {
        boolean deleted = dictionaryService.deleteAllTranslations(request.getKeyId());
        if (deleted) {
            return ResponseEntity.ok("All translations deleted");
        }
        return ResponseEntity.notFound().build();
    }
}
