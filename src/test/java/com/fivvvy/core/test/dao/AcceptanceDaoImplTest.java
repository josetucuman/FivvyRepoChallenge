package com.fivvvy.core.test.dao;
import com.fivvvy.core.dao.impl.AcceptanceDaoImpl;
import com.fivvvy.core.model.Acceptance;
import com.fivvvy.core.model.Disclaimer;
import com.fivvvy.core.model.User;
import com.fivvvy.core.repository.AcceptanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AcceptanceDaoImplTest {

    @Mock
    private AcceptanceRepository acceptanceRepository;

    @InjectMocks
    private AcceptanceDaoImpl acceptanceDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAcceptance() {
        // Prepare
        Acceptance acceptance = new Acceptance();
        acceptance.setId(1);
        acceptance.setUser(new User());
        acceptance.setDisclaimer(new Disclaimer());
        acceptance.setCreatedAt(LocalDateTime.now());

        when(acceptanceRepository.save(acceptance)).thenReturn(acceptance);

        // Execute
        Acceptance createdAcceptance = acceptanceDao.createAcceptance(acceptance);

        // Verify
        assertEquals(acceptance, createdAcceptance);
        verify(acceptanceRepository, times(1)).save(acceptance);
    }

    @Test
    public void testGetAllAcceptances() {
        // Prepare
        List<Acceptance> acceptances = new ArrayList<>();
        Acceptance acceptance1 = new Acceptance();
        acceptance1.setId(1);
        acceptance1.setUser(new User());
        acceptance1.setDisclaimer(new Disclaimer());
        acceptance1.setCreatedAt(LocalDateTime.now());
        acceptances.add(acceptance1);

        Acceptance acceptance2 = new Acceptance();
        acceptance2.setId(2);
        acceptance2.setUser(new User());
        acceptance2.setDisclaimer(new Disclaimer());
        acceptance2.setCreatedAt(LocalDateTime.now());
        acceptances.add(acceptance2);

        when(acceptanceRepository.findAll()).thenReturn(acceptances);

        // Execute
        List<Acceptance> retrievedAcceptances = acceptanceDao.getAllAcceptances();

        // Verify
        assertEquals(acceptances, retrievedAcceptances);
        verify(acceptanceRepository, times(1)).findAll();
    }

    @Test
    public void testGetAcceptanceById() {
        // Prepare
        Integer acceptanceId = 1;
        Acceptance acceptance = new Acceptance();
        acceptance.setId(acceptanceId);
        acceptance.setUser(new User());
        acceptance.setDisclaimer(new Disclaimer());
        acceptance.setCreatedAt(LocalDateTime.now());

        when(acceptanceRepository.findById(Long.valueOf(acceptanceId))).thenReturn(Optional.of(acceptance));

        // Execute
        Optional<Acceptance> retrievedAcceptance = acceptanceDao.getAcceptanceById(acceptanceId);

        // Verify
        assertTrue(retrievedAcceptance.isPresent());
        assertEquals(acceptance, retrievedAcceptance.get());
        verify(acceptanceRepository, times(1)).findById(Long.valueOf(acceptanceId));
    }

    @Test
    public void testGetAcceptancesByUser() {
        // Prepare
        Integer userId = 1;
        List<Acceptance> acceptances = new ArrayList<>();
        Acceptance acceptance1 = new Acceptance();
        acceptance1.setId(1);
        acceptance1.setUser(new User());  // Modificar esta línea
        acceptance1.getUser().setId(userId);  // Establecer el ID de User
        acceptance1.setDisclaimer(new Disclaimer());
        acceptance1.setCreatedAt(LocalDateTime.now());
        acceptances.add(acceptance1);

        Acceptance acceptance2 = new Acceptance();
        acceptance2.setId(2);
        acceptance2.setUser(new User());  // Modificar esta línea
        acceptance2.getUser().setId(userId);  // Establecer el ID de User
        acceptance2.setDisclaimer(new Disclaimer());
        acceptance2.setCreatedAt(LocalDateTime.now());
        acceptances.add(acceptance2);

        when(acceptanceRepository.findByUserId(userId)).thenReturn(acceptances);

        // Execute
        List<Acceptance> retrievedAcceptances = acceptanceDao.getAcceptancesByUser(userId);

        // Verify
        assertEquals(acceptances, retrievedAcceptances);
        verify(acceptanceRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetAcceptancesByDisclaimer() {
        // Prepare
        Long disclaimerId = 1L;
        List<Acceptance> acceptances = new ArrayList<>();
        Acceptance acceptance1 = new Acceptance();
        acceptance1.setId(1);
        acceptance1.setUser(new User());
        acceptance1.setDisclaimer(new Disclaimer());
        acceptance1.setCreatedAt(LocalDateTime.now());
        acceptances.add(acceptance1);

        Acceptance acceptance2 = new Acceptance();
        acceptance2.setId(2);
        acceptance2.setUser(new User());
        acceptance2.setDisclaimer(new Disclaimer());
        acceptance2.setCreatedAt(LocalDateTime.now());
        acceptances.add(acceptance2);

        when(acceptanceRepository.findByDisclaimerId(disclaimerId)).thenReturn(acceptances);

        // Execute
        List<Acceptance> retrievedAcceptances = acceptanceDao.getAcceptancesByDisclaimer(disclaimerId);

        // Verify
        assertEquals(acceptances, retrievedAcceptances);
        verify(acceptanceRepository, times(1)).findByDisclaimerId(disclaimerId);
    }

    @Test
    public void testDeleteAcceptance() {
        // Prepare
        Long acceptanceId = 1L;

        // Execute
        acceptanceDao.deleteAcceptance(acceptanceId);

        // Verify
        verify(acceptanceRepository, times(1)).deleteById(acceptanceId);
    }
}
