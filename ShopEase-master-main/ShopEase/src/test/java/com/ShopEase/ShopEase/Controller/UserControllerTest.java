package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.Model.User;
import com.ShopEase.ShopEase.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetAllUsers_Success() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("User1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("User2");
        user2.setEmail("user2@example.com");

        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        List<User> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("User1", response.get(0).getUsername());
        assertEquals("User2", response.get(1).getUsername());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Success() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setUsername("TestUser");
        mockUser.setEmail("test@example.com");

        when(userService.getUserById(userId)).thenReturn(mockUser);

        User response = userController.getUserById(userId);

        assertNotNull(response);
        assertEquals(userId, response.getId());
        assertEquals("TestUser", response.getUsername());

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testCreateUser_Success() {
        User newUser = new User();
        newUser.setUsername("NewUser");
        newUser.setEmail("newuser@example.com");

        when(userService.createUser(any(User.class))).thenReturn(newUser);

        User response = userController.createUser(newUser);

        assertNotNull(response);
        assertEquals("NewUser", response.getUsername());
        assertEquals("newuser@example.com", response.getEmail());

        verify(userService, times(1)).createUser(newUser);
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUsername("UpdatedUser");
        updatedUser.setEmail("updated@example.com");

        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(updatedUser);

        User response = userController.updateUser(userId, updatedUser);

        assertNotNull(response);
        assertEquals("UpdatedUser", response.getUsername());
        assertEquals("updated@example.com", response.getEmail());

        verify(userService, times(1)).updateUser(userId, updatedUser);
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        userController.deleteUser(userId);

        verify(userService, times(1)).deleteUser(userId);
    }
}
