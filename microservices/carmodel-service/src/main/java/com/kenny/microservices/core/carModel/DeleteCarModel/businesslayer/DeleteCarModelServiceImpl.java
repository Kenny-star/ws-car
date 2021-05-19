package com.kenny.microservices.core.carModel.DeleteCarModel.businesslayer;

import com.kenny.Utils.http.ServiceUtil;
import com.kenny.microservices.core.carModel.DeleteCarModel.datalayer.DeleteCarModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteCarModelServiceImpl implements DeleteCarModelService {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteCarModelServiceImpl.class);

    private final DeleteCarModelRepository repository;

    private final DeleteCarModelMapper mapper;

    private final ServiceUtil serviceUtil;

    public DeleteCarModelServiceImpl(DeleteCarModelRepository repository, DeleteCarModelMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public void deleteCarModel(int carId) {
        LOG.debug("deleteCar: trying to delete entity with carId: {}", carId);

        repository.findByCarId(carId).ifPresent(e -> repository.delete(e));
    }
}
