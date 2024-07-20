package br.com.mobicare.testcase.eclipsehotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDate checkin;

    @Column(nullable = false)
    private LocalDate checkout;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnTransformer(write="?::reservation_status")
    private ReservationStatus status;

}
