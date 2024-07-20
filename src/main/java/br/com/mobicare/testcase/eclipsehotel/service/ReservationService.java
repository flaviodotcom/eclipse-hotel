package br.com.mobicare.testcase.eclipsehotel.service;

import br.com.mobicare.testcase.eclipsehotel.model.Reservation;
import br.com.mobicare.testcase.eclipsehotel.model.ReservationStatus;
import br.com.mobicare.testcase.eclipsehotel.model.Room;
import br.com.mobicare.testcase.eclipsehotel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation endReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhuma reserva encontrada com o id " + id));
        reservation.setStatus(ReservationStatus.FINISHED);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsPorIntervaloDeDatas(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findAllByCheckinBetweenOrCheckoutBetween(startDate, endDate, startDate, endDate);
    }

    public List<Room> getRoomsOcupados() {
        List<Reservation> reservations = reservationRepository.findByStatus(String.valueOf(ReservationStatus.IN_USE));
        return reservations.stream()
                .map(Reservation::getRoom)
                .collect(Collectors.toList());
    }

}
