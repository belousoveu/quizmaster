package org.skypro.be.quizmaster.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.quizmaster.model.User;
import org.skypro.be.quizmaster.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.skypro.be.quizmaster.service.TestData.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImp out;

    @Test
    void init_testWhenAdminNotExist() {
        List<User> users = new ArrayList<>(USERS);
        when(passwordEncoder.encode("root")).thenReturn("root");
        when(userRepository.findByUsername("root")).thenReturn(null);

        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> {
            User savedUser = invocationOnMock.getArgument(0);
            savedUser.setId(3L);
            users.add(savedUser);
            return savedUser;
        });

        out.init();
        assertEquals(3, users.size());
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("root")));
    }

    @Test
    void init_testWhenAdminExist() {
        List<User> users = new ArrayList<>(USERS);
        users.add(ADMIN);
        when(userRepository.findByUsername("root")).thenReturn(ADMIN);
        out.init();
        assertEquals(3, users.size());
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("root")));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_test() {
        List<User> users = new ArrayList<>();
        when(passwordEncoder.encode("password")).thenReturn("password");

        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> {
            User savedUser = invocationOnMock.getArgument(0);
            savedUser.setId(1L);
            users.add(savedUser);
            return savedUser;
        });

        out.registerUser(USER1);

        assertEquals(1, users.size());
        assertEquals(USER1, users.get(0));
        assertEquals(USER1.getRoles(), Collections.singleton("ROLE_USER"));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_testWhenNull() {
        assertThrows(NullPointerException.class, () -> out.registerUser(null));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void changeUserRole_testWhenUserExist() {
        Set<User> users = new HashSet<>(USERS);
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER1));
        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> {
            User savedUser = invocationOnMock.getArgument(0);
            savedUser.setId(1L);
            users.add(savedUser);
            return savedUser;
        });

        out.changeUserRole(1L, "ADMIN");
        assertEquals(2, users.size());
        assertTrue(users.stream().filter(user -> user.getId().equals(1L)).findFirst().get().getRoles().contains("ROLE_ADMIN"));
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void changeUserRole_testWhenUserNotExist() {
        assertThrows(UsernameNotFoundException.class, () -> out.changeUserRole(3L, "ADMIN"));
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void getUser_testWhenUserExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER1));

        User user = out.getUser(1L);
        assertEquals(USER1, user);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUser_testWhenUserNotExist() {
        when(userRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> out.getUser(3L));
        verify(userRepository, times(1)).findById(3L);
    }

    @Test
    void getUsers_test() {
        List<User> users = new ArrayList<>(USERS);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(users, out.getUsers());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    void loadUserByUsername_testWhenUserExist() {
        when(userRepository.findByUsername("user1")).thenReturn(USER1);
        UserDetails actual = out.loadUserByUsername("user1");
        assertNotNull(actual);
        assertEquals(USER1, actual);
    }

    @Test
    void loadUserByUsername_testWhenUserNotExist() {
        when(userRepository.findByUsername("user3")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> out.loadUserByUsername("user3"));
    }
}