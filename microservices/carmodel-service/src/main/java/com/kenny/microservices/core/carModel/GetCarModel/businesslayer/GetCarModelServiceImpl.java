package com.kenny.microservices.core.carModel.GetCarModel.businesslayer;

import com.kenny.Utils.exceptions.NotFoundException;
import com.kenny.Utils.http.ServiceUtil;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.microservices.core.carModel.Dao.CarModelEntity;
import com.kenny.microservices.core.carModel.GetCarModel.datalayer.GetCarModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetCarModelServiceImpl implements GetCarModelService {

    private static final Logger LOG = LoggerFactory.getLogger(GetCarModelServiceImpl.class);

    private final GetCarModelRepository repository;

    private final GetCarModelMapper mapper;

    private final ServiceUtil serviceUtil;

    public GetCarModelServiceImpl(GetCarModelRepository repository, GetCarModelMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public CarModel getCarById(int carId) {

        CarModelEntity entity = repository.findByCarId(carId)
                .orElseThrow(() -> new NotFoundException("No car found for carId: " + carId));

        CarModel response = mapper.entityToModel(entity);
        response.setServiceAddress(serviceUtil.getServiceAddress());

        LOG.debug("Product getCarById: found carId: {}", response.getCarId());
        return response;

    }

}
