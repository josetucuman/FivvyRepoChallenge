package com.fivvvy.core.test.controller;

import com.fivvvy.core.controller.AcceptanceController;
import com.fivvvy.core.dto.AcceptanceDto;
import com.fivvvy.core.model.Acceptance;
import com.fivvvy.core.service.impl.AcceptanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class AcceptanceControllerTest {

    @Mock
    private AcceptanceServiceImpl acceptanceService;

    @InjectMocks
    private AcceptanceController acceptanceController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        acceptanceController = new AcceptanceController(acceptanceService);
    }

    @Test
    public void testCreateAcceptance_Success() {
        // Prepare
        AcceptanceDto acceptanceDto = new AcceptanceDto();
        Acceptance acceptance = new Acceptance();
        when(acceptanceService.createAcceptance(any(Acceptance.class))).thenReturn(acceptance);

        // Execute
        ResponseEntity<Acceptance> response = acceptanceController.createAcceptance(acceptanceDto);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acceptance, response.getBody());
    }

    @Test
    public void testCreateAcceptance_BadRequest() {
        // Prepare
        AcceptanceDto acceptanceDto = new AcceptanceDto(); // Create an object  AcceptanceDto valid

        // Service Mock
        AcceptanceServiceImpl acceptanceService = Mockito.mock(AcceptanceServiceImpl.class);
        when(acceptanceService.createAcceptance(any(Acceptance.class))).thenReturn(null);


        AcceptanceController acceptanceController = new AcceptanceController(acceptanceService);

        // Execute
        ResponseEntity<Acceptance> response = acceptanceController.createAcceptance(acceptanceDto);

        // Verify
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testCreateAcceptance_InternalServerError() {
        // Prepare
        AcceptanceDto acceptanceDto = new AcceptanceDto();
        when(acceptanceService.createAcceptance(any(Acceptance.class))).thenThrow(RuntimeException.class);

        // Execute
        ResponseEntity<Acceptance> response = acceptanceController.createAcceptance(acceptanceDto);

        // Verify
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllAcceptances_NotEmpty() {
        // Prepare
        List<Acceptance> acceptances = new ArrayList<>();
        acceptances.add(new Acceptance());
        when(acceptanceService.getAllAcceptances()).thenReturn(acceptances);

        // Execute
        ResponseEntity<List<Acceptance>> response = acceptanceController.getAllAcceptances();

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acceptances, response.getBody());
    }

    @Test
    public void testGetAllAcceptances_Empty() {
        // Prepare
        List<Acceptance> acceptances = new ArrayList<>();
        when(acceptanceService.getAllAcceptances()).thenReturn(acceptances);

        // Execute
        ResponseEntity<List<Acceptance>> response = acceptanceController.getAllAcceptances();

        // Verify
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAcceptanceById_Found() {
        // Prepare
        Integer acceptanceId = 1;
        Acceptance acceptance = new Acceptance();
        when(acceptanceService.getAcceptanceById(acceptanceId)).thenReturn(Optional.of(acceptance));

        // Execute
        ResponseEntity<Acceptance> response = acceptanceController.getAcceptanceById(acceptanceId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acceptance, response.getBody());
    }

    @Test
    public void testGetAcceptanceById_NotFound() {
        // Prepare
        Integer acceptanceId = 1;
        when(acceptanceService.getAcceptanceById(acceptanceId)).thenReturn(Optional.empty());

        // Execute
        ResponseEntity<Acceptance> response = acceptanceController.getAcceptanceById(acceptanceId);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAcceptancesByUser_NotEmpty() {
        // Prepare
        Integer userId = 1;
        List<Acceptance> acceptances = new ArrayList<>();
        acceptances.add(new Acceptance());
        when(acceptanceService.getAcceptancesByUser(userId)).thenReturn(acceptances);

        // Execute
        ResponseEntity<List<Acceptance>> response = acceptanceController.getAcceptancesByUser(userId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acceptances, response.getBody());
    }

    @Test
    public void testGetAcceptancesByUser_Empty() {
        // Prepare
        Integer userId = 1;
        List<Acceptance> acceptances = new ArrayList<>();
        when(acceptanceService.getAcceptancesByUser(userId)).thenReturn(acceptances);

        // Execute
        ResponseEntity<List<Acceptance>> response = acceptanceController.getAcceptancesByUser(userId);

        // Verify
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAcceptancesByDisclaimer_NotEmpty() {
        // Prepare
        Long disclaimerId = 1L;
        List<Acceptance> acceptances = new ArrayList<>();
        acceptances.add(new Acceptance());
        when(acceptanceService.getAcceptancesByDisclaimer(disclaimerId)).thenReturn(acceptances);

        // Execute
        ResponseEntity<List<Acceptance>> response = acceptanceController.getAcceptancesByDisclaimer(disclaimerId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acceptances, response.getBody());
    }

    @Test
    public void testGetAcceptancesByDisclaimer_Empty() {
        // Prepare
        Long disclaimerId = 1L;
        List<Acceptance> acceptances = new ArrayList<>();
        when(acceptanceService.getAcceptancesByDisclaimer(disclaimerId)).thenReturn(acceptances);

        // Execute
        ResponseEntity<List<Acceptance>> response = acceptanceController.getAcceptancesByDisclaimer(disclaimerId);

        // Verify
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteAcceptance() {
        // Prepare
        Long acceptanceId = 1L;

        // Execute
        ResponseEntity<Void> response = acceptanceController.deleteAcceptance(acceptanceId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
