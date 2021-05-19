package com.kenny.microservices.core.buildPrice.DeleteBuildPrice.presentation.controllers;

import com.kenny.api.core.buildPrice.buildPriceDeleteAPI;
import com.kenny.microservices.core.buildPrice.DeleteBuildPrice.businesslayer.DeleteBuildPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeleteBuildPriceRESTController implements buildPriceDeleteAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteBuildPriceRESTController.class);

    private final DeleteBuildPriceService buildPriceService;

    public DeleteBuildPriceRESTController(DeleteBuildPriceService buildPriceService){
        this.buildPriceService = buildPriceService;
    }

    @Override
    public void deleteBuildPrices(int carId) {
        LOG.debug("REST Controller deleteBuildPrices: trying to delete buildPrices for the car with carId: {}", carId);

        buildPriceService.deleteBuildPrices(carId);
    }

}