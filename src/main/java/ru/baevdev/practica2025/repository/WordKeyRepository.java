package ru.baevdev.practica2025.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.baevdev.practica2025.models.DictionaryType;
import ru.baevdev.practica2025.models.WordKey;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WordKeyRepository extends JpaRepository<WordKey, UUID> {
    Optional<WordKey> findByKeyAndDictionaryType(String key, DictionaryType dictionaryType);

    @EntityGraph(attributePaths = "wordTranslations")
    List<WordKey> findAll();
}
