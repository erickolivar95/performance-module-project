package com.spring.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*@Autowired
	private DataSource dataSource;*/

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.jdbcAuthentication().dataSource(dataSource);
		
		
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
				.withUser(users.username("john").password("test123").roles("EMPLOYEE"))
				.withUser(users.username("mary").password("test123").roles("MANAGER"));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/employees/**").hasRole("EMPLOYEE")
			.antMatchers("/managers/**").hasRole("MANAGER")
			.and()
			.formLogin()
			.permitAll()
			.and()
			.logout().permitAll();
	}
}
