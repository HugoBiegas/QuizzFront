package com.master.quizzfront.Models;

import java.io.Serializable;

public class Tentative implements Serializable {
    private Integer id;
    private IdWrapper idUtilisateur;
    private IdWrapper idQuestionnaire;
    private Integer score;
    private String datePassage;

    public static class IdWrapper implements Serializable {
        private Integer id;
        private String email;
        private String motDePasse;
        private String nom;
        private String prenom;
        private String statut;
        private String dateCreation;

        // Getters et Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getMotDePasse() { return motDePasse; }
        public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }

        public String getStatut() { return statut; }
        public void setStatut(String statut) { this.statut = statut; }

        public String getDateCreation() { return dateCreation; }
        public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }
    }

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public IdWrapper getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(IdWrapper idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public IdWrapper getIdQuestionnaire() { return idQuestionnaire; }
    public void setIdQuestionnaire(IdWrapper idQuestionnaire) { this.idQuestionnaire = idQuestionnaire; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getDatePassage() { return datePassage; }
    public void setDatePassage(String datePassage) { this.datePassage = datePassage; }
}