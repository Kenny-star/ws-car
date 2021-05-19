package com.kenny.microservices.core.carModel.GetCarModel.presentation.controllers;

import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.exceptions.NotManufacturedException;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.api.core.carModel.carModelGetAPI;
import com.kenny.microservices.core.carModel.GetCarModel.businesslayer.GetCarModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetCarModelRESTController implements carModelGetAPI {

    private static final Logger Log = LoggerFactory.getLogger(GetCarModelRESTController.class);

    private final GetCarModelService carModelService;

    //dependency injection
    public GetCarModelRESTController(GetCarModelService carModelService){
        this.carModelService = carModelService;
    }
    @Override
    public CarModel getCarModel(int carId) {

        Log.debug("/car MS returns the found car for carId: " + carId);

        if(carId < 1) throw new InvalidInputException("Invalid carId: " + carId);
        if (carId == 5) throw new NotManufacturedException("Invalid carId: " + carId + ", they're no longer manufactured.");
        //if(carId == 13) throw new NotFoundException("No car found for carId: " + carId);

        CarModel car = carModelService.getCarById(carId);

        return car;
    }
}
