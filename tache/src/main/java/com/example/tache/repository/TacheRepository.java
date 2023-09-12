package com.example.tache.repository;

import com.example.tache.entitie.Tache;
import com.example.tache.enumeration.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache,Long>

{
    @Query("SELECT t FROM Tache t WHERE t.projetId = :projectId")
    List<Tache> findTasksByProjectId(@Param("projectId") Long projectId);
    List<Tache> findByCreatedBy(String createdByUserId);


    int countBySituationAndCreatedBy(Situation situation, String createdBy);

    @Query("SELECT t FROM Tache t WHERE t.dateEchanche < current_timestamp AND t.situation ='EnCours' AND t.createdBy = :createdById")
    List<Tache> findEnRetardByCreatedBy(@Param("createdById") String createdById);

    @Query("SELECT t FROM Tache t WHERE t.situation = 'Termine' AND t.createdBy = :createdById")
    List<Tache> findTermineByCreatedBy(@Param("createdById") String createdById);
    ;
    @Query("SELECT t FROM Tache t WHERE t.createdBy = :userId AND (t.userId <> :userId OR t.userId IS NOT NULL)")
    List<Tache> findTasksCreatedByOtherUsers(String userId);
    List<Tache> findByPhaseId(Long phaseId);

    @Query("SELECT COUNT(t) FROM Tache t WHERE t.projetId = :projectId")
    int getTaskCountForProject(Long projectId);

    @Query("SELECT COUNT(t) FROM Tache t WHERE t.projetId = :projectId AND t.situation = 'EnCours'")
    int getTaskCountEncoursForProject(Long projectId);
    @Query("SELECT t FROM Tache t WHERE t.situation = 'EnCours' AND t.createdBy = :createdById")
    List<Tache> findEncoursByCreatedBy(@Param("createdById") String createdById);


}
