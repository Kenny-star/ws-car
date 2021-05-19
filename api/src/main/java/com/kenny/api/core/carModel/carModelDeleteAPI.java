package com.kenny.api.core.carModel;

import org.springframework.web.bind.annotation.*;

public interface carModelDeleteAPI {

    @DeleteMapping(
            value = "/carmodel/{carId}"
    )
    void deleteCarModel(@PathVariable int carId);


}
