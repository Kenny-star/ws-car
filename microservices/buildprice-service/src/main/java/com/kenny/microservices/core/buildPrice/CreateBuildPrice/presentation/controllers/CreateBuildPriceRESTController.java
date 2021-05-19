package com.kenny.microservices.core.buildPrice.CreateBuildPrice.presentation.controllers;

import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.api.core.buildPrice.buildPriceCreateAPI;
import com.kenny.microservices.core.buildPrice.CreateBuildPrice.businesslayer.CreateBuildPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreateBuildPriceRESTController implements buildPriceCreateAPI {

    private static final Logger LOG = LoggerFactory.getLogger(CreateBuildPriceRESTController.class);

    private final CreateBuildPriceService buildPriceService;

    public CreateBuildPriceRESTController(CreateBuildPriceService buildPriceService){
        this.buildPriceService = buildPriceService;
    }



    @Override
    public BuildPrice createBuildPrice(BuildPrice model) {
        BuildPrice buildPrice = buildPriceService.createBuildPrice(model);
        LOG.debug("REST Controller createBuildPrice: created an entity: {} / {}", buildPrice.getCarId(), buildPrice.getBuildPriceId());
        return buildPrice;
    }


}