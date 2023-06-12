package com.fivvvy.core.repository;

import com.fivvvy.core.model.Acceptance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptanceRepository extends JpaRepository<Acceptance, Long> {
    List<Acceptance> findByUserId(Integer userId);
    List<Acceptance> findByDisclaimerId(Long disclaimerId);

}
