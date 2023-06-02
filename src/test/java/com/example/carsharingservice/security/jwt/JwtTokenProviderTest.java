package com.example.carsharingservice.security.jwt;
import com.example.carsharingservice.exception.InvalidJwtAuthenticationException;
import com.example.carsharingservice.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class JwtTokenProviderTest {
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private HttpServletRequest httpServletRequest;
    private JwtTokenProvider jwtTokenProvider;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtTokenProvider = new JwtTokenProvider(userDetailsService);
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "mySecretKey");
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", "3600000");
        jwtTokenProvider.init();
    }
    @Test
    void createToken() {
        String login = "test";
        User.Role role = User.Role.MANAGER;
        String token = jwtTokenProvider.createToken(login, role);
        assertNotNull(token);
        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9"));
    }
    @Test
    void getAuthentication() {
        String token = jwtTokenProvider.createToken("test", User.Role.MANAGER);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("test")).thenReturn(userDetails);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        assertNotNull(authentication);
        assertEquals(authentication.getAuthorities(), List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
    }
    @Test
    void resolveToken() {
        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer test_token");
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        assertEquals("test_token", token);
    }
    @Test
    void validateToken() {
        String token = jwtTokenProvider.createToken("test", User.Role.MANAGER);
        boolean isValid = jwtTokenProvider.validateToken(token);
        assertTrue(isValid);
        assertThrows(InvalidJwtAuthenticationException.class, () -> jwtTokenProvider.validateToken("invalid_token"));
    }
}