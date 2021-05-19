package com.kenny.microservices.core.carModel.CreateCarModel.businesslayer;

import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.http.ServiceUtil;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.microservices.core.carModel.Dao.CarModelEntity;
import com.kenny.microservices.core.carModel.CreateCarModel.datalayer.CreateCarModelRepository;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateCarModelServiceImpl implements CreateCarModelService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateCarModelServiceImpl.class);

    private final CreateCarModelRepository repository;

    private final CreateCarModelMapper mapper;

    private final ServiceUtil serviceUtil;

    public CreateCarModelServiceImpl(CreateCarModelRepository repository, CreateCarModelMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public CarModel createCarModel(CarModel model) {
        try{
            CarModelEntity entity = mapper.modelToEntity(model);
            CarModelEntity newEntity = repository.save(entity);

            LOG.debug("createCarModel: entity created for carId: {}", model.getCarId());

            return mapper.entityToModel(newEntity);
        }
        catch(DuplicateKeyException dke){
            throw new InvalidInputException("Duplicate key for carId: " + model.getCarId());
        }
    }
}
