package com.example.carsharingservice.security.jwt;
import com.example.carsharingservice.exception.InvalidJwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
class JwtTokenFilterTest {
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private ServletRequest servletRequest;
    @Mock
    private ServletResponse servletResponse;
    @Mock
    private FilterChain filterChain;
    private JwtTokenFilter jwtTokenFilter;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.servletRequest = mock(HttpServletRequest.class);
        this.servletResponse = mock(HttpServletResponse.class);
        this.filterChain = mock(FilterChain.class);
        this.jwtTokenProvider = mock(JwtTokenProvider.class);
        this.jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
    }
    @Test
    void doFilter_validToken_setAuthentication() throws IOException, ServletException {
        String testToken = "testToken";
        when(jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest)).thenReturn(testToken);
        when(jwtTokenProvider.validateToken(testToken)).thenReturn(true);
        Authentication auth = mock(Authentication.class);
        when(jwtTokenProvider.getAuthentication(testToken)).thenReturn(auth);
        jwtTokenFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain).doFilter(servletRequest, servletResponse);
        assertEquals(SecurityContextHolder.getContext().getAuthentication(), auth);
    }
    @Test
    void doFilter_invalidToken_setUnauthorizedResponse() throws IOException, ServletException {
        String testToken = "testToken";
        when(jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest)).thenReturn(testToken);
        when(jwtTokenProvider.validateToken(testToken)).thenThrow(InvalidJwtAuthenticationException.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(outputStream);
        when(((HttpServletResponse) servletResponse).getOutputStream()).thenReturn(servletOutputStream);
        jwtTokenFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain, never()).doFilter(servletRequest, servletResponse);
        String responseString = outputStream.toString("UTF-8");
        assertTrue(responseString.contains("\"status\":401"));
        assertTrue(responseString.contains("\"errors\":[\"Token is not valid\"]"));
    }
}