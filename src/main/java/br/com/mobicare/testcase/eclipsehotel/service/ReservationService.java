package br.com.mobicare.testcase.eclipsehotel.service;

import br.com.mobicare.testcase.eclipsehotel.model.Reservation;
import br.com.mobicare.testcase.eclipsehotel.model.ReservationStatus;
import br.com.mobicare.testcase.eclipsehotel.model.Room;
import br.com.mobicare.testcase.eclipsehotel.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);


    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        logger.info("Criando reserva com status " + reservation.getStatus());
        return reservationRepository.save(reservation);
    }

    public Reservation endReservation(Long id) {
        logger.info("Encerrando reserva de id " + id);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhuma reserva encontrada com o id " + id));

        ReservationStatus status = reservation.getStatus();
        if (isFinalStatus(status)) {
            throw new RuntimeException("Não é possível atualizar uma reserva com status " + status);
        }

        reservation.setStatus(ReservationStatus.FINISHED);
        logger.info("Reserva encerrada com sucesso. Status atual " + status);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsPorIntervaloDeDatas(LocalDate startDate, LocalDate endDate) {
        logger.info("Buscando reservas pelo intervalo de datas entre {} e {}", startDate, endDate);
        return reservationRepository.findAllByCheckinBetweenOrCheckoutBetween(startDate, endDate, startDate, endDate);
    }

    public List<Room> getRoomsOcupados() {
        logger.info("Buscando quartos ocupados");
        List<Reservation> reservations = reservationRepository.findByStatus(String.valueOf(ReservationStatus.IN_USE));

        if (reservations.isEmpty()) {
            logger.info("Nenhum quarto ocupado encontrado");
        } else {
            logger.info("{} quarto(s) ocupado(s)", reservations.size());
        }

        return reservations.stream()
                .map(Reservation::getRoom)
                .collect(Collectors.toList());
    }


    private boolean isFinalStatus(ReservationStatus status) {
        return status == ReservationStatus.CANCELED ||
                status == ReservationStatus.FINISHED ||
                status == ReservationStatus.ABSENCE;
    }
}
