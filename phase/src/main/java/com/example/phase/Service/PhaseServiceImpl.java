package com.example.phase.Service;

import com.example.phase.Dto.PhaseDto;
import com.example.phase.Dto.ProjetDto;
import com.example.phase.Dto.TacheDto;
import com.example.phase.Entity.Phase;
import com.example.phase.FeignClient.ProjetFeignClient;
import com.example.phase.FeignClient.TaskFeignClient;
import com.example.phase.Repository.PhaseRepository;
import com.example.phase.enumeration.Situation;
import com.example.phase.enumeration.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service

public class PhaseServiceImpl implements PhaseService{
    @Autowired
    PhaseRepository phaseRepository;
    @Autowired
    TaskFeignClient taskFeignClient;
    @Autowired
    ProjetFeignClient projetFeignClient;

    @Override
    public PhaseDto getPhaseById(Long id) {

        Phase phase= phaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phase not found with id: " + id));
        PhaseDto phaseDto=convertToDto(phase);

        return phaseDto;


    }

    @Override
    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }

    @Override
    public List<Phase> getPhasesByProjectId(Long projectId) {
        return phaseRepository.findByProjetId(projectId);
    }

    @Override
    public Phase createPhaseAndAssignToProjet(Phase phase, Long projectId) {
        ProjetDto project = projetFeignClient.getProjetById(projectId);

        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found");
        }

        phase.setProjetId(projectId);
        Phase createdPhase = phaseRepository.save(phase);

        return createdPhase;
    }


    @Override
    public Phase updatePhase(Long id, Phase updatedPhase) {
        Optional<Phase> phaseOptional = phaseRepository.findById(id);

        if (phaseOptional.isPresent()) {
            Phase phase = phaseOptional.get();
            phase.setNom(updatedPhase.getNom());
            return phaseRepository.save(phase);
        } else {
            // Handle the case where the phase with the given ID does not exist.
            throw new EntityNotFoundException("Phase not found with ID: " + id);
        }
    }
    @Override
    public void deletePhase(Long id) {
        Phase phase = phaseRepository.findById(id).orElse(null);
        phaseRepository.delete(phase);
    }




    @Override
    public long getNombrePhases() {
        return phaseRepository.count();

    }


    @Override
    public int getNumberOfTasksInProject(Long projectId) {
        return 0;
    }

    @Override
    public List<TacheDto> getTasksByPhaseId(Long phaseId) {
        Phase phase = phaseRepository.findById(phaseId).orElseThrow(EntityNotFoundException::new);

        return taskFeignClient.getTasksByPhaseId(phaseId);
    }
    @Override
    public double calculatePhaseProgress(Long phaseId) {
        // Delegate the call to the Tache microservice using Feign Client
        return taskFeignClient.calculatePhaseProgress(phaseId);
    }
    @Override
    public PhaseDto convertToDto(Phase phase) {
        PhaseDto phaseDto = new PhaseDto();
        phaseDto.setId(phase.getId());
        phaseDto.setNom(phase.getNom());
        phaseDto.setProjetId(phase.getProjetId());

        // You can also set other properties if needed

        return phaseDto;
    }
    @Override
    public int getPhaseCountForProject(Long projectId) {
        return phaseRepository.countByProjetId(projectId);
    }
}
