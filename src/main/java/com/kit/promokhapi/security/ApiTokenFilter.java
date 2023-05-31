package com.kit.promokhapi.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ApiTokenFilter extends OncePerRequestFilter {
    @Value("${apiToken}")
    private String apiToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Optional<String> token = parseApiToken(request);
            if (!token.isPresent() || !validateApiToken(token.get())) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        } catch (Exception e) {
            log.error("Cannot set authentication", e);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> parseApiToken(HttpServletRequest request) {
        String token = request.getHeader("Api-Token");
        if (StringUtils.hasText(token)) {
            return Optional.of(token);
        }
        return Optional.empty();
    }

    private boolean validateApiToken(String token) {
        return token.equals(apiToken);
    }
}
