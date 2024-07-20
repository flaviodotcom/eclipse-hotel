package br.com.mobicare.testcase.eclipsehotel.service;

import br.com.mobicare.testcase.eclipsehotel.model.Room;
import br.com.mobicare.testcase.eclipsehotel.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationService reservationService;

    public List<Room> findAll() {
        logger.info("Procurando por todos os quartos");
        return roomRepository.findAll();
    }

    public Room findById(Long id) {
        logger.info("Procurando pelo quarto de id " + id);
        return roomRepository.findById(id).orElse(null);
    }

    public Room save(Room room) {
        logger.info("Salvando o registro do quarto de id " + room.getId());
        return roomRepository.save(room);
    }

    public Room update(Long id, Room roomDetails) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room != null) {
            logger.info("Atualizando o registro do quarto de id " + roomDetails.getId());
            room.setType(roomDetails.getType());
            room.setPrice(roomDetails.getPrice());
            return roomRepository.save(room);
        }
        logger.info("Nenhum registro encontrado com o id especificado");
        return null;
    }

    /**
     * Método que exclui um quarto, incluindo suas associações na tabela reservations.
     * Ou seja, todas reservas registradas nesse quarto são excluidas.
     * @param id id do registro a ser deletado.
     */
    @Transactional
    public void delete(Long id) {
        logger.info("Excluindo o registro do quarto de id " + id);
        reservationService.deleteByRoomId(id);
        roomRepository.deleteById(id);
    }
}
