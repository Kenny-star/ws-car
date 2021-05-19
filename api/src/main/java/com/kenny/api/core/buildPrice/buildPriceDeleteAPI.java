package com.kenny.api.core.buildPrice;

import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface buildPriceDeleteAPI {

    @DeleteMapping(
            value = "/buildprice"
    )
    void deleteBuildPrices(@RequestParam(value = "carId", required = true) int carId);

}
