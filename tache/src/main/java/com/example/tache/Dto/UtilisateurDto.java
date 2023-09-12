package com.example.tache.Dto;

import lombok.Data;

@Data
public class UtilisateurDto {
    private String id;
    private String first_name;
    private String username;
    private String email;
    private Integer congeUtilises;
    private Integer soldeActuelConges;
    private Integer soldeInitialCongee;

}
