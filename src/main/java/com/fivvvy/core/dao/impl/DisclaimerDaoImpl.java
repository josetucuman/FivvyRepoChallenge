package com.fivvvy.core.dao.impl;

import com.fivvvy.core.dao.DisclaimerDao;
import com.fivvvy.core.model.Disclaimer;
import com.fivvvy.core.repository.DisclaimerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public class DisclaimerDaoImpl implements DisclaimerDao {

    private final DisclaimerRepository repository;

    @Autowired
    public DisclaimerDaoImpl(DisclaimerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Disclaimer createDisclaimer(Disclaimer disclaimer) {
        disclaimer.setCreatedAt(LocalDateTime.now());
        return repository.save(disclaimer);
    }

    @Override
    public List<Disclaimer> getAllDisclaimers() {
        return repository.findAll();
    }

    @Override
    public List<Disclaimer> getAllDisclaimersByText(String text) {

        return repository.findByText(text);
    }

    @Override
    public Optional<Disclaimer> getDisclaimerById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Disclaimer updateDisclaimer(Disclaimer disclaimer) {

        // Check if the disclaimer exists in the database
            Integer disclaimerId = disclaimer.getId();
            Optional<Disclaimer> existingDisclaimer = repository.findById(disclaimerId);

            if (existingDisclaimer.isPresent()) {
                Disclaimer updatedDisclaimer = existingDisclaimer.get();
                updatedDisclaimer.setName(disclaimer.getName());
                updatedDisclaimer.setText(disclaimer.getText());
                updatedDisclaimer.setVersion(disclaimer.getVersion());

                return repository.save(updatedDisclaimer);
            } else {
                throw new RuntimeException("Disclaimer not found");
            }
        }
    @Override
    public void deleteDisclaimer(Integer id) {
        repository.deleteById(id);
    }
    @Override
    public boolean existsById(int id) {
        Optional<Disclaimer> optionalDisclaimer = repository.findById(id);
        return optionalDisclaimer.isPresent();
    }
}
