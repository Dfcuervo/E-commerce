package com.ecommerce.orderservice.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Obtiene el token del header
        String token = resolveToken(request);

        // Validacion del token
        if (token != null && jwtTokenProvider.validateToken(token)) {
            //Extrae el usuario
            String username = jwtTokenProvider.getUsername(token);

            //Crea un objeto de autenticación simulado
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //Guarda la autenticación en el contexto de seguridad actual
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //Deja que continúe la cadena de filtros
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        // Extraer el token JWT del encabezado Authorization
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7); // Elimina el prefijo "Bearer"
        }
        return null;
    }
}
