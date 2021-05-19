package wscar.demo.businesslayer.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wscar.demo.datalayer.dao.CarsRepository;
import wscar.demo.datalayer.entities.Cars;

@Service
public class CarsServiceImpl implements CarsService{

    private CarsRepository repo;

    public CarsServiceImpl(CarsRepository repo){this.repo = repo; }

    @Override
    public Page<Cars> getAllCars(Integer pageNum, Integer pageSize) {

        Pageable p = PageRequest.of(pageNum - 1, pageSize);
        return repo.findAll(p);
    }

    @Override
    public Cars getCarById(Integer id) {

        Cars c = repo.findById(id).get();
        return c;
    }

    @Override
    public Cars addNewCar(Cars car) {
        Cars c = repo.save(car);
        return c;
    }

    @Override
    public Cars updateCar(Cars car) {
        Cars c = repo.save(car);
        return c;
    }

    @Override
    public void deleteCar(Cars car) {
        repo.delete(car);
    }
}
