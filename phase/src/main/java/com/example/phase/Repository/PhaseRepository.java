package com.example.phase.Repository;

import com.example.phase.Entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    List<Phase> findByProjetId(Long projectId);
    int countByProjetId(Long projectId);


}
