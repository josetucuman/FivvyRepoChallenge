package com.fivvvy.core.dao;

import com.fivvvy.core.model.Acceptance;

import java.util.List;
import java.util.Optional;

public interface AcceptanceDao {

    Acceptance createAcceptance(Acceptance acceptance);
    List<Acceptance> getAllAcceptances();
    Optional<Acceptance> getAcceptanceById(Integer id);

    List<Acceptance> getAcceptancesByUser(Integer userId);
    List<Acceptance> getAcceptancesByDisclaimer(Long disclaimerId);
    void deleteAcceptance(Long id);
}
