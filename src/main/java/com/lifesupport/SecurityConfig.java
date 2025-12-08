package com.lifesupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lifesupport.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((auth) -> auth.requestMatchers("/*").permitAll()
						.requestMatchers("/images/**").permitAll()
						.requestMatchers("/assets/**").permitAll()
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
					    .requestMatchers("/blog/**").permitAll()
					    .requestMatchers("/job/**").permitAll()
					    .requestMatchers("/candidate/**").permitAll() 
					    .requestMatchers("/employer/**").permitAll() 
					    .requestMatchers("/gallary/**").permitAll()
					    .requestMatchers("/upload/**").permitAll()
//					    .hasRole("USER")
					    .anyRequest().authenticated()
					    )
				.formLogin(login -> login.loginPage("/logon").loginProcessingUrl("/logon").usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/default", true))
			    .logout((logout) -> logout.logoutUrl("/logout-url").logoutSuccessUrl("/logon").deleteCookies("JSESSIONID"));
		return http.build();
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web)->web.ignoring().requestMatchers
				("/static/**","/assets-fe/**","/upload/**","/lte3/**","/register/**","/views/**","/public/**");
		
	}
}
