package com.master.quizzfront.Models;

public class Reponse {
    private Integer id;
    private int idQuestion;
    private String texteReponse;
    private boolean estCorrect;

    // Constructeur par défaut
    public Reponse() {
        this.estCorrect = false;
    }

    // Constructeur avec paramètres
    public Reponse(Integer id, int idQuestion, String texteReponse, boolean estCorrect) {
        this.id = id;
        this.idQuestion = idQuestion;
        this.texteReponse = texteReponse;
        this.estCorrect = estCorrect;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getTexteReponse() {
        return texteReponse;
    }

    public void setTexteReponse(String texteReponse) {
        this.texteReponse = texteReponse;
    }

    public boolean isEstCorrect() {
        return estCorrect;
    }

    public void setEstCorrect(boolean estCorrect) {
        this.estCorrect = estCorrect;
    }
}

