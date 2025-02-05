package com.master.quizzfront.Models;

import com.master.quizzfront.Enum.UtilisateurStatut;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private Integer id;
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private UtilisateurStatut statut;
    private String dateCreation;

    // Constructeur par défaut
    public Utilisateur() {
        this.statut = UtilisateurStatut.En_attente;
    }

    // Constructeur avec paramètres
    public Utilisateur(Integer id, String email, String motDePasse, String nom, String prenom,
                       UtilisateurStatut statut, String dateCreation) {
        this.id = id;
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.statut = statut;
        this.dateCreation = dateCreation;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public UtilisateurStatut getStatut() {
        return statut;
    }

    public void setStatut(UtilisateurStatut statut) {
        this.statut = statut;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
}

