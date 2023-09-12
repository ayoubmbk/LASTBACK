package com.example.tache.controller;

import com.example.tache.Dto.TacheDto;
import com.example.tache.entitie.Tache;
import com.example.tache.enumeration.Situation;
import com.example.tache.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/taches")
public class TacheController {

    @Autowired
    TacheService tacheService;
    @PostMapping("add/{phaseId}/{Createdby}")
    public TacheDto createTacheAndAssignToPhaseAndUser(@PathVariable Long phaseId, @RequestBody Tache tache, @PathVariable String Createdby) {
        tache.setSituation(Situation.EnCours);
        Tache createdTache = tacheService.createTacheAndAssignToPhase(phaseId, tache,Createdby);
        return tacheService.convertToDto(createdTache);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tache> getTacheById(@PathVariable Long id) {
        Tache tache = tacheService.getTacheById(id);
        return ResponseEntity.ok(tache);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tache>> getAllTaches() {
        List<Tache> taches = tacheService.getAllTaches();
        return ResponseEntity.ok(taches);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable Long id, @RequestBody Tache tache) {
        if (!id.equals(tache.getIdTache())) {
            throw new IllegalArgumentException("ID in URL path and Tache ID do not match");
        }

        Tache updatedTache = tacheService.updateTache(tache);
        return ResponseEntity.ok(updatedTache);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        tacheService.deleteTache(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{projectId}/tasks")
    public List<Tache> getTasksByProjectId(@PathVariable Long projectId) {
        return tacheService.getTasksByProjectId(projectId);
    }
    @GetMapping("creator/{creatorId}")
    public List<Tache> getTachesByCreator(@PathVariable("creatorId") String creatorId) {

        return tacheService.getTasksByCreator(creatorId);
    }
    @GetMapping("/nb-taches-termine/{createdBy}")
    public int getNombreTachesTermineesByCreatedBy(@PathVariable String createdBy) {
        return tacheService.getNombreTachesTermineesByCreatedBy(createdBy);
    }
    @PostMapping("/addTacheToCreator/{userId}")
    public ResponseEntity<Tache> addTacheWithUser(@RequestBody Tache tache, @PathVariable String userId) {
        Tache addedTache = tacheService.createTacheToCreator(userId,tache);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTache);
    }
    @PostMapping("/assignToPhase/{phaseId}")
    public ResponseEntity<Tache> assignTacheToPhase(@RequestBody Tache tache, @PathVariable Long phaseId) {
        Tache assignedTache = tacheService.assignTacheToPhase(tache, phaseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignedTache);
    }
    @GetMapping("/tacheassignedto/{userId}")
    public List<Tache> getTasksAssignedtootheruser(@PathVariable String userId)  {
        return tacheService.getTasksCreatedByOtherUsers(userId);
    }
    @PostMapping("/assignToProjet/{projetId}")
    public ResponseEntity<Tache> assignTacheToProjet(@RequestBody Tache tache, @PathVariable Long projetId) {
        Tache assignedTache = tacheService.assignTacheToProject(tache, projetId);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignedTache);
    }

    @PostMapping("/assignToProjetPhase/{tacheId}/{projetId}/{phaseId}")
    public ResponseEntity<Tache> assignTacheToProjetandPhase(@PathVariable Long tacheId, @PathVariable Long projetId,@PathVariable Long phaseId) {
        Tache assignedTache = tacheService.assignTacheToProjectandPhase(tacheId, projetId,phaseId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(assignedTache);
    }
    @GetMapping("/enretard/{createdById}")
    public List<Tache> getTachesEnRetardByCreatedBy(@PathVariable String createdById) {
        return tacheService.getTachesEnRetardByCreatedBy(createdById);
    }
    @GetMapping("/termine/{createdById}")
    public List<Tache> getTachesTermineByCreatedBy(@PathVariable String createdById) {
        return tacheService.getTachesTermineByCreatedBy(createdById);
    }

    @GetMapping("/byPhase/{phaseId}")
    public List<TacheDto> getTasksByPhaseId(@PathVariable Long phaseId) {
        // Implement the logic to fetch tasks by phase ID from your database
        return tacheService.getTasksByPhaseId(phaseId);
    }
    @GetMapping("/{phaseId}/progress")
    public double calculatePhaseProgress(@PathVariable Long phaseId) {
        return tacheService.calculatePhaseProgress(phaseId);
    }
    @GetMapping("/{projectId}/taskcount")
    public int getTaskCountForProject(@PathVariable Long projectId) {
        return tacheService.getTaskCountForProject(projectId);
    }
    @GetMapping("/countEnCours/{projectId}")
    public int getTaskCountEncoursForProject(@PathVariable Long projectId) {
        return tacheService.getTaskCountEncoursForProject(projectId);
    }
    @GetMapping("/Encours/{createdById}")
    public List<Tache> getTachesEncoursByCreatedBy(@PathVariable String createdById) {
        return tacheService.getTachesEncoursByCreatedBy(createdById);
    }


}
