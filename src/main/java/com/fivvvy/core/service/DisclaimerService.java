package com.fivvvy.core.service;

import com.fivvvy.core.dto.DisclaimerDto;
import com.fivvvy.core.model.Disclaimer;

import java.util.List;
import java.util.Optional;

public interface DisclaimerService {
    Disclaimer createDisclaimer(DisclaimerDto disclaimer);

    List<Disclaimer> getAllDisclaimers();

    List<Disclaimer> getAllDisclaimersByText(String text);

    Optional<Disclaimer> getDisclaimerById(Integer id);

    Disclaimer updateDisclaimer(Disclaimer disclaimer);

    void deleteDisclaimer(Integer id);

   boolean existsById(int id);
}
