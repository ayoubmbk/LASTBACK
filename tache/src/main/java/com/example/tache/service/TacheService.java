package com.example.tache.service;

import com.example.tache.Dto.TacheDto;
import com.example.tache.entitie.Tache;

import java.util.List;

public interface TacheService {
    Tache createTacheAndAssignToPhase(Long phaseId, Tache tache, String createdBy);

    Tache getTacheById(Long id);

    List<Tache> getAllTaches();

    Tache updateTache(Tache tache);

    boolean updateTaskStatus(Long taskId);

    void deleteTache(Long id);

    void assignTacheToUser(Long tacheId, String userId);

    long getNombreTaches();


    TacheDto convertToDto(Tache tache);

    List<Tache> getTasksByProjectId(Long projectId);

    List<Tache> getTasksByCreator(String createdBy);

    int getNombreTachesTermineesByCreatedBy(String createdBy);

    public Tache createTacheToCreator(String createdBy, Tache tache);


    Tache assignTacheToPhase(Tache tache, Long phaseId);

    List<Tache> getTasksCreatedByOtherUsers(String userId);

    Tache assignTacheToProject(Tache tache, Long projetId);

    Tache assignTacheToProjectandPhase(Long tacheId, Long projetId, Long phaseId);

    List<Tache> getTachesEnRetardByCreatedBy(String createdById);

    List<Tache> getTachesTermineByCreatedBy(String createdById);
    void RemoveTacheFromPhase(Long phaseId, Long taskId);
    public List<TacheDto> getTasksByPhaseId(Long phaseId);
    public double calculatePhaseProgress(Long phaseId);
    int getTaskCountForProject(Long projectId);

    int getTaskCountEncoursForProject(Long projectId);
    List<Tache> getTachesEncoursByCreatedBy(String createdById) ;
}




