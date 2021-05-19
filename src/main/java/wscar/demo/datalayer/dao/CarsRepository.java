package wscar.demo.datalayer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wscar.demo.datalayer.entities.Cars;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Integer> {

}
