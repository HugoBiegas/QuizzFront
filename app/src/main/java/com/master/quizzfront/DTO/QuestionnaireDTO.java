package com.master.quizzfront.DTO;

import java.io.Serializable;
import java.util.Date;

public class QuestionnaireDTO implements Serializable {
    private Integer id;
    private String titre;
    private String description;
    private Date dateCreation;
    private UtilisateurDTO creePar;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public UtilisateurDTO getCreePar() { return creePar; }
    public void setCreePar(UtilisateurDTO creePar) { this.creePar = creePar; }
}