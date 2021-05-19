package com.kenny.microservices.composite.car.createCar.businesslayer;

import com.kenny.api.composite.car.CarAggregate;

public interface CarCompositeCreateService {
    public void createCarModel(CarAggregate model);

}
