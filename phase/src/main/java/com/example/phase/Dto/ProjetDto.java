package com.example.phase.Dto;


import lombok.Data;

import java.util.Date;
@Data
public class ProjetDto {
    private Long projetId;
    private Long numProjet;
    private Date dateCreation;
    private String nomProjet;
    private String descriptionProjet;
    private String createdBy;
    private String userId;

}
