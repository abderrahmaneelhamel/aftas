package com.project.aftas.Models.entities;

public enum IdentificationDocumentType {

    PASSPORT("Passport"),
    CIN("CIN"),
    CARTE_RESIDENCE("Residence card");

    private final String name;

    private IdentificationDocumentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
