package com.fivvvy.core.repository;

import com.fivvvy.core.model.Disclaimer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisclaimerRepository extends JpaRepository<Disclaimer, Integer> {
    List<Disclaimer> findByText(String text);
}
