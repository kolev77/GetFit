package org.getfit.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "coaches")
public class Coach extends User {

    private String certificates;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "coach_client",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    @JsonIgnoreProperties("coaches")
    private List<Client> clients;
    // rating

    public Coach() {
        this.clients = new ArrayList<>();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + "COACH"));
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
