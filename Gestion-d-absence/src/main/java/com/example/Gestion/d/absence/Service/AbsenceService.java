package com.example.Gestion.d.absence.Service;

import com.example.Gestion.d.absence.Dto.UtilisateurDto;
import com.example.Gestion.d.absence.Entities.Absence;
import com.example.Gestion.d.absence.FeignClient.DemoFeignClient;
import com.example.Gestion.d.absence.enumera.StatutAbsence;
import com.example.Gestion.d.absence.exception.AbsenceNotFoundException;
import com.example.Gestion.d.absence.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;


@Service
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final DemoFeignClient demoFeignClient;
    private final RestTemplate restTemplate;
    private static final String USER_BASE_URL = "http://localhost:9091";

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, DemoFeignClient demoFeignClient, RestTemplate restTemplate) {
        this.absenceRepository = absenceRepository;
        this.demoFeignClient = demoFeignClient;
        this.restTemplate = restTemplate;
    }

    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    public Absence saveAbsence(Absence absence) {
        return absenceRepository.save(absence);
    }



    // Service
    public Absence approuverAbsence(Long absenceId) {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() -> new AbsenceNotFoundException(absenceId));

        if (absence.getStatut() == StatutAbsence.EN_ATTENTE) {
            // Vérifier le solde actuel de congés de l'utilisateur via le Feign Client
            Integer dureeEnJours = absence.getDureeEnJours();
            String userId = absence.getUserId();
            UtilisateurDto userData = restTemplate.getForObject(
                    USER_BASE_URL + "/users/userById/{userId}",
                    UtilisateurDto.class,
                    userId
            );

            if (userData != null) {
                Integer soldeConges = userData.getSoldeActuelConges();

                if (dureeEnJours <= soldeConges) {
                    // Mettre à jour le statut de l'absence
                    absence.setStatut(StatutAbsence.APPROUVE);

                    try {
                        // Mettre à jour le solde actuel de congés de l'utilisateur via le Feign Client
                        demoFeignClient.updateSoldeActuelConges(userId, dureeEnJours);
                    } catch (Exception e) {
                        // En cas d'échec de la mise à jour du solde, annuler l'approbation de l'absence
                        absence.setStatut(StatutAbsence.EN_ATTENTE);
                        throw new IllegalStateException("Échec de la mise à jour du solde de congés.");
                    }

                    return absenceRepository.save(absence);
                } else {
                    throw new IllegalStateException("Le nombre de jours demandés dépasse le solde de congés restant.");
                }
            } else {
                throw new IllegalStateException("Utilisateur non trouvé.");
            }
        } else {
            throw new IllegalStateException("L'absence ne peut pas être approuvée car elle n'est pas en attente.");
        }
    }


    public Absence rejeterAbsence(Long absenceId) {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() -> new AbsenceNotFoundException(absenceId));

        absence.setStatut(StatutAbsence.REJETE);
        return absenceRepository.save(absence);
    }

    public Absence addAbsence(Absence absence, String userId) {
        System.out.println("dkhal lel  add service ");
        UtilisateurDto userData = restTemplate.getForObject(
                USER_BASE_URL + "/users/userById/{userId}",
                UtilisateurDto.class,
                userId
        );
        System.out.println(userData.getId()+"SERS");
        absence.setUserId(userData.getId());
        absence.setStatut(StatutAbsence.EN_ATTENTE);

        return absenceRepository.save(absence);
    }
    public Absence getAbsenceById(Long absenceId) {
        Absence absence= absenceRepository.findById(absenceId).orElse(null);
        return absence ;

    }

}

