package com.kenny.microservices.core.carModel.DeleteCarModel.datalayer;

import com.kenny.microservices.core.carModel.Dao.CarModelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DeleteCarModelRepository extends PagingAndSortingRepository<CarModelEntity, String> {

    Optional<CarModelEntity> findByCarId(int carId);
}