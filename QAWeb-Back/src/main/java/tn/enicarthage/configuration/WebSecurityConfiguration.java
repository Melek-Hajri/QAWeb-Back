package tn.enicarthage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tn.enicarthage.filter.JwtRequestFilter;



@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	private final JwtRequestFilter jwtRequestFilter;
	
	public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}
	
	@Override
	public void configure(HttpSecurity security) throws Exception{
		security
				.csrf().disable()
	            .authorizeRequests(authorizeRequests ->
	                authorizeRequests
	                    .antMatchers("/signup", "/login").permitAll()
	                    .antMatchers("/api/**").authenticated()
	                    .anyRequest().authenticated()
	            )
	            .sessionManagement(sessionManagement ->
	                sessionManagement
	                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
	            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);	    
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
}
