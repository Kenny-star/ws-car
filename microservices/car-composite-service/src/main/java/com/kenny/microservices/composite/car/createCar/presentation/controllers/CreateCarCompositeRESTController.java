package com.kenny.microservices.composite.car.createCar.presentation.controllers;

import com.kenny.api.composite.car.CarAggregate;
import com.kenny.api.composite.car.CreateCarCompositeServiceAPI;
import com.kenny.api.composite.car.GetCarCompositeServiceAPI;
import com.kenny.microservices.composite.car.createCar.businesslayer.CarCompositeCreateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CreateCarCompositeRESTController implements CreateCarCompositeServiceAPI {

    private static final Logger Log = LoggerFactory.getLogger(CreateCarCompositeRESTController.class);
    private final CarCompositeCreateService carCompositeService;

    public CreateCarCompositeRESTController(CarCompositeCreateService carCompositeService) {
        this.carCompositeService = carCompositeService;

    }

    @Override
    public void createCompositeCar(CarAggregate model) {
        Log.debug("CarComposite REST received createCompositeCar");
        carCompositeService.createCarModel(model);
    }

}
