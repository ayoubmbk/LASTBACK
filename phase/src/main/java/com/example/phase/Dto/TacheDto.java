package com.example.phase.Dto;

import com.example.phase.enumeration.Niveau;
import com.example.phase.enumeration.Situation;
import com.example.phase.enumeration.Status;
import lombok.Data;

@Data
public class TacheDto {
    private Long idTache;
    private Status status;
    private Situation situation;
    private Niveau nivTache;
    private String nomTache;
    private Long phaseId;
    private Long projetId;
}
