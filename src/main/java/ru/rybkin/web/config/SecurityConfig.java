package ru.rybkin.web.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/news/**", "/news_category/**").permitAll()
                .antMatchers(HttpMethod.POST, "/news/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/news/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/news/**", "/news_category/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/news_category/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/news_category/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

   /* @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(Role.ADMIN.name())
                .build(),
                User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles(Role.USER.name())
                .build()
        );
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
