package com.example.Projet.Dto;

import com.example.Projet.enumeration.Niveau;
import com.example.Projet.enumeration.Status;
import lombok.Data;

@Data
public class TacheDto {
    private Long idTache;
    private Status status;
    private Niveau nivTache;
    private String nomTache;
    private Long phaseId;
    private Long projetId;
}
