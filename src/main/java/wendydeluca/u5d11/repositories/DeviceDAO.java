package wendydeluca.u5d11.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wendydeluca.u5d11.entities.Device;
import wendydeluca.u5d11.entities.User;

import java.util.UUID;

@Repository
public interface DeviceDAO extends JpaRepository<Device,UUID> {

}
