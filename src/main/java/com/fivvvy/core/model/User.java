package com.fivvvy.core.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String username;
}
