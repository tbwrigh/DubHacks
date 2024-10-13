package dev.tbwright.dubhacks.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*") // Allow all origins
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE") // Specify allowed HTTP methods
        configuration.allowedHeaders = listOf("*") // Allow all headers

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable() // Disable CSRF protection for APIs (optional, depending on use case)
            .cors().and() // Enable CORS
            .authorizeHttpRequests { authz ->
                authz
                    .anyRequest().authenticated() // Secure all endpoints, require authentication
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt() // Enable JWT Bearer token authentication
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Ensure the session is stateless
            }
        return http.build()
    }
}
