package com.example.tache.Dto;

import com.example.tache.enumeration.Niveau;
import com.example.tache.enumeration.Situation;
import com.example.tache.enumeration.Status;
import lombok.Data;

@Data
public class TacheDto {
    private Long idTache;
    private Status status;
    private Niveau nivTache;
    private String nomTache;
    private Situation situation;
    private Long phaseId;
    private Long projetId;
}
