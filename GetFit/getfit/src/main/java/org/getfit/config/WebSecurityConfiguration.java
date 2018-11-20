package org.getfit.config;

import org.getfit.common.JwtAuthenticationFilter;
import org.getfit.common.JwtAuthorizationFilter;
import org.getfit.services.ClientService;
import org.getfit.services.CoachService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final CoachService coachService;

    private final ClientService clientService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProviderManager providerManager;

    public WebSecurityConfiguration(CoachService coachService, ClientService clientService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.coachService = coachService;
        this.clientService = clientService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.providerManager = new ProviderManager(this.initializeProviders());
    }

    ArrayList<AuthenticationProvider> initializeProviders() {
        AuthenticationProvider provider = new CustomAuthenticationProvider(this.clientService,this.coachService, this.bCryptPasswordEncoder);
        ArrayList<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(provider);
        return providers;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/coach/**", "/client/**","/login","/home","/exercises/**").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.coachService,this.clientService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.providerManager.getProviders().get(0));
        auth
                .userDetailsService(this.clientService)
                .passwordEncoder(this.bCryptPasswordEncoder);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
}
