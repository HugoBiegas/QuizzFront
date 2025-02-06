package com.master.quizzfront.DTO;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class TentativeDetailsDTO implements Serializable {
    private Integer id;
    private Integer score;
    private List<QuestionDTO> questions;
    private Set<ReponseUtilisateurInfo> reponsesUtilisateur;

    public static class QuestionDTO {
        private Integer id;
        private String texteQuestion;
        private String typeReponse;
        private List<ReponseDTO> reponses;

        // Getters et Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getTexteQuestion() { return texteQuestion; }
        public void setTexteQuestion(String texteQuestion) { this.texteQuestion = texteQuestion; }
        public String getTypeReponse() { return typeReponse; }
        public void setTypeReponse(String typeReponse) { this.typeReponse = typeReponse; }
        public List<ReponseDTO> getReponses() { return reponses; }
        public void setReponses(List<ReponseDTO> reponses) { this.reponses = reponses; }
    }

    public static class ReponseDTO {
        private Integer id;
        private String texteReponse;
        private Boolean estCorrect;

        // Getters et Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getTexteReponse() { return texteReponse; }
        public void setTexteReponse(String texteReponse) { this.texteReponse = texteReponse; }
        public Boolean getEstCorrect() { return estCorrect; }
        public void setEstCorrect(Boolean estCorrect) { this.estCorrect = estCorrect; }
    }

    public static class ReponseUtilisateurInfo {
        private Integer questionId;
        private Integer reponseId;

        // Getters et Setters
        public Integer getQuestionId() { return questionId; }
        public void setQuestionId(Integer questionId) { this.questionId = questionId; }
        public Integer getReponseId() { return reponseId; }
        public void setReponseId(Integer reponseId) { this.reponseId = reponseId; }
    }

    // Méthode utilitaire pour vérifier si une réponse a été sélectionnée
    public boolean isReponseSelectionnee(Integer questionId, Integer reponseId) {
        return reponsesUtilisateur.stream().anyMatch(ru ->
                ru.getQuestionId().equals(questionId) && ru.getReponseId().equals(reponseId));
    }

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<QuestionDTO> getQuestions() { return questions; }
    public void setQuestions(List<QuestionDTO> questions) { this.questions = questions; }
    public Set<ReponseUtilisateurInfo> getReponsesUtilisateur() { return reponsesUtilisateur; }
    public void setReponsesUtilisateur(Set<ReponseUtilisateurInfo> reponsesUtilisateur) { this.reponsesUtilisateur = reponsesUtilisateur; }
}
