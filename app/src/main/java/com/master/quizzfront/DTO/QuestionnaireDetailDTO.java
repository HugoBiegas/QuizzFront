package com.master.quizzfront.DTO;

import com.master.quizzfront.Enum.TypeReponse;

import java.util.List;
import java.util.Date;

public class QuestionnaireDetailDTO {
    private Integer id;
    private String titre;
    private String description;
    private Date dateCreation;
    private List<Question> questions;

    public QuestionnaireDetailDTO(Integer id, String titre, String description, Date dateCreation, List<Question> questions) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateCreation = dateCreation;
        this.questions = questions;
    }

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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public static class Question {
        private Integer id;
        private String texteQuestion;
        private TypeReponse typeReponse;
        private List<Reponse> reponses;

        public Question(Integer id, String texteQuestion, TypeReponse typeReponse, List<Reponse> reponses) {
            this.id = id;
            this.texteQuestion = texteQuestion;
            this.typeReponse = typeReponse;
            this.reponses = reponses;
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

        public List<Reponse> getReponses() {
            return reponses;
        }

        public void setReponses(List<Reponse> reponses) {
            this.reponses = reponses;
        }
    }

    public static class Reponse {
        private Integer id;
        private String texteReponse;

        public Reponse(Integer id, String texteReponse) {
            this.id = id;
            this.texteReponse = texteReponse;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTexteReponse() {
            return texteReponse;
        }

        public void setTexteReponse(String texteReponse) {
            this.texteReponse = texteReponse;
        }
    }
}

