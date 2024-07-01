package org.senla.komar.spring.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "addresses")

@NamedEntityGraph(name = "address-entity-graph", attributeNodes = {
        @NamedAttributeNode("attraction"),
        @NamedAttributeNode("hotel")
})
@NamedEntityGraph(name = "address-house-number-entity-graph", attributeNodes = {
        @NamedAttributeNode("attraction"),
        @NamedAttributeNode("hotel"),
        @NamedAttributeNode("city"),
        @NamedAttributeNode("street")
})
public class Address extends BaseEntity<Long>{

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "street_id")
    private Street street;

    @Column(name = "house_number")
    private int houseNumber;

    @Column(name = "latitude", precision = 10, scale = 6)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 6)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BigDecimal longitude;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Attraction attraction;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Hotel hotel;
}
