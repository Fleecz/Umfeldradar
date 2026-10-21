package de.umfeldradar.thema;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ThemaControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void adminKannThemenlisteAufrufen() throws Exception {
        mockMvc.perform(get("/admin/themen")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("themen/liste"));
    }

    @Test
    void testePestel() throws Exception {
        mockMvc.perform(get("/admin/themen")
                        .param("pestel", "TECHNOLOGISCH")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }
}