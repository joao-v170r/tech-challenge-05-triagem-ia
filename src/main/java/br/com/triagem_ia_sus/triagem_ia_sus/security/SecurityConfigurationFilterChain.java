package br.com.triagem_ia_sus.triagem_ia_sus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean; // Importar Bean
import org.springframework.context.annotation.Configuration; // Mudar @Configurable para @Configuration
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Use @Configuration para classes de configuração Spring
@EnableWebSecurity
public class SecurityConfigurationFilterChain {

    @Autowired
    SecurityFilter securityFilter;

    @Bean // Adicione @Bean para que o Spring gerencie este SecurityFilterChain
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para APIs REST
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a política de sessão como STATELESS
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        // Permite acesso público a todas as rotas do Swagger UI e OpenAPI docs
                                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-resources", "/configuration/ui", "/configuration/security", "/webjars/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/").permitAll() // Exemplo: se a raiz for uma página inicial pública
                                        // Qualquer outra requisição exige autenticação
                                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}