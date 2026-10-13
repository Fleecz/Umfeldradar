package de.umfeldradar.benutzer;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BenutzerUserDetailsService implements UserDetailsService {
    private final BenutzerRepository benutzerRepository;
    public BenutzerUserDetailsService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Benutzer benutzer = benutzerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden"));
                if(benutzer.getRole() == Role.AGENT){
                    throw new UsernameNotFoundException("Benutzer nicht gefunden");
                }
        return User.builder()
                .username(benutzer.getEmail())
                .password(benutzer.getPasswordHash())
                .roles(benutzer.getRole().name())
                .disabled(!benutzer.isActive())
                .build();
    }
}