package com.master.quizzfront.DTO;

import java.util.List;

public class ReponseUtilisateurDTO {
    private Integer tentativeId;
    private Integer questionId;
    private List<Integer> reponseIds;

    public ReponseUtilisateurDTO(Integer tentativeId, Integer questionId, List<Integer> reponseIds) {
        this.tentativeId = tentativeId;
        this.questionId = questionId;
        this.reponseIds = reponseIds;
    }

    public Integer getTentativeId() {
        return tentativeId;
    }

    public void setTentativeId(Integer tentativeId) {
        this.tentativeId = tentativeId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public List<Integer> getReponseIds() {
        return reponseIds;
    }

    public void setReponseIds(List<Integer> reponseIds) {
        this.reponseIds = reponseIds;
    }
}


