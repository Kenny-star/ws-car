package com.kenny.api.core.carModel;

import org.springframework.web.bind.annotation.*;

public interface carModelGetAPI {
    @GetMapping(
            value = "/carmodel/{carId}",
            produces = "application/json"
    )
    CarModel getCarModel(@PathVariable int carId);


}
