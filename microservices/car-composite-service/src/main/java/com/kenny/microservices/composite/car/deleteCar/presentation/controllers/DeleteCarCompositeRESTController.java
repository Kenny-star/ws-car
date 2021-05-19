package com.kenny.microservices.composite.car.deleteCar.presentation.controllers;

import com.kenny.api.composite.car.CarAggregate;
import com.kenny.api.composite.car.CreateCarCompositeServiceAPI;
import com.kenny.api.composite.car.DeleteCarCompositeServiceAPI;
import com.kenny.microservices.composite.car.createCar.businesslayer.CarCompositeCreateService;
import com.kenny.microservices.composite.car.deleteCar.businesslayer.CarCompositeDeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeleteCarCompositeRESTController implements DeleteCarCompositeServiceAPI {

    private static final Logger Log = LoggerFactory.getLogger(DeleteCarCompositeRESTController.class);
    private final CarCompositeDeleteService carCompositeService;

    public DeleteCarCompositeRESTController(CarCompositeDeleteService carCompositeService) {
        this.carCompositeService = carCompositeService;

    }

    @Override
    public void deleteCompositeCar(int carId) {
        Log.debug("CarComposite REST received deleteCompositeCar");
        carCompositeService.deleteCarModel(carId);
    }
}
