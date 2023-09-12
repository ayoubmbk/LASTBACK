package com.example.Gestion.d.absence.Entities;

import com.example.Gestion.d.absence.enumera.StatutAbsence;
import com.example.Gestion.d.absence.enumera.TypeAbsence;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId; // ID de l'employé concerné par l'absence
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;
    @NotNull
    private Integer dureeEnJours;

    @Enumerated(EnumType.STRING)
    private TypeAbsence type;
    @Enumerated(EnumType.STRING)
    private StatutAbsence statut;

    // Autres propriétés et méthodes
}






