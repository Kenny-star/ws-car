package com.kenny.microservices.core.buildPrice.GetBuildPrice.datalayer;

import com.kenny.microservices.core.buildPrice.Dao.BuildPriceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GetBuildPriceRepository extends CrudRepository<BuildPriceEntity, Integer> {
    @Transactional(readOnly = true)
    List<BuildPriceEntity> findByCarId(int carId);
}