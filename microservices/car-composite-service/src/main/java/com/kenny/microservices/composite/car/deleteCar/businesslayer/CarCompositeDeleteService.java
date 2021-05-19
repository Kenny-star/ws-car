package com.kenny.microservices.composite.car.deleteCar.businesslayer;

import com.kenny.api.composite.car.CarAggregate;

public interface CarCompositeDeleteService {
    public void deleteCarModel(int carId);
}
