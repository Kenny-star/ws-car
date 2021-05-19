package com.kenny.microservices.core.buildPrice.DeleteBuildPrice.datalayer;

import com.kenny.microservices.core.buildPrice.Dao.BuildPriceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeleteBuildPriceRepository extends CrudRepository<BuildPriceEntity, Integer> {
    @Transactional(readOnly = true)
    List<BuildPriceEntity> findByCarId(int carId);
}