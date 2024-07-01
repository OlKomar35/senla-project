package org.senla.komar.spring.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "guests")
public class Guest extends BaseEntity<Long>  {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "rank_guest", precision = 3, scale = 1)
    private BigDecimal rank;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "guest_feedback",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "feedback_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Feedback> feedbacks;


    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Booking> bookings;
}
