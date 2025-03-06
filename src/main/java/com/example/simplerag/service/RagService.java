package com.example.simplerag.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.simplerag.model.DocumentResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagService {

    public static final String LOCALE_KEY = "locale";

    private final VectorStore vectorStore;

    public @NonNull List<DocumentResponse> similaritySearch(@NonNull SearchRequest searchRequest) {
        final var documents = vectorStore.similaritySearch(searchRequest);
        return documents != null ? documents.stream()
                .map(doc -> new DocumentResponse(doc.getText(), doc.getScore())).toList() : Collections.emptyList();
    }

    /**
     * Initializes the vector store with dummy data for testing purposes.
     * This method is called after the bean's properties have been set.
     */
    @PostConstruct
    void setupVectorStore() {

        log.info("Vector store is empty, adding dummy data");

        final Map<String, Object> metadataPortuguese = localeOf("pt-BR");
        final Map<String, Object> metadataEnglish = localeOf("en-US");
        final Map<String, Object> metadataSpanish = localeOf("es-MX");
        final Map<String, Object> metadataFrench = localeOf("fr-FR");
        final Map<String, Object> metadataItalian = localeOf("it-IT");

        final var documents = List.of(
                new Document("A powerfull female adult woman member of a monarchy",
                        metadataEnglish),
                new Document("A powerfull female teenager member of a monarchy",
                        metadataEnglish),
                new Document("Una persona de sexo masculino con poder supremo en una monarquía",
                        metadataSpanish),
                new Document(
                        "Une personne de sexe masculin avec beaucoup de pouvoir dans une république présidentielle",
                        metadataFrench),
                new Document("A male figure with supreme authority in the Roman Catholic Church",
                        metadataEnglish),
                new Document(
                        "Una persona di sesso maschile con potere di comando in un capo tribù di una tribù indigena del Brasile",
                        metadataItalian),
                new Document("It is a large piece of fabric used to catch the wind and propel a boat forward",
                        metadataEnglish),
                new Document("É uma superfície de tecido usada para captar o vento e impulsionar um barco",
                        metadataPortuguese),
                new Document("É um objeto composto por cera ou parafina, que, ao ser aceso, emite luz",
                        metadataPortuguese),
                new Document(
                        "É um componente da ignição de um motor, que gera a faísca que acende a mistura de ar e combustível",
                        metadataPortuguese),
                new Document("The iPhone 15 has a 6.1-inch display, A16 Bionic chip, 48MP rear camera, 5G connectivity, and 128GB storage.",
                    metadataEnglish));
        vectorStore.add(documents);
    }

    private Map<String, Object> localeOf(String locale) {
        return Map.of(LOCALE_KEY, locale);
    }

}
