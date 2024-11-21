package se.ifmo.is_lab1.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import se.ifmo.is_lab1.service.AuthUserDetailsService;
import se.ifmo.is_lab1.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtUtils;

    @Autowired
    private AuthUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(final @NotNull HttpServletRequest request,
                                    final @NotNull HttpServletResponse response, final @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            UserDetails userDetails = getUserDetails(request);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: { }", e);
        }

        filterChain.doFilter(request, response);
    }

    public UserDetails getUserDetails(HttpServletRequest request) {
        String jwt = parseJwt(request);

        if (jwt == null || !jwtUtils.validateJwtToken(jwt))
            return null;

        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        return userDetailsService.loadUserByUsername(username);
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String bearerPrefix = "Bearer ";
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(bearerPrefix)){
            return headerAuth.substring(bearerPrefix.length());}

        return null;
    }
}
