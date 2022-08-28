package com.ipstresser.app.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "plans")
public class Plan extends BaseEntity {

    @Column(name = "type")
    @NotNull
    private String type;

    @Column(name = "price")
    @Positive
    @NotNull
    private BigDecimal price;

    @Column(name = "duration_in_days")
    @Positive
    @NotNull
    private int durationInDays;

    @Column(name = "max_boot_time_in_seconds")
    @Positive
    @NotNull
    private double maxBootTimeInSeconds;

    @Column(name = "max_boots_time_per_day")
    @Positive
    @NotNull
    private int maxBootsPerDay;

    @Column(name = "servers")
    @PositiveOrZero
    @NotNull
    private int servers;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User author;

    public Plan(@NotNull String type, @Positive @NotNull BigDecimal price, @NotNull @Positive int durationInDays, @NotNull @Positive double maxBootTimeInSeconds, @NotNull @Positive int maxBootsPerDay, @NotNull @PositiveOrZero int servers, LocalDateTime createdOn) {
        this.type = type;
        this.price = price;
        this.durationInDays = durationInDays;
        this.maxBootTimeInSeconds = maxBootTimeInSeconds;
        this.maxBootsPerDay = maxBootsPerDay;
        this.servers = servers;
        this.createdOn = createdOn;
    }
}
