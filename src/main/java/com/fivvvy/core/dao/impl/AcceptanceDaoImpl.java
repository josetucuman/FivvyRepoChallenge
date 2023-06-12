package com.fivvvy.core.dao.impl;

import com.fivvvy.core.dao.AcceptanceDao;
import com.fivvvy.core.model.Acceptance;
import com.fivvvy.core.repository.AcceptanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AcceptanceDaoImpl implements AcceptanceDao {

    private final AcceptanceRepository repository;

    @Autowired
    public AcceptanceDaoImpl(AcceptanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Acceptance createAcceptance(Acceptance acceptance) {
        acceptance.setCreatedAt(LocalDateTime.now());
        return repository.save(acceptance);
    }

    @Override
    public List<Acceptance> getAllAcceptances() {
        return repository.findAll();
    }

    @Override
    public Optional<Acceptance> getAcceptanceById(Integer id) {
        return repository.findById(Long.valueOf(id));
    }

    @Override
    public List<Acceptance> getAcceptancesByUser(Integer userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Acceptance> getAcceptancesByDisclaimer(Long disclaimerId) {
        return repository.findByDisclaimerId(disclaimerId);
    }

    @Override
    public void deleteAcceptance(Long id) {
        repository.deleteById(id);
    }
}
