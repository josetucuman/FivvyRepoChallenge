package com.fivvvy.core.test.service;

import com.fivvvy.core.controller.DisclaimerController;
import com.fivvvy.core.dto.DisclaimerDto;
import com.fivvvy.core.model.Disclaimer;
import com.fivvvy.core.service.DisclaimerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class DisclaimerServiceImplTest {

    @Mock
    private DisclaimerService disclaimerService;

    private DisclaimerController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new DisclaimerController(disclaimerService);
    }

    @Test
    public void testGetDisclaimersById_NotFound() {
        // Prepare
        when(disclaimerService.getDisclaimerById(1)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<Disclaimer> response = controller.getDisclaimersById(1);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(disclaimerService, times(1)).getDisclaimerById(1);
    }

    @Test
    public void testCreateDisclaimer_Success() {
        // Prepare
        DisclaimerDto newDisclaimerDto = new DisclaimerDto();
        newDisclaimerDto.setName("Test Disclaimer");
        newDisclaimerDto.setText("This is a test disclaimer");
        newDisclaimerDto.setVersion("1.0");

        Disclaimer createdDisclaimer = new Disclaimer();
        createdDisclaimer.setId(1);
        createdDisclaimer.setName("Test Disclaimer");
        createdDisclaimer.setText("This is a test disclaimer");
        createdDisclaimer.setVersion("1.0");

        when(disclaimerService.createDisclaimer(any(DisclaimerDto.class))).thenReturn(createdDisclaimer);

        // Execute
        ResponseEntity<Disclaimer> response = controller.createDisclaimer(newDisclaimerDto);

        // Verify
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDisclaimer, response.getBody());
        verify(disclaimerService, times(1)).createDisclaimer(any(DisclaimerDto.class));
    }

    @Test
    public void testCreateDisclaimer_BadRequest() {
        // Prepare
        DisclaimerDto newDisclaimerDto = new DisclaimerDto();

        // Execute
        ResponseEntity<Disclaimer> response = controller.createDisclaimer(newDisclaimerDto);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(disclaimerService, never()).createDisclaimer(any(DisclaimerDto.class));
    }

    @Test
    public void testGetAllDisclaimers_WithText() {
        // Prepare
        String searchText = "test";
        List<Disclaimer> disclaimers = new ArrayList<>();
        disclaimers.add(new Disclaimer(1, "Test Disclaimer 1", "This is a test disclaimer", "1.0"));
        disclaimers.add(new Disclaimer(2, "Test Disclaimer 2", "This is another test disclaimer", "2.0"));

        when(disclaimerService.getAllDisclaimersByText(searchText)).thenReturn(disclaimers);

        // Execute
        ResponseEntity<List<Disclaimer>> response = controller.getAllDisclaimers(searchText);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(disclaimers, response.getBody());
        verify(disclaimerService, times(1)).getAllDisclaimersByText(searchText);
    }

    @Test
    public void testGetAllDisclaimers_NoText() {
        // Prepare
        List<Disclaimer> disclaimers = new ArrayList<>();
        disclaimers.add(new Disclaimer(1, "Test Disclaimer 1", "This is a test disclaimer", "1.0"));
        disclaimers.add(new Disclaimer(2, "Test Disclaimer 2", "This is another test disclaimer", "2.0"));

        when(disclaimerService.getAllDisclaimers()).thenReturn(disclaimers);

        // Execute
        ResponseEntity<List<Disclaimer>> response = controller.getAllDisclaimers(null);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(disclaimers, response.getBody());
        verify(disclaimerService, times(1)).getAllDisclaimers();
    }

    @Test
    public void testGetAllDisclaimers_NoContent() {
        // Prepare
        when(disclaimerService.getAllDisclaimers()).thenReturn(new ArrayList<>());

        // Execute
        ResponseEntity<List<Disclaimer>> response = controller.getAllDisclaimers(null);

        // Verify
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(disclaimerService, times(1)).getAllDisclaimers();
    }

    @Test
    public void testUpdateDisclaimer_Success() {
        // Prepare
        int disclaimerId = 1;
        DisclaimerDto updatedDisclaimerDto = new DisclaimerDto();
        updatedDisclaimerDto.setName("Updated Disclaimer");
        updatedDisclaimerDto.setText("This is an updated disclaimer");
        updatedDisclaimerDto.setVersion("2.0");

        Disclaimer existingDisclaimer = new Disclaimer(1, "Test Disclaimer", "This is a test disclaimer", "1.0");
        Disclaimer updatedDisclaimer = new Disclaimer(1, "Updated Disclaimer", "This is an updated disclaimer", "2.0");

        when(disclaimerService.getDisclaimerById(disclaimerId)).thenReturn(Optional.of(existingDisclaimer));
        when(disclaimerService.updateDisclaimer(any(Disclaimer.class))).thenReturn(updatedDisclaimer);

        // Execute
        ResponseEntity<Disclaimer> response = controller.updateDisclaimer(disclaimerId, updatedDisclaimerDto);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDisclaimer, response.getBody());
        verify(disclaimerService, times(1)).getDisclaimerById(disclaimerId);
        verify(disclaimerService, times(1)).updateDisclaimer(any(Disclaimer.class));
    }

    @Test
    public void testUpdateDisclaimer_NotFound() {
        // Prepare
        int disclaimerId = 1;
        DisclaimerDto updatedDisclaimerDto = new DisclaimerDto();

        when(disclaimerService.getDisclaimerById(disclaimerId)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<Disclaimer> response = controller.updateDisclaimer(disclaimerId, updatedDisclaimerDto);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(disclaimerService, times(1)).getDisclaimerById(disclaimerId);
        verify(disclaimerService, never()).updateDisclaimer(any(Disclaimer.class));
    }

    @Test
    public void testUpdateDisclaimer_BadRequest() {
        // Prepare
        int disclaimerId = 1;
        DisclaimerDto updatedDisclaimerDto = new DisclaimerDto();

        Disclaimer existingDisclaimer = new Disclaimer(1, "Test Disclaimer", "This is a test disclaimer", "1.0");

        when(disclaimerService.getDisclaimerById(disclaimerId)).thenReturn(Optional.of(existingDisclaimer));

        // Execute
        ResponseEntity<Disclaimer> response = controller.updateDisclaimer(disclaimerId, updatedDisclaimerDto);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(disclaimerService, times(1)).getDisclaimerById(disclaimerId);
        verify(disclaimerService, never()).updateDisclaimer(any(Disclaimer.class));
    }

    @Test
    public void testDeleteDisclaimer_Success() {
        // Prepare
        int disclaimerId = 1;

        // Execute
        controller.deleteDisclaimers(disclaimerId);

        // Verify
        verify(disclaimerService, times(1)).deleteDisclaimer(disclaimerId);
    }
}
