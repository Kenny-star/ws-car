package com.kenny.microservices.core.carModel.DeleteCarModel.presentation.controllers;

import com.kenny.api.core.carModel.carModelDeleteAPI;
import com.kenny.microservices.core.carModel.DeleteCarModel.businesslayer.DeleteCarModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeleteCarModelRESTController implements carModelDeleteAPI {

    private static final Logger Log = LoggerFactory.getLogger(DeleteCarModelRESTController.class);

    private final DeleteCarModelService carModelService;

    //dependency injection
    public DeleteCarModelRESTController(DeleteCarModelService carModelService){
        this.carModelService = carModelService;
    }

    @Override
    public void deleteCarModel(int carId) {
        Log.debug("REST deleteCar: tried to delete carId: {}", carId);
        carModelService.deleteCarModel((carId));
    }
}
