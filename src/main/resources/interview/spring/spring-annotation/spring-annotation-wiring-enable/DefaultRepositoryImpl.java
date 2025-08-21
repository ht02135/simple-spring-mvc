package com.example.repository;

import org.springframework.stereotype.Repository;

@Repository("defaultRepository")
public class DefaultRepositoryImpl implements UserRepository {
    @Override
    public String getUserName() {
        return "Default User";
    }
}
