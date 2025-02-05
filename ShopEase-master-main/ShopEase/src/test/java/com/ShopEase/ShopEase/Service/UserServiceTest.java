package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.User;
import com.ShopEase.ShopEase.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1, user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("User1");
        user1.setEmail("user1@example.com");

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("User2");
        user2.setEmail("user2@example.com");
    }

    @Test
    void testGetAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("User1", users.get(0).getUsername());
        assertEquals("User2", users.get(1).getUsername());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));

        User foundUser = userService.getUserById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("User1", foundUser.getUsername());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_NotFound() {
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User savedUser = userService.createUser(user1);

        assertNotNull(savedUser);
        assertEquals("User1", savedUser.getUsername());

        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUsername("UpdatedUser");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("UpdatedUser", result.getUsername());
        assertEquals("updated@example.com", result.getEmail());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testUpdateUser_NotFound() {
        Long userId = 99L;
        User updatedUser = new User();
        updatedUser.setUsername("UpdatedUser");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
