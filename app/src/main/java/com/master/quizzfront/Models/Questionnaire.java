package com.master.quizzfront.Models;

public class Questionnaire {
    private Integer id;
    private String titre;
    private String description;
    private String dateCreation;
    private int creePar;

    // Constructeur par défaut
    public Questionnaire() {}

    // Constructeur avec paramètres
    public Questionnaire(Integer id, String titre, String description, String dateCreation, int creePar) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateCreation = dateCreation;
        this.creePar = creePar;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getCreePar() {
        return creePar;
    }

    public void setCreePar(int creePar) {
        this.creePar = creePar;
    }
}

