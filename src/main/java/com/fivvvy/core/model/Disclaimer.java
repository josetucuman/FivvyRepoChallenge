package com.fivvvy.core.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

@NoArgsConstructor
@Entity
public class Disclaimer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String text;
    private String version;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Disclaimer(String name, String text, String version) {
        this.name = name;
        this.text = text;
        this.version = version;
    }

    public Disclaimer(Integer id, String name, String text, String version) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.version = version;
    }
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
