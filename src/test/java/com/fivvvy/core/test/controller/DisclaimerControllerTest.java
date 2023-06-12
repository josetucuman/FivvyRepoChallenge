package com.fivvvy.core.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivvvy.core.controller.DisclaimerController;
import com.fivvvy.core.dto.DisclaimerDto;
import com.fivvvy.core.model.Disclaimer;
import com.fivvvy.core.service.DisclaimerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DisclaimerController.class)
@RunWith(SpringRunner.class)


public class DisclaimerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DisclaimerService service;

    @Test
    public void createDisclaimer() throws Exception {
        DisclaimerDto newDisclaimer = new DisclaimerDto();
        newDisclaimer.setName("Test Disclaimer");
        newDisclaimer.setText("This is a test disclaimer.");
        newDisclaimer.setVersion("1.0");

        when(service.createDisclaimer(newDisclaimer)).thenReturn(new Disclaimer());

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(newDisclaimer);

        mvc.perform(MockMvcRequestBuilders.post("/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void createDisclaimer_throwsException() throws Exception {
        DisclaimerDto newDisclaimer = new DisclaimerDto();
        newDisclaimer.setName("Test Disclaimer");
        newDisclaimer.setText("This is a test disclaimer.");
        newDisclaimer.setVersion("1.0");

        when(service.createDisclaimer(newDisclaimer)).thenThrow(new RuntimeException());


        mvc.perform(MockMvcRequestBuilders.post("/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newDisclaimer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllDisclaimer() throws Exception {

        Disclaimer disclaimer1 = new Disclaimer();
        disclaimer1.setName("Disclaimer 1");
        disclaimer1.setText("This is disclaimer 1.");
        disclaimer1.setVersion("1.0.0");

        Disclaimer disclaimer2 = new Disclaimer();
        disclaimer2.setName("Disclaimer 2");
        disclaimer2.setText("This is disclaimer 2.");
        disclaimer2.setVersion("1.0.1");

        when(service.getAllDisclaimers()).thenReturn(Arrays.asList(disclaimer1, disclaimer2));

        mvc.perform(MockMvcRequestBuilders.get("/disclaimers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":null,\"name\":\"Disclaimer 1\",\"text\":" +
                        "\"This is disclaimer 1.\",\"version\":\"1.0.0\"},{\"id\":null,\"name\":\"Disclaimer 2\"," +
                        "\"text\":\"This is disclaimer 2.\",\"version\":\"1.0.1\"}]"));

    }

    @Test
    public void getDisclaimerById() throws Exception {
        Disclaimer disclaimer = new Disclaimer(1, "Disclaimer 1",
                "This is disclaimer 1.", "1.0.0");

        when(service.getDisclaimerById(1)).thenReturn(Optional.of(disclaimer));

        mvc.perform(MockMvcRequestBuilders.get("/disclaimers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Disclaimer 1\"," +
                        "\"text\":\"This is disclaimer 1.\"}"));
    }

    @Test
    public void getDisclaimerByText() throws Exception {
        Disclaimer disclaimer = new Disclaimer(1, "Disclaimer 1",
                "This is disclaimer 1.", "1.0.0");

        List<Disclaimer> disclaimerList = Collections.singletonList(disclaimer);

        when(service.getAllDisclaimersByText("This is disclaimer 1.")).thenReturn(disclaimerList);

        mvc.perform(MockMvcRequestBuilders.get("/disclaimers?text=This is disclaimer 1."))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Disclaimer 1\"," +
                        "\"text\":\"This is disclaimer 1.\"}]"));
    }
    @Test
    public void updateDisclaimer() throws Exception {
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setName("Updated Disclaimer");
        disclaimer.setText("This is an updated disclaimer.");
        disclaimer.setVersion("1.0.1");

        Optional<Disclaimer> existingDisclaimer = service.getDisclaimerById(1);

        if (existingDisclaimer.isPresent()) {
            when(service.updateDisclaimer(disclaimer)).thenReturn(existingDisclaimer.get());

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(disclaimer);

            mvc.perform(MockMvcRequestBuilders.put("/disclaimers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        } else {
            System.out.println("No disclaimer found with id 1");
        }
    }

    @Test
    public void deleteDisclaimer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/disclaimers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
