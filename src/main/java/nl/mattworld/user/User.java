package nl.mattworld.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String name;
    private String surName;
    private String email;
    private String password;

    @Override
    public String userId() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String surName() {
        return null;
    }

    @Override
    public String email() {
        return null;
    }

    @Override
    public String password() {
        return null;
    }
}
