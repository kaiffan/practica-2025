package ru.baevdev.practica2025.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dictionary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private DictionaryType dictionaryType;

    @Column(name = "key", nullable = false)
    private String key;

    @OneToMany(mappedBy = "wordKey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WordTranslation> wordTranslations;

    @Builder
    public WordKey(DictionaryType dictionaryType, String key) {
        this.dictionaryType = dictionaryType;
        this.key = key;
    }
}
