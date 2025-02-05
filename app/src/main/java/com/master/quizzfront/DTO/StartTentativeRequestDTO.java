package com.master.quizzfront.DTO;

import com.master.quizzfront.Models.Questionnaire;
import com.master.quizzfront.Models.Utilisateur;

public class StartTentativeRequestDTO {
    private Utilisateur utilisateur;
    private Questionnaire questionnaire;


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
}

