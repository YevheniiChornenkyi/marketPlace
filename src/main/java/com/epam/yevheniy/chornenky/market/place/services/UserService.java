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
    private final EmailService emailService;
    private final PasswordService passwordService;

    public UserService(UserRepository repository, EmailService emailService, PasswordService passwordService) {
        this.repository = repository;
        this.emailService = emailService;
        this.passwordService = passwordService;
    }

    /**
     * Check checks if user exists with received in parameter email
     * checks if passwords match
     * check user status(not banned)
     * after validation and authentication return authenticate object contains name, surname, role, status, id.
     * @param email user email
     * @param psw user psw
     * @return authenticate object contains name, surname, role, status, id.
     */
    public Authentication authenticate(String email, String psw) {
        UserEntity user = repository.findByEmail(email).orElseThrow(AuthenticationException::new);
        String password = passwordService.getHash(psw);

        if (!user.getPsw().equals(password)) {
            throw new AuthenticationException();
        }
        if (!user.getIsActive()) {
            throw new AuthenticationException("msg.user-banned");
        }
        return new Authentication(user.getName(), user.getSurName(), user.getEmail(), user.getRole(), user.getId());
    }

    /**
     * check simple psw validation and convert userRegistrationDTO to userEntity
     * send userEntity to repository lvl
     * using emailService to sent user email to specified email
     * @param userRegistrationDTO user registration parameters
     */
    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        if (!emailCheckUniqueness(userRegistrationDTO.getEmail())) {
            throw new ValidationException(Map.of("email", "msg.email-not-unique"));
        }
        String id = UUID.randomUUID().toString();
        String password = passwordService.getHash(userRegistrationDTO.getPsw());
        UserEntity user = new UserEntity(userRegistrationDTO.getName(), userRegistrationDTO.getSurName(),
                password, userRegistrationDTO.getEmail(), id, Role.NOT_ACTIVATE);
        repository.createUser(user);
        String userActivationLink = repository.createUserActivationLink(id);
        emailService.sendEmail(user.getEmail(), "Activation", getEmailBodyForActivationLatter(userActivationLink));
    }

    /**
     * Sent received in parameters email to repository lvl.
     * save response in optional, return optional.isEmpty result.
     * @param email user email
     * @return boolean true if email unique
     */
    private boolean emailCheckUniqueness(String email) {
        Optional<UserEntity> optionalUserEntity = repository.findByEmail(email);
        return optionalUserEntity.isEmpty();
    }

    /**
     * return default activation latter + received in parameter key
     * @param activationKey activation key
     * @return latter
     */
    private String getEmailBodyForActivationLatter(String activationKey) {
        return String.format("Dear customer go to next link http://localhost/activation?key=%s to activate you account", activationKey);
    }

    /**
     * Convert received from repository lvl UserEntities to UserViewDto return as List<UserViewDto>
     * @return List<UserViewDto>
     */
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

    /**
     * Performs ban unban depending on the status banFlag. performs the appropriate operation to user with id received in parameters
     * @param banFlag ban flag
     * @param userId user id
     */
    public void blockDispatcher(String banFlag, String userId) {
        if (banFlag.equals("ban")) {
            repository.banById(userId);
        } else if (banFlag.equals("unBan")) {
            repository.unBanById(userId);
        }
    }

    /**
     * Send activation key to repository lvl
     * @param key activation key
     */
    public void activateUser(String key) {
        repository.activateUser(key);
    }

    /**
     * Authentication object. This transfer object characterized by name, surname, email, role, userId
     * Contains the information necessary to service the user after authentication
     */
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
