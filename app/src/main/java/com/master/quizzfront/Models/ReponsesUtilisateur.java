package com.master.quizzfront.Models;

public class ReponsesUtilisateur {
    private int idTentative;
    private int idQuestion;
    private int idReponse;

    // Constructeur par défaut
    public ReponsesUtilisateur() {}

    // Constructeur avec paramètres
    public ReponsesUtilisateur(int idTentative, int idQuestion, int idReponse) {
        this.idTentative = idTentative;
        this.idQuestion = idQuestion;
        this.idReponse = idReponse;
    }

    // Getters et Setters
    public int getIdTentative() {
        return idTentative;
    }

    public void setIdTentative(int idTentative) {
        this.idTentative = idTentative;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(int idReponse) {
        this.idReponse = idReponse;
    }
}

