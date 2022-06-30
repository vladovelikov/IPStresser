package com.ipstresser.app.domain.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "username", unique = true)
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "registered_on")
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

    public User (String username, String password, String email, String imageUrl, LocalDateTime registeredOn, UserActivePlan userActivePlan, Set<Role> roles,
                 List<Attack> attacks, List<Plan> plans, List<Article> articles,
                 List<Announcement> announcements, List<Cryptocurrency> cryptocurrencies, Comment comment) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;
        this.registeredOn = registeredOn;
        this.userActivePlan = userActivePlan;
        this.roles = roles;
        this.attacks = attacks;
        this.plans = plans;
        this.articles = articles;
        this.announcements = announcements;
        this.cryptocurrencies = cryptocurrencies;
        this.comment = comment;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
