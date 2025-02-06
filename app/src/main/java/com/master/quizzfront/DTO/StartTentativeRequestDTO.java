package com.master.quizzfront.DTO;

public class StartTentativeRequestDTO {
    private SimpleIdWrapper utilisateur;
    private SimpleIdWrapper questionnaire;

    public static class SimpleIdWrapper {
        private Integer id;

        public SimpleIdWrapper(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public StartTentativeRequestDTO(Integer userId, Integer questionnaireId) {
        this.utilisateur = new SimpleIdWrapper(userId);
        this.questionnaire = new SimpleIdWrapper(questionnaireId);
    }

    public SimpleIdWrapper getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(SimpleIdWrapper utilisateur) {
        this.utilisateur = utilisateur;
    }

    public SimpleIdWrapper getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(SimpleIdWrapper questionnaire) {
        this.questionnaire = questionnaire;
    }
}