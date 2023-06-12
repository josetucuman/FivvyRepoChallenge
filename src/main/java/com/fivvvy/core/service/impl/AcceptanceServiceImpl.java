package com.fivvvy.core.service.impl;

import com.fivvvy.core.dao.AcceptanceDao;
import com.fivvvy.core.model.Acceptance;
import com.fivvvy.core.repository.AcceptanceRepository;
import com.fivvvy.core.service.AcceptanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AcceptanceServiceImpl implements AcceptanceService {


    private final AcceptanceDao repository;

    @Autowired
    public AcceptanceServiceImpl(AcceptanceDao repository) {
        this.repository = repository;
    }

    @Override
    public Acceptance createAcceptance(Acceptance acceptance) {
       return repository.createAcceptance(acceptance);
    }

    @Override
    public List<Acceptance> getAllAcceptances() {
        return repository.getAllAcceptances();
    }

    @Override
    public Optional<Acceptance> getAcceptanceById(Integer id) {

        return repository.getAcceptanceById(id);
    }

    @Override
    public List<Acceptance> getAcceptancesByUser(Integer userId) {
        return repository.getAcceptancesByUser(userId);
    }

    @Override
    public List<Acceptance> getAcceptancesByDisclaimer(Long disclaimerId) {
        return repository.getAcceptancesByDisclaimer(disclaimerId);
    }

    @Override
    public void deleteAcceptance(Long id) {
        repository.deleteAcceptance(id);
    }
}
