package com.example.Projet.Dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
@Data

public class ProjetDto {
    private Long numProjet;
    private Date dateCreation;
    private  String nomProjet;
    private String descriptionProjet;
    private List<UtilisateurDto> utilisateurs;
    private String createdBy;
    List<TacheDto> taches;



}
