package br.com.jovencio.projetofonocore.configs.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfiguration( JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider ) {
		this.authenticationProvider = authenticationProvider;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
		http
				.csrf( AbstractHttpConfigurer::disable )
				.cors( Customizer.withDefaults() )
				.cors( httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource( corsConfigurationSource() ) )
				.authorizeHttpRequests( authz -> authz
						.requestMatchers( "/auth/token", "/auth/check-token", "/auth/refresh-token", "/auth/change-password", "/public/**", "/actuator/**" ).permitAll()
						.anyRequest().authenticated()
				).sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
				.authenticationProvider( authenticationProvider )
				.addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOrigin( "http://localhost:8080/" );
		configuration.addAllowedOrigin( "http://localhost:4200/" );
		configuration.addAllowedOrigin( "https://d3dsqwsb93lpxc.cloudfront.net/" );
		configuration.addAllowedOrigin( "http://ladvox-front.s3-website-us-east-1.amazonaws.com/" );
		configuration.setAllowedMethods( List.of( "GET", "POST", "PATCH", "PUT", "DELETE" ) );
		configuration.setAllowedHeaders( List.of( "Authorization", "Content-Type" ) );

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration( "/**", configuration );

		return source;
	}

}
