package com.kenny.api.core.buildPrice;

import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface buildPriceCreateAPI {
    @PostMapping(
            value = "/buildprice",
            consumes = "application/json",
            produces = "application/json"
    )
    BuildPrice createBuildPrice(@RequestBody BuildPrice model);

}
