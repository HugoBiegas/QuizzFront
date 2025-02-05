package com.master.quizzfront.Models;

public class Tentative {
    private Integer id;
    private int idUtilisateur;
    private int idQuestionnaire;
    private int score;
    private String datePassage;

    // Constructeur par défaut
    public Tentative() {
        this.score = 0;
    }

    // Constructeur avec paramètres
    public Tentative(Integer id, int idUtilisateur, int idQuestionnaire, int score, String datePassage) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idQuestionnaire = idQuestionnaire;
        this.score = score;
        this.datePassage = datePassage;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdQuestionnaire() {
        return idQuestionnaire;
    }

    public void setIdQuestionnaire(int idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDatePassage() {
        return datePassage;
    }

    public void setDatePassage(String datePassage) {
        this.datePassage = datePassage;
    }
}

