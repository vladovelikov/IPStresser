package com.ipstresser.app.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_active_plans")
public class UserActivePlan {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private Plan plan;

    @Column
    @Positive
    @NotNull
    private int leftDays;

    @Column
    @PositiveOrZero(message = "Your daily limit is reached!")
    @NotNull
    private int leftAttacksForTheDay;

    @Column(name = "started_on")
    private LocalDateTime startedOn;

    public UserActivePlan(Plan plan, @Positive @NotNull int leftDays, @PositiveOrZero @NotNull int leftAttacksForTheDay, LocalDateTime startedOn) {
        this.plan = plan;
        this.leftDays = leftDays;
        this.leftAttacksForTheDay = leftAttacksForTheDay;
        this.startedOn = startedOn;
    }


}
