package com.example.phase.Controller;

import com.example.phase.Dto.PhaseDto;
import com.example.phase.Dto.TacheDto;
import com.example.phase.Entity.Phase;
import com.example.phase.Service.PhaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phases")
public class PhaseController {
    private final PhaseService phaseService;

    public PhaseController(PhaseService phaseService) {
        this.phaseService = phaseService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Phase>> getAllPhases() {
        List<Phase> phases = phaseService.getAllPhases();
        return ResponseEntity.ok(phases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhaseDto> getPhaseById(@PathVariable Long id) {
        PhaseDto phase = phaseService.getPhaseById(id);
        return ResponseEntity.ok(phase);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Phase> updatePhase(@PathVariable Long id, @RequestBody Phase updatedPhase) {
        Phase phase = phaseService.updatePhase(id, updatedPhase);
        return ResponseEntity.ok(phase);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhase(@PathVariable Long id) {
        phaseService.deletePhase(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/count")
    public long getNombrePhases() {

        Long nbr = phaseService.getNombrePhases();
        return nbr;
    }
    @GetMapping("/{projectId}/tasks/count")
    public int getNumberOfTasksInProject(@PathVariable("projectId") Long projectId) {
        return phaseService.getNumberOfTasksInProject(projectId);
    }
    @GetMapping("/getFromProject/{projectId}")
    public List<Phase> getPhasesByProjectId(@PathVariable Long projectId) {
        return phaseService.getPhasesByProjectId(projectId);
    }
    @GetMapping("/{phaseId}/get")
    public List<TacheDto> getTasksByPhaseId(@PathVariable Long phaseId) {
        List<TacheDto> taches= phaseService.getTasksByPhaseId(phaseId);
        return taches;

    }

    @PostMapping("/create/{projectId}")
    public ResponseEntity<PhaseDto> createPhaseAndAssignToProjet(@RequestBody PhaseDto phaseDto, @PathVariable Long projectId) {
        try {
            // Create a new Phase entity from the PhaseDto received in the request body
            Phase phase = new Phase();
            phase.setNom(phaseDto.getNom());

            // Call the service method to create and assign the phase to the project
            Phase createdPhase = phaseService.createPhaseAndAssignToProjet(phase, projectId);

            // Convert the createdPhase back to a PhaseDto if needed
            PhaseDto createdPhaseDto = phaseService.convertToDto(createdPhase);

            return new ResponseEntity<>(createdPhaseDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Handle the case where the project with the given ID doesn't exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // You can choose an appropriate HTTP status code
        }
    }
    @GetMapping("/{phaseId}/progress")
    public double calculatePhaseProgress(@PathVariable Long phaseId) {
        return phaseService.calculatePhaseProgress(phaseId);
    }
    @GetMapping("/count/{projectId}")
    public int getPhaseCountForProject(@PathVariable Long projectId) {
        return phaseService.getPhaseCountForProject(projectId);
    }



}
