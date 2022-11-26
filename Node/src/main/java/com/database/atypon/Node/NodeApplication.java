package com.database.atypon.Node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class NodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(NodeApplication.class, args);
	}
//	@EnableWebSecurity
//	@Configuration
//	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
////			admin can access all
////			user can access only /user
////			anyone can access /login /logout
//			http.csrf().disable().authorizeRequests()
//					.antMatchers("/login").permitAll()
//					.antMatchers("/user").hasAnyRole("USER")
//					.antMatchers("/admin").hasAnyRole("ADMIN")
//					.anyRequest().authenticated()
//					.and()
//					.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//		}

//	}
}
