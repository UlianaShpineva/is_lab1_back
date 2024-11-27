package se.ifmo.is_lab1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import se.ifmo.is_lab1.service.AuthUserDetailsService;
import se.ifmo.is_lab1.util.CustomPasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthTokenFilter;

    private final AuthUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }));
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/index.html", "/favicon.ico", "/static/**").permitAll()
//                        .requestMatchers("/api/user/register", "/api/user/login").permitAll() // Разрешаем доступ без аутентификации
////                        .requestMatchers("/api/user/**", "api/**", "/user/role/**", "/ws", "/").permitAll()
//                        .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()
//                        .anyRequest().authenticated())
//                .authenticationProvider(authenticationProvider());

        http
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

//                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthEntryPoint()))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth.requestMatchers("/index.html", "/favicon.ico", "/static/**").permitAll()
//               .requestMatchers("/api/user/**", "api/**", "/user/role/**", "/ws", "/").permitAll()
//                        .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()
////               .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
////               .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
//               .anyRequest().authenticated())
//                .authenticationProvider(authenticationProvider());
//        http
//                .userDetailsService(userDetailsService)
//                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
//
//        http
//                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(userAuthenticationEntryPoint))
//                .addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class)
//                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF, если это необходимо для API
//                .cors(cors -> cors.configurationSource(request -> {
//                    var corsConfiguration = new CorsConfiguration();
//                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
//                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                    corsConfiguration.setAllowedHeaders(List.of("*"));
//                    corsConfiguration.setAllowCredentials(true);
//                    return corsConfiguration;
//                }))
//                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/public/**").permitAll()
//                        .requestMatchers("/api/**").authenticated()
//                        //.requestMatchers("/", "/index.html", "/static/**").permitAll()
//                        .anyRequest().permitAll()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login") // Указываем свою страницу авторизации
//                        .permitAll() // Разрешаем доступ к странице авторизации
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // URL для выхода
//                        .permitAll() // Разрешаем доступ к выходу
//                );
//
//        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "from", "size"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
