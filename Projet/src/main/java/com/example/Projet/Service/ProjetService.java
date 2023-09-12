package com.example.Projet.Service;

import com.example.Projet.Dto.ProjetDto;
import com.example.Projet.Entities.Projet;
import com.example.Projet.Entities.Utilisateur;
import java.util.List;
import java.util.Optional;

public interface ProjetService {

    List<Projet> getAllProjets();
   ProjetDto getProjecById(Long id);
    Projet createProjet(Projet projet,String Createdby);

    /*Projet createProjet(Projet projet);*/

    Projet updateProjet(Long id, Projet projet);
    void deleteProjet(Long id);
    //void assignProjectToUser(Long projectId, String userId);
    public List<Projet> getProjectsByUserId(String userId);

    void assignUserToProject(Long projectId, String userId);
    public void assignUserToProject(String userId, Long projectId);
    public List<Projet> getProjectsByCreator(Utilisateur createdBy);
    int getTaskCountForProject(Long projectId);
    int getTaskCountEncoursForProject(Long projectId) ;


    int getPhaseCountForProject(Long projectId) ;
}

