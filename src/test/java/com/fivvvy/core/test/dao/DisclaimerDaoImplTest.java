package com.fivvvy.core.test.dao;


import com.fivvvy.core.dao.impl.DisclaimerDaoImpl;
import com.fivvvy.core.model.Disclaimer;
import com.fivvvy.core.repository.DisclaimerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DisclaimerDaoImplTest {

    @Mock
    private DisclaimerRepository disclaimerRepository;

    @InjectMocks
    private DisclaimerDaoImpl disclaimerDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDisclaimer() {
        // Prepare
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setId(1);
        disclaimer.setName("Disclaimer 1");
        disclaimer.setText("This is the disclaimer text");
        disclaimer.setVersion("1.0");
        disclaimer.setCreatedAt(LocalDateTime.now());

        when(disclaimerRepository.save(disclaimer)).thenReturn(disclaimer);

        // Execute
        Disclaimer createdDisclaimer = disclaimerDao.createDisclaimer(disclaimer);

        // Verify
        assertEquals(disclaimer, createdDisclaimer);
        verify(disclaimerRepository, times(1)).save(disclaimer);
    }

    @Test
    public void testGetAllDisclaimers() {
        // Prepare
        List<Disclaimer> disclaimers = new ArrayList<>();
        Disclaimer disclaimer1 = new Disclaimer();
        disclaimer1.setId(1);
        disclaimer1.setName("Disclaimer 1");
        disclaimer1.setText("This is the disclaimer text");
        disclaimer1.setVersion("1.0");
        disclaimer1.setCreatedAt(LocalDateTime.now());
        disclaimers.add(disclaimer1);

        Disclaimer disclaimer2 = new Disclaimer();
        disclaimer2.setId(2);
        disclaimer2.setName("Disclaimer 2");
        disclaimer2.setText("This is another disclaimer text");
        disclaimer2.setVersion("2.0");
        disclaimer2.setCreatedAt(LocalDateTime.now());
        disclaimers.add(disclaimer2);

        when(disclaimerRepository.findAll()).thenReturn(disclaimers);

        // Execute
        List<Disclaimer> retrievedDisclaimers = disclaimerDao.getAllDisclaimers();

        // Verify
        assertEquals(disclaimers, retrievedDisclaimers);
        verify(disclaimerRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllDisclaimersByText() {
        // Prepare
        String searchText = "disclaimer";

        List<Disclaimer> disclaimers = new ArrayList<>();
        Disclaimer disclaimer1 = new Disclaimer();
        disclaimer1.setId(1);
        disclaimer1.setName("Disclaimer 1");
        disclaimer1.setText("This is the disclaimer text");
        disclaimer1.setVersion("1.0");
        disclaimer1.setCreatedAt(LocalDateTime.now());
        disclaimers.add(disclaimer1);

        Disclaimer disclaimer2 = new Disclaimer();
        disclaimer2.setId(2);
        disclaimer2.setName("Disclaimer 2");
        disclaimer2.setText("This is another disclaimer text");
        disclaimer2.setVersion("2.0");
        disclaimer2.setCreatedAt(LocalDateTime.now());
        disclaimers.add(disclaimer2);

        when(disclaimerRepository.findByText(searchText)).thenReturn(disclaimers);

        // Execute
        List<Disclaimer> retrievedDisclaimers = disclaimerDao.getAllDisclaimersByText(searchText);

        // Verify
        assertEquals(disclaimers, retrievedDisclaimers);
        verify(disclaimerRepository, times(1)).findByText(searchText);
    }

    @Test
    public void testGetDisclaimerById() {
        // Prepare
        Integer disclaimerId = 1;
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setId(disclaimerId);
        disclaimer.setName("Disclaimer 1");
        disclaimer.setText("This is the disclaimer text");
        disclaimer.setVersion("1.0");
        disclaimer.setCreatedAt(LocalDateTime.now());

        when(disclaimerRepository.findById(disclaimerId)).thenReturn(Optional.of(disclaimer));

        // Execute
        Optional<Disclaimer> retrievedDisclaimer = disclaimerDao.getDisclaimerById(disclaimerId);

        // Verify
        assertTrue(retrievedDisclaimer.isPresent());
        assertEquals(disclaimer, retrievedDisclaimer.get());
        verify(disclaimerRepository, times(1)).findById(disclaimerId);
    }

    @Test
    public void testUpdateDisclaimer() {
        // Prepare
        Disclaimer existingDisclaimer = new Disclaimer();
        existingDisclaimer.setId(1);
        existingDisclaimer.setName("Disclaimer 1");
        existingDisclaimer.setText("This is the disclaimer text");
        existingDisclaimer.setVersion("1.0");
        existingDisclaimer.setCreatedAt(LocalDateTime.now());

        Disclaimer updatedDisclaimer = new Disclaimer();
        updatedDisclaimer.setId(1);
        updatedDisclaimer.setName("Updated Disclaimer");
        updatedDisclaimer.setText("This is the updated disclaimer text");
        updatedDisclaimer.setVersion("2.0");
        updatedDisclaimer.setCreatedAt(LocalDateTime.now());

        when(disclaimerRepository.findById(existingDisclaimer.getId())).thenReturn(Optional.of(existingDisclaimer));
        when(disclaimerRepository.save(updatedDisclaimer)).thenReturn(updatedDisclaimer);

        // Execute
        Disclaimer result = disclaimerDao.updateDisclaimer(updatedDisclaimer);

        // Verify
        assertNotNull(result);
        assertEquals(updatedDisclaimer, result);
        verify(disclaimerRepository, times(1)).findById(existingDisclaimer.getId());
        verify(disclaimerRepository, times(1)).save(updatedDisclaimer);
    }

    @Test
    public void testUpdateDisclaimer_NotFound() {
        // Prepare
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setId(1);
        disclaimer.setName("Updated Disclaimer");
        disclaimer.setText("This is the updated disclaimer text");
        disclaimer.setVersion("2.0");
        disclaimer.setCreatedAt(LocalDateTime.now());

        when(disclaimerRepository.findById(disclaimer.getId())).thenReturn(Optional.empty());

        // Execute and Verify
        assertThrows(RuntimeException.class, () -> disclaimerDao.updateDisclaimer(disclaimer));
        verify(disclaimerRepository, times(1)).findById(disclaimer.getId());
        verify(disclaimerRepository, never()).save(any());
    }

    @Test
    public void testDeleteDisclaimer() {
        // Prepare
        Integer disclaimerId = 1;

        // Execute
        disclaimerDao.deleteDisclaimer(disclaimerId);

        // Verify
        verify(disclaimerRepository, times(1)).deleteById(disclaimerId);
    }

    @Test
    public void testExistsById_True() {
        // Prepare
        Integer disclaimerId = 1;

        when(disclaimerRepository.findById(disclaimerId)).thenReturn(Optional.of(new Disclaimer()));

        // Execute
        boolean result = disclaimerDao.existsById(disclaimerId);

        // Verify
        assertTrue(result);
        verify(disclaimerRepository, times(1)).findById(disclaimerId);
    }

    @Test
    public void testExistsById_False() {
        // Prepare
        Integer disclaimerId = 1;

        when(disclaimerRepository.findById(disclaimerId)).thenReturn(Optional.empty());

        // Execute
        boolean result = disclaimerDao.existsById(disclaimerId);

        // Verify
        assertFalse(result);
        verify(disclaimerRepository, times(1)).findById(disclaimerId);
    }
}
