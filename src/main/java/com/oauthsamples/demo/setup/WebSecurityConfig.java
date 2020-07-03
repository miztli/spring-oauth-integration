package com.oauthsamples.demo.setup;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    public static final String HOME = "/";

    public static final String ERROR = "/error";

    public static final String WEBJARS = "/webjars/*?";

    public static final String LOGOUT_SUCCESS = "/";

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests(reqCustomizer -> reqCustomizer
                        .antMatchers(HOME, ERROR, WEBJARS).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exHandler -> exHandler
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(HOME))
                )
                .oauth2Login(loginConfigurer -> loginConfigurer
                        .defaultSuccessUrl(HOME, true))
                .csrf(csrfConfigurer -> csrfConfigurer
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl(LOGOUT_SUCCESS).permitAll());
    }
}
