package com.example.Projet.Controller;

import com.example.Projet.Entities.Utilisateur;
import com.example.Projet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Utilisateur> createProject(@RequestBody Utilisateur user) {
        Utilisateur  createdProject = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }
    @GetMapping("/userById/{userId}")
    public Utilisateur getUserById(@PathVariable String userId ) {
        return userService.getUserById(userId);
    }
    @GetMapping("/allusers")
    public List<Utilisateur> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/updateSoldeActuelConges/{userId}/{dureeEnJours}")
    public ResponseEntity<String> updateSoldeActuelConges(
            @PathVariable String userId,
            @PathVariable Integer dureeEnJours) {
        try {
            userService.updateSoldeActuelCongee(userId, dureeEnJours);
            return ResponseEntity.ok("Le solde actuel de congés a été mis à jour avec succès.");
        } catch (IllegalAccessError e) {
            return ResponseEntity.notFound().build();
        }
    }


}
