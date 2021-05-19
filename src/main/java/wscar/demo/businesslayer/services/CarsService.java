package wscar.demo.businesslayer.services;

import org.springframework.data.domain.Page;
import wscar.demo.datalayer.entities.Cars;

public interface CarsService {
    public Page<Cars> getAllCars(Integer pageNum, Integer pageSize);

    public Cars getCarById(Integer id);

    public Cars addNewCar(Cars car);

    public Cars updateCar(Cars car);

    public void deleteCar(Cars car);
}
