package com.example.tache.service;

import com.example.tache.Dto.PhaseDto;
import com.example.tache.Dto.ProjetDto;
import com.example.tache.Dto.TacheDto;
import com.example.tache.Dto.UtilisateurDto;
import com.example.tache.FeignClient.PhaseFeignClient;
import com.example.tache.FeignClient.ProjetFeignClient;
import com.example.tache.FeignClient.UserFeignClient;
import com.example.tache.entitie.Tache;
import com.example.tache.enumeration.Situation;
import com.example.tache.enumeration.Status;
import com.example.tache.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TacheServiceImpl implements TacheService{
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    TacheRepository tacheRepository;
    @Autowired
    PhaseFeignClient phaseFeignClient;
    @Autowired
    ProjetFeignClient projetFeignClient;

    @Override
    public Tache createTacheAndAssignToPhase(Long phaseId, Tache tache, String createdByUserId) {
        // Utilisez les Feign Clients pour obtenir les informations de la phase et de l'utilisateur
        PhaseDto phaseDto = phaseFeignClient.getPhaseById(phaseId);
        System.out.println(phaseDto + " oussemaaaaaaaa");
        UtilisateurDto createdByUser = userFeignClient.getUserById(createdByUserId);
        System.out.println(createdByUser + "ayouuuuuuuub");

        // Vérifiez si la phase existe et si l'utilisateur existe
        if (phaseDto != null && createdByUser != null) {
            tache.setPhaseId(phaseDto.getId());
            tache.setCreatedBy(createdByUser.getId());
            return tacheRepository.save(tache);
        } else {
            // Gérez le cas où la phase ou l'utilisateur n'a pas été trouvé
            throw new IllegalArgumentException("La phase ou l'utilisateur spécifié n'existe pas.");
        }
    }



    @Override
    public Tache getTacheById(Long id) {
        return tacheRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tache not found"));
    }

    @Override
    public List<Tache> getAllTaches() {

        return tacheRepository.findAll();
    }

    @Override
    public Tache updateTache(Tache tache) {

        return tacheRepository.save(tache);
    }

    @Override
    public boolean updateTaskStatus(Long taskId) {
        Optional<Tache> optionalTask = tacheRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Tache task = optionalTask.get();
            task.setSituation(Situation.Termine);
            tacheRepository.save(task);
            return true;
        }
        return false;
    }
    @Override
    public void deleteTache(Long id) {

        tacheRepository.deleteById(id);
    }

    @Override
    public void assignTacheToUser(Long tacheId, String userId) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new EntityNotFoundException("Tache not found"));

        try {
            UtilisateurDto userDto = userFeignClient.getUserById(userId);
            System.out.println(userDto + " LOOL");
            tache.setUserId(userId);
            tache.setStatus(Status.assigned); // Update the task status to ASSIGNED
            tacheRepository.save(tache);
        } catch (Exception e) {
            // If an exception occurs while fetching the user, you can throw a UserNotFoundException or handle it accordingly.
            throw new IllegalArgumentException("User not found with ID: " + userId, e);
        }
    }


    @Override
    public long getNombreTaches() {
        return tacheRepository.count();

    }
    public TacheDto convertToDto(Tache tache) {
        TacheDto dto = new TacheDto();
        dto.setIdTache(tache.getIdTache());
        dto.setStatus(tache.getStatus());
        dto.setNivTache(tache.getNivTache());
        dto.setNomTache(tache.getNomTache());
        dto.setPhaseId(tache.getPhaseId());
        dto.setProjetId(tache.getProjetId());
        return dto;
    }


    @Override
    public List<Tache> getTasksByProjectId(Long projectId) {
        return tacheRepository.findTasksByProjectId(projectId);
    }
    @Override
    public List<Tache> getTasksByCreator(String createdBy) {
        return tacheRepository.findByCreatedBy(createdBy);
    }

    @Override
    public int getNombreTachesTermineesByCreatedBy(String createdByUserId) {
        return tacheRepository.countBySituationAndCreatedBy(Situation.Termine, createdByUserId);
    }

    @Override
    public Tache createTacheToCreator(String createdBy,Tache tache) {
        UtilisateurDto userDto=userFeignClient.getUserById(createdBy);
        tache.setCreatedBy( createdBy);
        tache.setSituation(Situation.EnCours);

        return tacheRepository.save(tache);
    }


    @Override
    public Tache assignTacheToPhase(Tache tache, Long phaseId) {
        try {
            PhaseDto phase = phaseFeignClient.getPhaseById(phaseId);
            if (phase == null) {
                throw new IllegalArgumentException("Phase with ID " + phaseId + " not found");
            }

            tache.setPhaseId(phaseId);
            return tacheRepository.save(tache);
        } catch (Exception e) {
            // Handle the exception here, you can log it or throw a custom exception
            throw new RuntimeException("Failed to assign tache to phase", e);
        }
    }




    @Override
    public List<Tache> getTasksCreatedByOtherUsers(String userId) {
        return tacheRepository.findTasksCreatedByOtherUsers(userId);
    }

    @Override
    public Tache assignTacheToProject(Tache tache, Long projetId) {
        ProjetDto projet = projetFeignClient.getProjetById(projetId);

        if (projet == null) {
            // Project with the given projetId does not exist, throw an exception
            throw new IllegalArgumentException("Project not found with ID: " + projetId);
        }

        tache.setProjetId(projetId);
        return tacheRepository.save(tache);
    }

    @Override
    public Tache assignTacheToProjectandPhase(Long tacheId, Long projetId, Long phaseId) {
        try {
            ProjetDto projet = projetFeignClient.getProjetById(projetId);

            if (projet == null) {
                throw new IllegalArgumentException("Projet with ID " + projetId + " not found");
            }

            Tache tache = tacheRepository.findById(tacheId)
                    .orElseThrow(() -> new EntityNotFoundException("Tache not found"));

            PhaseDto phase = phaseFeignClient.getPhaseById(phaseId);

            if (phase == null) {
                throw new IllegalArgumentException("Phase with ID " + phaseId + " not found");
            }

            // Check if the phase belongs to the specified projet
            if (!phase.getProjetId().equals(projetId)) {
                throw new IllegalArgumentException("Phase with ID " + phaseId + " does not belong to Projet with ID " + projetId);
            }

            tache.setProjetId(projetId);
            tache.setPhaseId(phaseId);
            return tacheRepository.save(tache);
        } catch (Exception e) {
            // Handle the exception here, you can log it or throw a custom exception
            throw new RuntimeException("Failed to assign tache to projet and phase", e);
        }
    }


    @Override
    public List<Tache> getTachesEnRetardByCreatedBy(String createdById) {
        return tacheRepository.findEnRetardByCreatedBy(createdById);
    }
    @Override
    public List<Tache> getTachesTermineByCreatedBy(String createdById) {
        return tacheRepository.findTermineByCreatedBy(createdById);
    }

    @Override
    public void RemoveTacheFromPhase(Long phaseId, Long taskId) {
        PhaseDto phase = phaseFeignClient.getPhaseById(phaseId);


        Tache task = tacheRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        if (task.getPhaseId() != null && task.getPhaseId().equals(phaseId)) {
            task.setPhaseId(null);
            tacheRepository.save(task);
        }
    }

    @Override
    public List<TacheDto> getTasksByPhaseId(Long phaseId) {
        // Assuming you have a Task entity that is mapped to your database table
        List<Tache> tasks = tacheRepository.findByPhaseId(phaseId);

        // You need to convert your Task entities to TacheDto objects here if needed
        List<TacheDto> taskDtos = convertToDto(tasks);

        return taskDtos;
    }
    private List<TacheDto> convertToDto(List<Tache> tasks) {
        // Implement the conversion logic here
        // Example:
        List<TacheDto> taskDtos = new ArrayList<>();
        for (Tache task : tasks) {
            TacheDto taskDto = new TacheDto();
            taskDto.setIdTache(task.getIdTache());
            taskDto.setStatus(task.getStatus());
            taskDto.setSituation(task.getSituation());
            taskDto.setNomTache(task.getNomTache());
            taskDto.setNivTache(task.getNivTache());
            taskDto.setPhaseId(task.getPhaseId());
            taskDto.setProjetId(task.getProjetId());
            // Set other properties as needed
            taskDtos.add(taskDto);
        }
        return taskDtos;

    }
    @Override
    public double calculatePhaseProgress(Long phaseId) {
        // In this example, we assume that you have a list of tasks for the phase.
        // Replace this with your actual logic to fetch tasks from the database.
        List<TacheDto> tasks =getTasksByPhaseId(phaseId); // Fetch tasks for the given phaseId from the database.

        if (tasks == null || tasks.isEmpty()) {
            return 0.0; // No tasks, so progress is 0%.
        }

        int totalTasks = tasks.size();
        int completedTasks = 0;

        for (TacheDto task : tasks) {
            // Check if the task's situation is "Terminé" to count completed tasks.
            if (task.getSituation() == Situation.Termine) {
                completedTasks++;
            }
        }

        // Calculate the progress as a percentage.
        return ((double) completedTasks / totalTasks) * 100.0;
    }

    @Override
    public int getTaskCountForProject(Long projectId) {
        return tacheRepository.getTaskCountForProject(projectId);
    }
    @Override
    public int getTaskCountEncoursForProject(Long projectId) {
        return tacheRepository.getTaskCountEncoursForProject(projectId);
    }

    @Override
    public List<Tache> getTachesEncoursByCreatedBy(String createdById) {
        return tacheRepository.findEncoursByCreatedBy(createdById);
    }
}

