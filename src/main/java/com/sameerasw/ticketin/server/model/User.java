package com.sameerasw.ticketin.server.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private boolean isSimulated;

    // Constructors, getters, and setters
    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.isSimulated = false;
    }

    public User(String name, boolean isSimulated) {
        this.name = name;
        this.isSimulated = isSimulated;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    // ... getters and setters ...
}