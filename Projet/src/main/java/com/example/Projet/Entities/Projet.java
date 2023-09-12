package com.example.Projet.Entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projetId;
    private Long numProjet;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    private String nomProjet;
    private String descriptionProjet;



    @ManyToOne
    @JoinColumn(name = "created_by")
    private Utilisateur createdBy;
    @ManyToMany
    @JoinTable(name = "utilisateur_projet",
            joinColumns = @JoinColumn(name = "id_projet"),
            inverseJoinColumns = @JoinColumn(name = "id_utilisateur"))
    private Set<Utilisateur> utilisateurs;


}