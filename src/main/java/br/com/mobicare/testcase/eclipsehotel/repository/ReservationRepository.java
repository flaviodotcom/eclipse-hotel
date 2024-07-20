package br.com.mobicare.testcase.eclipsehotel.repository;

import br.com.mobicare.testcase.eclipsehotel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCheckinBetweenOrCheckoutBetween(LocalDate checkinStart, LocalDate checkinEnd, LocalDate checkoutStart, LocalDate checkoutEnd);

    @Query(value = "SELECT * FROM \"reservations\" r WHERE r.status = reservation_status_from_text(:status)", nativeQuery = true)
    List<Reservation> findByStatus(@Param("status") String status);

}
