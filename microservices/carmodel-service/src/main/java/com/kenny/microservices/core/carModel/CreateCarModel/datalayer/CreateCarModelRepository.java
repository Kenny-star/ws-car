package com.kenny.microservices.core.carModel.CreateCarModel.datalayer;

import com.kenny.microservices.core.carModel.Dao.CarModelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CreateCarModelRepository extends PagingAndSortingRepository<CarModelEntity, String> {

    Optional<CarModelEntity> findByCarId(int carId);
}