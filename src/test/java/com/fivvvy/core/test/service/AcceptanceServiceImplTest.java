package com.fivvvy.core.test.service;


import com.fivvvy.core.dao.AcceptanceDao;
import com.fivvvy.core.model.Acceptance;
import com.fivvvy.core.service.impl.AcceptanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AcceptanceServiceImplTest {

    private AcceptanceServiceImpl acceptanceService;
    private AcceptanceDao acceptanceDao;

    @BeforeEach
    public void setUp() {
        acceptanceDao = mock(AcceptanceDao.class);
        acceptanceService = new AcceptanceServiceImpl(acceptanceDao);
    }

    @Test
    public void testCreateAcceptance() {
        // Arrange
        Acceptance acceptance = new Acceptance();
        acceptance.setId(1);
        acceptance.setCreatedAt(LocalDateTime.now());

        when(acceptanceDao.createAcceptance(any(Acceptance.class))).thenReturn(acceptance);

        // Act
        Acceptance createdAcceptance = acceptanceService.createAcceptance(acceptance);

        // Assert
        assertNotNull(createdAcceptance);
        assertEquals(acceptance.getId(), createdAcceptance.getId());
        assertEquals(acceptance.getCreatedAt(), createdAcceptance.getCreatedAt());
        verify(acceptanceDao, times(1)).createAcceptance(acceptance);
    }

    @Test
    public void testGetAllAcceptances() {
        // Arrange
        List<Acceptance> acceptanceList = new ArrayList<>();
        acceptanceList.add(new Acceptance());
        acceptanceList.add(new Acceptance());

        when(acceptanceDao.getAllAcceptances()).thenReturn(acceptanceList);

        // Act
        List<Acceptance> allAcceptances = acceptanceService.getAllAcceptances();

        // Assert
        assertNotNull(allAcceptances);
        assertEquals(2, allAcceptances.size());
        verify(acceptanceDao, times(1)).getAllAcceptances();
    }

    @Test
    public void testGetAcceptanceById() {
        // Arrange
        Acceptance acceptance = new Acceptance();
        acceptance.setId(1);

        when(acceptanceDao.getAcceptanceById(1)).thenReturn(Optional.of(acceptance));
        when(acceptanceDao.getAcceptanceById(2)).thenReturn(Optional.empty());

        // Act
        Optional<Acceptance> foundAcceptance = acceptanceService.getAcceptanceById(1);
        Optional<Acceptance> notFoundAcceptance = acceptanceService.getAcceptanceById(2);

        // Assert
        assertTrue(foundAcceptance.isPresent());
        assertFalse(notFoundAcceptance.isPresent());
        assertEquals(acceptance.getId(), foundAcceptance.get().getId());
        verify(acceptanceDao, times(1)).getAcceptanceById(1);
        verify(acceptanceDao, times(1)).getAcceptanceById(2);
    }

    @Test
    public void testGetAcceptancesByUser() {
        // Arrange
        List<Acceptance> acceptanceList = new ArrayList<>();
        acceptanceList.add(new Acceptance());
        acceptanceList.add(new Acceptance());

        when(acceptanceDao.getAcceptancesByUser(1)).thenReturn(acceptanceList);

        // Act
        List<Acceptance> acceptancesByUser = acceptanceService.getAcceptancesByUser(1);

        // Assert
        assertNotNull(acceptancesByUser);
        assertEquals(2, acceptancesByUser.size());
        verify(acceptanceDao, times(1)).getAcceptancesByUser(1);
    }

    @Test
    public void testGetAcceptancesByDisclaimer() {
        // Arrange
        List<Acceptance> acceptanceList = new ArrayList<>();
        acceptanceList.add(new Acceptance());
        acceptanceList.add(new Acceptance());

        when(acceptanceDao.getAcceptancesByDisclaimer(1L)).thenReturn(acceptanceList);

        // Act
        List<Acceptance> acceptancesByDisclaimer = acceptanceService.getAcceptancesByDisclaimer(1L);

        // Assert
        assertNotNull(acceptancesByDisclaimer);
        assertEquals(2, acceptancesByDisclaimer.size());
        verify(acceptanceDao, times(1)).getAcceptancesByDisclaimer(1L);
    }

    @Test
    public void testDeleteAcceptance() {
        // Arrange
        Long acceptanceId = 1L;

        // Act
        acceptanceService.deleteAcceptance(acceptanceId);

        // Assert
        verify(acceptanceDao, times(1)).deleteAcceptance(acceptanceId);
    }
}
