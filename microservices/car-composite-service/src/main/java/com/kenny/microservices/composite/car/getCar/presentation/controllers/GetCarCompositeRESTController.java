package com.kenny.microservices.composite.car.getCar.presentation.controllers;

import com.kenny.api.composite.car.*;
import com.kenny.microservices.composite.car.getCar.businesslayer.CarCompositeGetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetCarCompositeRESTController implements GetCarCompositeServiceAPI {

    private static final Logger Log = LoggerFactory.getLogger(GetCarCompositeRESTController.class);
    private final CarCompositeGetService carCompositeService;

    public GetCarCompositeRESTController(CarCompositeGetService carCompositeService) {
        this.carCompositeService = carCompositeService;

    }

    @Override
    public CarAggregate getCompositeCar(int carId) {
        Log.debug("CarComposite REST received getCompositeCar request for carId: {}", carId);

        CarAggregate carAggregate = carCompositeService.getCarModel(carId);
        return carAggregate;
    }

}
