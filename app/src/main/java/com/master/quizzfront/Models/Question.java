package com.master.quizzfront.Models;

import com.master.quizzfront.Enum.TypeReponse;

import java.io.Serializable;

public class Question implements Serializable {
    private Integer id;
    private String texteQuestion;
    private TypeReponse typeReponse;
    private int idQuestionnaire;

    // Constructeur par défaut
    public Question() {
        this.typeReponse = TypeReponse.unique;
    }

    // Constructeur avec paramètres
    public Question(Integer id, String texteQuestion, TypeReponse typeReponse, int idQuestionnaire) {
        this.id = id;
        this.texteQuestion = texteQuestion;
        this.typeReponse = typeReponse;
        this.idQuestionnaire = idQuestionnaire;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexteQuestion() {
        return texteQuestion;
    }

    public void setTexteQuestion(String texteQuestion) {
        this.texteQuestion = texteQuestion;
    }

    public TypeReponse getTypeReponse() {
        return typeReponse;
    }

    public void setTypeReponse(TypeReponse typeReponse) {
        this.typeReponse = typeReponse;
    }

    public int getIdQuestionnaire() {
        return idQuestionnaire;
    }

    public void setIdQuestionnaire(int idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
    }
}


