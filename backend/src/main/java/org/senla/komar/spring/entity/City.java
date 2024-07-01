package org.senla.komar.spring.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "cities")
public class City extends BaseEntity<Long> {

    @Column(name = "city_name", nullable = false, length = 25, unique = true)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "city",  cascade = CascadeType.ALL)
    private List<Address> addresses;
}
