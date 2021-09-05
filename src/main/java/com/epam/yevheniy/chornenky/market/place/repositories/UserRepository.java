package com.epam.yevheniy.chornenky.market.place.repositories;

import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.exceptions.AuthenticationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {

    void createUser(UserEntity user);

    Optional<UserEntity> findByEmail(String emailForSearch);
}
