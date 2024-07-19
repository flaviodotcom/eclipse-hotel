package br.com.mobicare.testcase.eclipsehotel.repository;

import br.com.mobicare.testcase.eclipsehotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
