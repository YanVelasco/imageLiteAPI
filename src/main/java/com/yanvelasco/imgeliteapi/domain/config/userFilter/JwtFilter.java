package com.yanvelasco.imgeliteapi.domain.config.userFilter;

import com.yanvelasco.imgeliteapi.domain.userDomain.entity.UserEntity;
import com.yanvelasco.imgeliteapi.domain.userDomain.jwt.JWTService;
import com.yanvelasco.imgeliteapi.domain.userDomain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = getJwtFromRequest(request);
        if (jwt != null) {
            try {
                String email = jwtService.getEmailFromToken(jwt);
                UserEntity user = userRepository.findByEmail(email).orElseThrow(
                        () -> new RuntimeException("User not found")
                );
                setUserAuthenticated(user);
            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
            }
        }
        request.setAttribute("jwt", jwt);
        filterChain.doFilter(request, response);
    }

    private void setUserAuthenticated( UserEntity user) {
        UserDetails userDetails = User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}