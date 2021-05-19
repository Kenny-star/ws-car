package com.kenny.api.core.buildPrice;

import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface buildPriceGetAPI {
    @GetMapping(
            value = "/buildprice",
            produces = "application/json"
    )
    List<BuildPrice> getBuildPrice(@RequestParam(value = "carId", required = true) int carId);

}
