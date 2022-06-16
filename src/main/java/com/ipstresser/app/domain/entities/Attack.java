package com.ipstresser.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attacks")
public class Attack {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "host")
    @NotNull
    private String host;

    @Column(name = "port")
    @NotNull
    private String port;

    @Column(name = "method")
    @NotNull
    @Enumerated(EnumType.STRING)
    private MethodType method;

    @Column(name = "servers")
    @NotNull
    private int servers;

    @Column(name = "expires_on")
    @NotNull
    private LocalDateTime expiresOn;

    @ManyToOne
    @JoinColumn(name = "attacker_id", referencedColumnName = "id")
    private User attacker;
}
