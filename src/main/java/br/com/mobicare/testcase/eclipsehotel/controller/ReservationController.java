package br.com.mobicare.testcase.eclipsehotel.controller;

import br.com.mobicare.testcase.eclipsehotel.model.Reservation;
import br.com.mobicare.testcase.eclipsehotel.model.Room;
import br.com.mobicare.testcase.eclipsehotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation novaReserva = reservationService.createReservation(reservation);
        return ResponseEntity.ok(novaReserva);
    }

    /**
     * Atualiza uma reserva.
     * Não permite alterar reserva caso status seja ABSENCE, CANCELED ou FINISHED.
     * @return status 200 OK.
     */
    @PutMapping("/{id}/end")
    public ResponseEntity<Reservation> endReservation(@PathVariable Long id) {
        Reservation reservaFinalizada = reservationService.endReservation(id);
        return ResponseEntity.ok(reservaFinalizada);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationsByIntervaloDeDatas(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Reservation> reservations = reservationService.getReservationsPorIntervaloDeDatas(startDate, endDate);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/occupied-rooms")
    public ResponseEntity<List<Room>> getRoomsOcupados() {
        List<Room> quartosOcupados = reservationService.getRoomsOcupados();
        return ResponseEntity.ok(quartosOcupados);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> parametroAusente(MissingServletRequestParameterException ex) {
        String nomeParametro = ex.getParameterName();
        return ResponseEntity.badRequest().body("Parâmetro ausente: " + nomeParametro);
    }
}
