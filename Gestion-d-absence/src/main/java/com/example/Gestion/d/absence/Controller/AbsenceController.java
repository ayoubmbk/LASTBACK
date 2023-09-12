package com.example.Gestion.d.absence.Controller;

import com.example.Gestion.d.absence.Entities.Absence;
import com.example.Gestion.d.absence.Service.AbsenceService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/absence")
public class AbsenceController {
    private final RestTemplate restTemplate;
    @Autowired
    private AbsenceService absenceService;

    public AbsenceController(RestTemplate restTemplate, AbsenceService absenceService) {
        this.restTemplate = restTemplate;
        this.absenceService = absenceService;
    }

    @PostMapping("/approuver/{absenceId}")
    public ResponseEntity<String> approuverAbsence(@PathVariable Long absenceId) {
        System.out.println("dkhal lel approuver");
        try {
            Absence absence = absenceService.approuverAbsence(absenceId);

            return ResponseEntity.ok("L'absence a été approuvée avec succès.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("L'absence a déjà été approuvée.");
        }
    }

    @PostMapping("/rejeter/{absenceId}")
    public Absence rejeterAbsence(@PathVariable Long absenceId) {
        return absenceService.rejeterAbsence(absenceId);
    }


    @PostMapping("/add/{userId}")
    public ResponseEntity<Absence> addAbsence(@RequestBody Absence absence,@PathVariable  String userId) {
        System.out.println("dkhal lel add");
        Absence createdAbsence = absenceService.addAbsence(absence,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAbsence);
    }
    @GetMapping("/get/{absenceId}")
    public Absence getAbsence(@PathVariable Long absenceId ) {
        return absenceService.getAbsenceById(absenceId);
    }
}
