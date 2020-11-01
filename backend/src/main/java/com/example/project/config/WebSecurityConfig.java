package com.example.project.config;

import com.example.project.security.JwtTokenFilterConfigurer;
import com.example.project.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * This method is used to configure patterns to define protected/unprotected API endpoints.
     * CSRF protection is disabled because of not using Cookies.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        //zbog jwt
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        System.out.println("WEB SECURITY CONFIGURE");
        http.cors();
        // Entry points
        http.authorizeRequests()//
        		.antMatchers("/").hasRole("ADMIN")
        		.antMatchers("/user/comfirmRegistration").permitAll()
        		.antMatchers("/user/getChangePasswordCode").permitAll()
                .antMatchers("/user/signin").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/h2-console/**/**").permitAll()
                .antMatchers("/user/changePassword").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated();

        // If a user try to access a resource without having enough permissions
        //http.exceptionHandling().accessDeniedPage("/signin");
        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}