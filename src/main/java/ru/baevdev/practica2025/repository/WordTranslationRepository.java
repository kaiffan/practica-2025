package ru.baevdev.practica2025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.baevdev.practica2025.models.WordTranslation;

import java.util.List;
import java.util.UUID;

@Repository
public interface WordTranslationRepository extends JpaRepository<WordTranslation, UUID> {
    List<WordTranslation> findByWordKeyId(UUID keyId);
    void deleteByWordKeyId(UUID wordKeyId);
}
