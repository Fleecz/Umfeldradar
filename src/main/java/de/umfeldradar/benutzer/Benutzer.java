package de.umfeldradar.benutzer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Entity
@Table(name= "users")
public class Benutzer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String displayName;
    private boolean active;
    public Benutzer(){
    }
    public Benutzer(boolean active, String email, String passwordHash, Role role, String displayName) {
        this.active = active;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.displayName = displayName;
    }
    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public Role getRole(){
        return role;
    }

    public String getEmail() {
        return email;
    }

    public @Nullable String getPasswordHash() {
        return passwordHash;
    }
}