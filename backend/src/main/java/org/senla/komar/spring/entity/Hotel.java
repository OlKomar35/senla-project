package org.senla.komar.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.senla.komar.spring.enums.TypeHotel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "hotels")
@NamedEntityGraph(name = "graph.hotel.feedback",
        attributeNodes = @NamedAttributeNode("feedbacks"))
public class Hotel extends BaseEntity<Long> {

    @Column(name = "hotel_name", length = 100, nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "phone_number", length = 30, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "e_mail", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "web_site", length = 200)
    private String webSite;

    @Column(name = "rank", precision = 3, scale = 1)
    private BigDecimal rank;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_hotel")
    private TypeHotel typeHotel;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "count_rooms", nullable = false)
    private int countRooms;

    @Column(name = "num_available_rooms")
    private int numAvailableRooms;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<AmenityHotel> amenities;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "hotel_feedback",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "feedback_id")
    )
    private List<Feedback> feedbacks;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "hotel_employees",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;
}
