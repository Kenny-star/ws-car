package com.kenny.microservices.core.carModel.GetCarModel.datalayer;

import com.kenny.microservices.core.carModel.Dao.CarModelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface GetCarModelRepository extends PagingAndSortingRepository<CarModelEntity, String> {

    Optional<CarModelEntity> findByCarId(int carId);
}