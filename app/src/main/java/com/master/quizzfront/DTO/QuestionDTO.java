package com.master.quizzfront.DTO;

import java.io.Serializable;

public class QuestionDTO implements Serializable {
    private Integer id;
    private String texteQuestion;
    private String typeReponse;
    private QuestionnaireSimpleDTO idQuestionnaire;

    public static class QuestionnaireSimpleDTO implements Serializable {
        private Integer id;
        private String titre;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getTitre() { return titre; }
        public void setTitre(String titre) { this.titre = titre; }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTexteQuestion() { return texteQuestion; }
    public void setTexteQuestion(String texteQuestion) { this.texteQuestion = texteQuestion; }

    public String getTypeReponse() { return typeReponse; }
    public void setTypeReponse(String typeReponse) { this.typeReponse = typeReponse; }

    public QuestionnaireSimpleDTO getIdQuestionnaire() { return idQuestionnaire; }
    public void setIdQuestionnaire(QuestionnaireSimpleDTO idQuestionnaire) { this.idQuestionnaire = idQuestionnaire; }
}

