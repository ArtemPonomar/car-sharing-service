package com.example.carsharingservice.controller;
import com.example.carsharingservice.dto.request.UserLoginRequestDto;
import com.example.carsharingservice.dto.request.UserRegistrationRequestDto;
import com.example.carsharingservice.dto.response.UserLoginResponseDto;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.AuthenticationService;
import com.example.carsharingservice.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class AuthenticationControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private AuthenticationController authenticationController;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }
    @Test
    void registerNewUser() throws Exception {
        UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
        userRegistrationRequestDto.setEmail("email@test.com");
        userRegistrationRequestDto.setPassword("password123");
        userRegistrationRequestDto.setFirstName("John");
        userRegistrationRequestDto.setLastName("Doe");
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegistrationRequestDto)))
                .andExpect(status().isOk());
    }
    @Test
    void login() throws Exception {
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setEmail("email@test.com");
        userLoginRequestDto.setPassword("password123");
        User user = new User();
        user.setEmail("email@test.com");
        user.setPassword("password123");
        user.setRole(User.Role.CUSTOMER);
        when(authenticationService.login(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()))
                .thenReturn(user);
        when(jwtTokenProvider.createToken(user.getEmail(), user.getRole())).thenReturn("token");
        String response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserLoginResponseDto userLoginResponseDto = objectMapper.readValue(response, UserLoginResponseDto.class);
        assertEquals("token", userLoginResponseDto.getToken());
    }
}