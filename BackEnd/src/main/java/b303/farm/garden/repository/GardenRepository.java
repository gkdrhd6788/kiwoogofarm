package b303.farm.garden.repository;

import b303.farm.garden.entity.Garden;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GardenRepository extends JpaRepository<Garden, Long> {

}
