package de.umfeldradar.benutzer;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.UUID;
import jakarta.persistence.Table;

@Entity
@Table(name= "users")
public class Benutzer {
    @Id
    private UUID id;
    private String email;
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String displayName;
    private boolean active;
    public Benutzer(){

    }
}