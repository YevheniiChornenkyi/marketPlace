package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.AuthenticationException;
import com.epam.yevheniy.chornenky.market.place.repositories.UserRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.UserRegistrationDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    public static final String PSW = "psw";
    public static final String EMAIL = "email";

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordService passwordService;
    @Mock
    private UserEntity userEntity;
    @Mock
    private UserRegistrationDTO userRegistrationDTO;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    @InjectMocks
    private UserService tested;

    @Test(expected = AuthenticationException.class)
    public void givenBannedUser_WhenTryAuthorize_ThenThrowAuthenticationException() {
        String psw = "psw";
        Optional<UserEntity> userEntityOptional = Optional.of(this.userEntity);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userEntityOptional);
        Mockito.when(passwordService.getHash(Mockito.anyString())).thenReturn(psw);
        Mockito.when(userEntity.getPsw()).thenReturn(psw);
        Mockito.when(userEntity.getIsActive()).thenReturn(false);

        tested.authenticate("email", psw);
    }

    @Test
    public void givenRegisteredUser_WhenTryAuthenticate_ThenReturnAuthentication() {
        Optional<UserEntity> userEntityOptional = Optional.of(this.userEntity);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userEntityOptional);
        Mockito.when(passwordService.getHash(Mockito.anyString())).thenReturn(PSW);
        Mockito.when(userEntity.getPsw()).thenReturn(PSW);
        Mockito.when(userEntity.getIsActive()).thenReturn(true);

        UserService.Authentication authentication = tested.authenticate(EMAIL, PSW);

        Assertions.assertThat(authentication).isNotNull();
    }

    @Test
    public void givenUserRegistrationDTO_WhenInvokedMethodCreateUser_ThenShouldInvokeMethodRepositoryCreateUserAndEmailServiceSendLatter() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRegistrationDTO.getEmail()).thenReturn(EMAIL);

        tested.createUser(userRegistrationDTO);

        Mockito.verify(userRepository).createUser(userEntityArgumentCaptor.capture());
        UserEntity actualCreatedUser = userEntityArgumentCaptor.getValue();
        Assertions.assertThat(actualCreatedUser.getEmail()).isEqualTo(EMAIL);
        Assertions.assertThat(actualCreatedUser.getId()).isNotEmpty();
        Mockito.verify(emailService).sendEmail(Mockito.eq(EMAIL), Mockito.eq("Activation"), Mockito.anyString());
    }
}