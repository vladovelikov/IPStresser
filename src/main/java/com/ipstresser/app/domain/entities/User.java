package com.ipstresser.app.domain.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @NotNull
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    @Email
    private String email;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime registeredOn;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private UserActivePlan userActivePlan;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "attacker", cascade = CascadeType.ALL)
    private List<Attack> attacks = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Plan> plans;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Article> articles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Cryptocurrency> cryptocurrencies;

    @OneToOne(mappedBy = "author",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private Comment comment;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
}
