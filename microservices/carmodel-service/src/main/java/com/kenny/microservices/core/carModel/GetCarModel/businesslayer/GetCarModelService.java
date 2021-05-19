package com.kenny.microservices.core.carModel.GetCarModel.businesslayer;

import com.kenny.api.core.carModel.CarModel;

public interface GetCarModelService {

    public CarModel getCarById(int carId);

}
