package com.example.carsharingservice.security;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CarSharingUserDetailServiceTest {

    @Test
    void loadUserByUsername() {
        String email = "test@example.com";
        String password = "password";
        User testUser = new User();
        testUser.setEmail(email);
        testUser.setPassword(password);

        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.getByEmail(email)).thenReturn(testUser);

        CarSharingUserDetailService userDetailService = new CarSharingUserDetailService(userService);

        UserDetails userDetails = userDetailService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_NotFound() {
        String email = "test@example.com";

        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.getByEmail(email)).thenReturn(null);

        CarSharingUserDetailService userDetailService = new CarSharingUserDetailService(userService);

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailService.loadUserByUsername(email);
        });
    }
}
