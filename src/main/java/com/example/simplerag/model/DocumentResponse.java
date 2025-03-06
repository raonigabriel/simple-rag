package com.example.simplerag.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a response containing the text and score of a document.
 */
@Getter
@RequiredArgsConstructor
public class DocumentResponse {

    private final String text;

    private final Double score;

}
