package de.umfeldradar.benutzer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityAccessTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void redirectToLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
    @Test
    void rejectExpertOnAdmin() throws Exception {
        mockMvc.perform(get("/admin/test")
                .with(user("experte").roles("EXPERTE")))
                .andExpect(status().isForbidden());
    }
    @Test
    void allowAdminonAdmin() throws Exception {
        mockMvc.perform(get("/admin/test")
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound());
    }
}
