package com.kenny.microservices.core.buildPrice.CreateBuildPrice.businesslayer;

import com.kenny.Utils.http.ServiceUtil;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.microservices.core.buildPrice.Dao.BuildPriceEntity;
import com.kenny.microservices.core.buildPrice.CreateBuildPrice.datalayer.CreateBuildPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateBuildPriceServiceImpl implements CreateBuildPriceService {
    private static final Logger LOG = LoggerFactory.getLogger(CreateBuildPriceServiceImpl.class);

    private final CreateBuildPriceRepository repository;

    private final CreateBuildPriceMapper mapper;

    private final ServiceUtil serviceUtil;

    public CreateBuildPriceServiceImpl(CreateBuildPriceRepository repository, CreateBuildPriceMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public BuildPrice createBuildPrice(BuildPrice model) {

        BuildPriceEntity entity = mapper.modelToEntity(model);
        BuildPriceEntity newEntity = repository.save(entity);

        LOG.debug("BuildPriceService createBuildPrice: created a buildPrice entity: {}/{}", model.getCarId(), model.getBuildPriceId());
        return mapper.entityToModel(newEntity);
    }

}


