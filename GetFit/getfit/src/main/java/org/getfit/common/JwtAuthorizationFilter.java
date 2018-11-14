package org.getfit.common;

import io.jsonwebtoken.Jwts;
import org.getfit.services.ClientService;
import org.getfit.services.CoachService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final ClientService clientService;
    private final CoachService coachService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, CoachService coachService, ClientService clientService) {
        super(authenticationManager);
        this.clientService = clientService;
        this.coachService = coachService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        HttpServletRequest request1 = request;


        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        String user = Jwts.parser()
                .setSigningKey(JwtSecurityConstants.SECRET.getBytes())
                .parseClaimsJws(header.replace("Bearer ", ""))
                .getBody()
                .getSubject();

        if (user != null) {
            UserDetails userEntity;
            if (this.clientService.clientExists(user)) {
                userEntity = this.clientService.getClientByUsername(user);

            } else {
                userEntity = this.coachService.getCoachByUsername(user);
            }

            return new UsernamePasswordAuthenticationToken(
                    user,
                    null, userEntity.getAuthorities()
            );
        }
        return null;
    }
}
