package com.example.Projet.Service;

import com.example.Projet.Dto.ProjetDto;
import com.example.Projet.Entities.Projet;
import com.example.Projet.Entities.Utilisateur;
import com.example.Projet.Repository.ProjetRepo;
import com.example.Projet.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProjetServiceImpl implements ProjetService{

    @Autowired
    ProjetRepo projectRepository;
    @Autowired
    UserRepo userRepository;

    @Override
    public List<Projet> getAllProjets() {
        return projectRepository.findAll();

    }
    @Override
    public ProjetDto getProjecById(Long id) {

        Projet projet= projectRepository.findById(id).orElse(null);
      ProjetDto projetDto= convertToDto(projet);
        return  projetDto;
    }
    @Override
    public Projet createProjet(Projet projet, String Createdby) {
        Utilisateur utilisateur = userRepository.findById(Createdby).orElse(null);
        projet.setCreatedBy(utilisateur);
        return projectRepository.save(projet);
    }
    @Override
    public Projet updateProjet(Long id, Projet project) {
        Optional<Projet> existingProjectOptional = projectRepository.findById(id);
        if (existingProjectOptional.isPresent()) {
            Projet existingProject = existingProjectOptional.get();
            existingProject.setNomProjet(project.getNomProjet());
            // Update other fields as needed
            return projectRepository.save(existingProject);
        } else {
            throw new IllegalArgumentException("Project not found with id: " + id);
        }
    }
    @Override
    public void deleteProjet(Long id) {
        projectRepository.deleteById(id);

    }
    public List<Projet> getProjectsByUserId(String userId) {
        return projectRepository.findByUtilisateurs_Id(userId);
    }
    public void assignUserToProject(Long projectId, String userId) {
        Projet projet = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        Utilisateur utilisateur = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        projet.getUtilisateurs().add(utilisateur);
        projectRepository.save(projet);


    }
    public List<Projet> getProjectsByCreator(Utilisateur createdBy) {
        return projectRepository.findByCreatedBy(createdBy);
    }

    @Override
    public int getTaskCountForProject(Long projectId) {
        return 0;// Implementé dans le microservice Tache
    }


    @Override
    public int getTaskCountEncoursForProject(Long projectId) {
        return 0; // Implementé dans le microservice Tache
    }



    @Override
    public int getPhaseCountForProject(Long projectId) {
        return 0; // implementer dans le microservice Phase
    }

    @Override
    public void assignUserToProject(String userId, Long projectId) {
        Utilisateur user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Projet project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        project.getUtilisateurs().add(user);
        user.getProjets().add(project);

        projectRepository.save(project);
        userRepository.save(user);


    }
    public ProjetDto convertToDto(Projet projet) {
        ProjetDto projetDto = new ProjetDto();
        projetDto.setNumProjet(projet.getNumProjet());
        projetDto.setDateCreation(projet.getDateCreation());
        projetDto.setNomProjet(projet.getNomProjet());
        projetDto.setDescriptionProjet(projet.getDescriptionProjet());
        return projetDto;
    }


}
