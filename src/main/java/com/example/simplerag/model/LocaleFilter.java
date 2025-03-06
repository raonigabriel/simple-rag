package com.example.simplerag.model;

public enum LocaleFilter {

    ENGLISH("en-US"),
    PORTUGUESE("pt-BR"),
    SPANISH("es-MX"),
    FRENCH("fr-FR"),
    ITALIAN("it-IT");

    private final String value;

    LocaleFilter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}