package org.senla.komar.spring.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.senla.komar.spring.enums.BookingStatus;
import org.senla.komar.spring.enums.PaymentStatus;
import org.senla.komar.spring.enums.TypeFood;
import org.senla.komar.spring.enums.TypePayment;

import jakarta.persistence.*;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @Column(name = "count_guests", nullable = false)
    private int countGuests;

    @Column(name = "check_in_date", nullable = false)
    private Date checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private Date checkOutDate;

    @Column(name = "arrival_time", nullable = false)
    private Date arrivalTime;

    @Column(name = "cost_booking", precision = 10, scale = 2)
    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "comment_booking", length = 300)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_payment")
    private TypePayment typePayment;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "booking_facilities",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private List<Facility> facilities;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_food")
    private TypeFood typeFood;
}
