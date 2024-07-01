package org.senla.komar.spring.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "attractions")
public class Attraction extends BaseEntity<Long> {

    @Column(name = "attraction_name", nullable = false, length = 150)
    private String name;

    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "description", length = 1000)
    private String description;
}
