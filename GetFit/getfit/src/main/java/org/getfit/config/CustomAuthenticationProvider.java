package org.getfit.config;

import org.getfit.entities.User;
import org.getfit.services.ClientService;
import org.getfit.services.CoachService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CoachService coachService;

    private final ClientService clientService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(ClientService clientService, CoachService coachService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.coachService = coachService;
        this.clientService = clientService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user;
        if (coachService.coachExists(name)) {
            user = this.coachService.getCoachByUsername(name);
        }else {
            user = this.clientService.getClientByUsername(name);
        }

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            System.out.println("ma4");
            return new UsernamePasswordAuthenticationToken(name, user.getPassword(), new ArrayList<>());
        }

        System.out.println("");
        return null;
    }

    @Override
    public boolean supports(Class<?> aut) {
        return aut.equals(UsernamePasswordAuthenticationToken.class);
    }
}
