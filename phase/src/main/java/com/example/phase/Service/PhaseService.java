package com.example.phase.Service;

import com.example.phase.Dto.PhaseDto;
import com.example.phase.Dto.TacheDto;
import com.example.phase.Entity.Phase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhaseService {
    PhaseDto getPhaseById(Long phaseId);

    List<Phase> getAllPhases();

    List<Phase> getPhasesByProjectId(Long projectId);

    Phase createPhaseAndAssignToProjet(Phase phase,Long projectId);

    Phase updatePhase(Long phaseId, Phase phase);

    void deletePhase(Long phaseId);



    long getNombrePhases();

    int getNumberOfTasksInProject(Long projectId) ;
    List<TacheDto> getTasksByPhaseId(Long phaseId) ;
    double calculatePhaseProgress(Long phaseId) ;
    public PhaseDto convertToDto(Phase phase);
    int getPhaseCountForProject(Long projectId);


}
