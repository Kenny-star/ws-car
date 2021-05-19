package com.kenny.microservices.core.buildPrice.CreateBuildPrice.businesslayer;

import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.microservices.core.buildPrice.Dao.BuildPriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreateBuildPriceMapper {

    @Mappings({
            @Mapping(target = "color", source="entity.coloration"),
            @Mapping(target = "serviceAddress", ignore = true)
    })
    BuildPrice entityToModel(BuildPriceEntity entity);

    @Mappings({
            @Mapping(target = "coloration", source="model.color"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    BuildPriceEntity modelToEntity(BuildPrice model);

    List<BuildPrice> entityListToModelList(List<BuildPriceEntity> entity);
    List<BuildPriceEntity> modelListToEntityList(List<BuildPrice> model);
}