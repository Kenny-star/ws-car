package com.kenny.microservices.core.carModel.CreateCarModel.presentation.controllers;

import com.kenny.api.core.carModel.CarModel;
import com.kenny.api.core.carModel.carModelCreateAPI;
import com.kenny.microservices.core.carModel.CreateCarModel.businesslayer.CreateCarModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CreateCarModelRESTController implements carModelCreateAPI {

    private static final Logger Log = LoggerFactory.getLogger(CreateCarModelRESTController.class);

    private final CreateCarModelService carModelService;

    //dependency injection
    public CreateCarModelRESTController(CreateCarModelService carModelService){
        this.carModelService = carModelService;
    }

    @Override
    public CarModel createCarModel(CarModel model) {
        CarModel car = carModelService.createCarModel(model);

        Log.debug("REST createCar: car created for carId: {}", car.getCarId());

        return car;
    }


}
