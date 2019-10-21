package org.sid.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//comment il va chercher les utilisateurs et les roles
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		auth.inMemoryAuthentication()
		.withUser("admin").password("{noop}1234").roles("ADMIN","USER")
		.and()
		.withUser("user").password("{noop}1234").roles("USER");*/
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
         
	}
	
	
	//ici on va ajouter des filtres
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable();
	
	/* on devra desactiver l'authentification basée sur les sessions 
	 * on va utiliser une sécurité basée sur le mode stateless
	 * 
	 * */
	
	//systéme d'authentification par référence vers valeur
      http.sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
	//http.formLogin();
       http.authorizeRequests().antMatchers("/login/**","/register/**" ).permitAll();
       http.authorizeRequests().antMatchers(HttpMethod.POST, "/tasks/**").hasAuthority("ADMIN");
       http.authorizeRequests().anyRequest().authenticated();
       http.addFilter(new JWTAuthentificationFilter(authenticationManager()));
       http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
       
      
	}
}
