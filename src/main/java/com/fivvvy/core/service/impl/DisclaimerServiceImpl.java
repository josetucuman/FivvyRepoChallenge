package com.fivvvy.core.service.impl;

import com.fivvvy.core.dao.DisclaimerDao;
import com.fivvvy.core.dto.DisclaimerDto;
import com.fivvvy.core.model.Disclaimer;
import com.fivvvy.core.service.DisclaimerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisclaimerServiceImpl implements DisclaimerService {

    private DisclaimerDao repository;

    @Autowired
    public DisclaimerServiceImpl(DisclaimerDao repository) {
        this.repository = repository;
    }

    @Override
    public Disclaimer createDisclaimer(DisclaimerDto newDisclaimer) {
        Disclaimer disclaimer = new Disclaimer();
        BeanUtils.copyProperties(newDisclaimer, disclaimer);
        return repository.createDisclaimer(disclaimer);
    }

    @Override
    public List<Disclaimer> getAllDisclaimers() {
        return repository.getAllDisclaimers();
    }

    @Override
    public List<Disclaimer> getAllDisclaimersByText(String text) {
        return repository.getAllDisclaimersByText(text);
    }

    @Override
    public Optional<Disclaimer> getDisclaimerById(Integer id) {
        return repository.getDisclaimerById(id);
    }


    @Override
    public boolean existsById(int id) {
        return repository.existsById(id);
    }


    @Override
    public Disclaimer updateDisclaimer(Disclaimer disclaimer) {
       return repository.updateDisclaimer(disclaimer);
    }

    @Override
    public void deleteDisclaimer(Integer id) {
        repository.deleteDisclaimer(id);
        }


}
