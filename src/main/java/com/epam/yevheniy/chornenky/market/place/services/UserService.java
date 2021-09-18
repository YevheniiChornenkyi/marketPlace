package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.AuthenticationException;
import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.repositories.UserRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.UserRegistrationDTO;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.UserViewDto;

import java.util.*;

import static com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity.Role;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Authentication authenticate(String email, String psw) {
        UserEntity user = repository.findByEmail(email).orElseThrow(AuthenticationException::new);

        if (!user.getPsw().equals(psw)) {
            throw new AuthenticationException();
        }
        if (!user.getIsActive()) {
            throw new AuthenticationException("User is banned");
        }
        return new Authentication(user.getName(), user.getSurName(), user.getEmail(), user.getRole(), user.getId());
    }

    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        if (!emailCheckUniqueness(userRegistrationDTO.getEmail())) {
            throw new ValidationException(Map.of("email", "Email not unique"));
        }
        String id = UUID.randomUUID().toString();
        UserEntity user = new UserEntity(userRegistrationDTO.getName(), userRegistrationDTO.getSurName(),
                userRegistrationDTO.getPsw(), userRegistrationDTO.getEmail(), id, Role.CUSTOMER);
        repository.createUser(user);
    }

    private boolean emailCheckUniqueness(String email) {
        Optional<UserEntity> optionalUserEntity = repository.findByEmail(email);
        return optionalUserEntity.isEmpty();
    }

    public List<UserViewDto> getUsersDtoList() {
        List<UserEntity> usersEntityList = repository.getAllUsersList();
        List<UserViewDto> userViewDtoList = new ArrayList<>();
        for (UserEntity userEntity : usersEntityList) {
            String id = userEntity.getId();
            String name = userEntity.getName();
            String surName = userEntity.getSurName();
            Role role = userEntity.getRole();
            String email = userEntity.getEmail();
            boolean isActive = userEntity.getIsActive();
            UserViewDto userViewDto = new UserViewDto(id, name, surName, role.name(), email, isActive);
            userViewDtoList.add(userViewDto);
        }
        return userViewDtoList;
    }

    public void blockDispatcher(String banFlag, String userId) {
        if (banFlag.equals("ban")) {
            repository.banById(userId);
        } else if (banFlag.equals("unBan")) {
            repository.unBanById(userId);
        }
    }

    public static class Authentication {
        private final String name;
        private final String surName;
        private final String email;
        private final Role role;
        private final String userId;

        private Authentication(String name, String surName, String email, Role role, String userId) {
            this.name = name;
            this.surName = surName;
            this.email = email;
            this.role = role;
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public String getSurName() {
            return surName;
        }

        public String getEmail() {
            return email;
        }

        public Role getRole() {
            return role;
        }

        public String getUserId() {
            return userId;
        }
    }
}
