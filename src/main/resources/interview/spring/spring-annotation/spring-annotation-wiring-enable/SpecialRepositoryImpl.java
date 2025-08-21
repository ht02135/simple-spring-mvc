package com.example.repository;

import org.springframework.stereotype.Repository;

@Repository("specialRepository")
public class SpecialRepositoryImpl implements UserRepository {
    @Override
    public String getUserName() {
        return "Special User";
    }
}
