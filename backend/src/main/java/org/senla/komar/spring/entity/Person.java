package org.senla.komar.spring.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "people")

@NamedEntityGraph(name = "persson-entity-graph", attributeNodes = {
        @NamedAttributeNode("guest"),
        @NamedAttributeNode("employee")
})

public class Person extends BaseEntity<Long> {

    @Column(name = "surname", nullable = false, length = 35)
    private String surname;

    @Column(name = "firstname", nullable = false, length = 35)
    private String firstname;

    @Column(name = "middlename", length = 35)
    private String middleName;

    @Column(name = "passport_series", nullable = false, length = 5)
    private String passportSeries;

    @Column(name = "passport_number", nullable = false)
    private int passportNumber;

    @Column(name = "phone_number", nullable = false, unique = true, length = 30)
    private String phoneNumber;

    @Column(name = "e_mail", length = 50)
    private String email;

    @Column(name = "login", length = 100, unique = true, nullable = false)
    private String login;

    @Column(name = "password", length = 350, nullable = false)
    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Guest guest;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private Employee employee;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "people_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;
}
