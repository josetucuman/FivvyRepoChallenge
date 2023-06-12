package com.fivvvy.core.service;

import com.fivvvy.core.model.Acceptance;

import java.util.List;
import java.util.Optional;

public interface AcceptanceService {

    Acceptance createAcceptance(Acceptance acceptance);
    List<Acceptance> getAllAcceptances();
    Optional<Acceptance> getAcceptanceById(Integer id);

    List<Acceptance> getAcceptancesByUser(Integer userId);
    List<Acceptance> getAcceptancesByDisclaimer(Long disclaimerId);
    void deleteAcceptance(Long id);
}
