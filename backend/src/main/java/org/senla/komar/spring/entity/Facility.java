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
@Table(name = "facilities")
public class Facility extends BaseEntity<Integer>  {

    @Column(name = "facility_name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "cost_facility", precision = 10, scale = 2, nullable = false)
    private BigDecimal cost;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "booking_facilities",
            joinColumns = @JoinColumn(name = "facility_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Booking> bookings;
}
