package com.example.Projet.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(36)")

    private String id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "username")

    private String username;

    @Column(name = "email")

    private String email;

    private Integer congeUtilises;

    @Column(name = "created_timestamp")
    private Long dateCreation;

    @Column(name = "solde_actuel_conges")
    private double soldeActuelConges=0;



    @JsonIgnore
    @ManyToMany(mappedBy = "utilisateurs", fetch = FetchType.EAGER)
    private Set<Projet> projets;


}
