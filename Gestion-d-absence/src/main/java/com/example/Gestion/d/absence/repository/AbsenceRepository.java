package com.example.Gestion.d.absence.repository;

import com.example.Gestion.d.absence.Entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
}
