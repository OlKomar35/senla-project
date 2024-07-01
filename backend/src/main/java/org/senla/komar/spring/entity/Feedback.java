package org.senla.komar.spring.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity<Long>  {

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "score", nullable = false)
    private BigDecimal score;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "hotel_feedback",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "guest_feedback",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Guest guest;

}
