package com.project.aftas.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.project.aftas.Models.user.Permission.ADMIN_CREATE;
import static com.project.aftas.Models.user.Permission.ADMIN_DELETE;
import static com.project.aftas.Models.user.Permission.ADMIN_READ;
import static com.project.aftas.Models.user.Permission.ADMIN_UPDATE;
import static com.project.aftas.Models.user.Permission.JURY_CREATE;
import static com.project.aftas.Models.user.Permission.JURY_DELETE;
import static com.project.aftas.Models.user.Permission.JURY_READ;
import static com.project.aftas.Models.user.Permission.JURY_UPDATE;
import static com.project.aftas.Models.user.Permission.MEMBER_CREATE;
import static com.project.aftas.Models.user.Permission.MEMBER_DELETE;
import static com.project.aftas.Models.user.Permission.MEMBER_READ;
import static com.project.aftas.Models.user.Permission.MEMBER_UPDATE;
import static com.project.aftas.Models.user.Role.ADMIN;
import static com.project.aftas.Models.user.Role.JURY;
import static com.project.aftas.Models.user.Role.MEMBER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/member/**").hasAnyRole(ADMIN.name())
                                .requestMatchers("/api/competition/**").hasAnyRole(ADMIN.name(),JURY.name())
                                .requestMatchers("/api/ranking/**").hasAnyRole(JURY.name(), MEMBER.name(),ADMIN.name())
                                .requestMatchers("/api/level/**").hasAnyRole(JURY.name(), MEMBER.name(),ADMIN.name())
                                .requestMatchers("/api/fish/**").hasAnyRole(MEMBER.name(),ADMIN.name(),JURY.name())
                                .requestMatchers("/api/hunting/**").hasAnyRole(MEMBER.name(),ADMIN.name(),JURY.name())
                                .requestMatchers(GET, "/api/member/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(GET, "/api/ranking/**").hasAnyAuthority(JURY_READ.name())
                                .requestMatchers(GET, "/api/ranking/**").hasAnyAuthority(MEMBER_READ.name())
                                .requestMatchers(GET, "/api/level/**").hasAnyAuthority(MEMBER_READ.name())
                                .requestMatchers(GET, "/api/fish/**").hasAnyAuthority(MEMBER_READ.name())
                                .requestMatchers(GET, "/api/fish/**").hasAnyAuthority(JURY_READ.name())
                                .requestMatchers(POST, "/api/member/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(POST, "/api/competition/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(POST, "/api/competition/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/competition/**").hasAnyAuthority(MEMBER_READ.name())
                                .requestMatchers(POST, "/api/ranking/**").hasAnyAuthority(JURY_CREATE.name())
                                .requestMatchers(POST, "/api/fish/**").hasAnyAuthority(MEMBER_CREATE.name())
                                .requestMatchers(PUT, "/api/member/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(PUT, "/api/ranking/**").hasAnyAuthority(JURY_UPDATE.name())
                                .requestMatchers(PUT, "/api/fish/**").hasAnyAuthority(MEMBER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/member/**").hasAnyAuthority(ADMIN_DELETE.name())
                                .requestMatchers(DELETE, "/api/ranking/**").hasAnyAuthority(JURY_DELETE.name())
                                .requestMatchers(DELETE, "/api/fish/**").hasAnyAuthority(MEMBER_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }
}
