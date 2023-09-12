package com.example.phase.Dto;

import lombok.Data;

import java.util.List;
@Data
public class PhaseDto {
    private Long id;
    private String nom;
    private Long projetId;
    private List<TacheDto> taches;
}
