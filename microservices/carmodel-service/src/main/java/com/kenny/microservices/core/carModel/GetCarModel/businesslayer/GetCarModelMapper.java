package com.kenny.microservices.core.carModel.GetCarModel.businesslayer;

import com.kenny.api.core.carModel.CarModel;
import com.kenny.microservices.core.carModel.Dao.CarModelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GetCarModelMapper {

    @Mapping(target = "serviceAddress", ignore=true)
    CarModel entityToModel(CarModelEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    CarModelEntity modelToEntity(CarModel model);
}