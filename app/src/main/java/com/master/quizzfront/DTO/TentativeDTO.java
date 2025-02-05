package com.master.quizzfront.DTO;

import java.io.Serializable;
import java.util.Date;

public class TentativeDTO implements Serializable {
    private Integer id;
    private QuestionnaireSimpleDTO questionnaire;
    private Integer score;
    private Date datePassage;

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

    public QuestionnaireSimpleDTO getQuestionnaire() { return questionnaire; }
    public void setQuestionnaire(QuestionnaireSimpleDTO questionnaire) { this.questionnaire = questionnaire; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Date getDatePassage() { return datePassage; }
    public void setDatePassage(Date datePassage) { this.datePassage = datePassage; }
}