package com.master.quizzfront.Models;

import java.io.Serializable;

public class Tentative implements Serializable {
    private Integer id;
    private int idUtilisateur;
    private int idQuestionnaire;
    private int score;
    private String datePassage;

    public Tentative() {
        this.score = 0;
    }

    public Tentative(Integer id, int idUtilisateur, int idQuestionnaire, int score, String datePassage) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idQuestionnaire = idQuestionnaire;
        this.score = score;
        this.datePassage = datePassage;
    }

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

