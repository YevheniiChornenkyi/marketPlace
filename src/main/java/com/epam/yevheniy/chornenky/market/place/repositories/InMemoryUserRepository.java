package com.epam.yevheniy.chornenky.market.place.repositories;

import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository{

    private final Map<String, UserEntity> usersMap = new HashMap<>();

    public synchronized void createUser(UserEntity user) {
        usersMap.put(user.getEmail(), user);
    }

    public Optional<UserEntity> findByEmail(String emailForSearch) {
        for (String s : usersMap.keySet()) {
            if (s.equals(emailForSearch)) {
                return Optional.of(usersMap.get(s));
            }
        }
        return Optional.empty();
    }
}

