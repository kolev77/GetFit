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
@Table(name = "clients")
public class Client extends User {


    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @ManyToMany(mappedBy = "clients",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("clients")
    private List<Coach> coaches;

    public Client() {
        this.coaches = new ArrayList<>();
    }


    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + "CLIENT"));
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Coach> coaches) {
        this.coaches = coaches;
    }
}
