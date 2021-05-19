package com.kenny.microservices.core.buildPrice.GetBuildPrice.presentation.controllers;

import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.exceptions.NotManufacturedException;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.api.core.buildPrice.buildPriceGetAPI;
import com.kenny.microservices.core.buildPrice.GetBuildPrice.businesslayer.GetBuildPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetBuildPriceRESTController implements buildPriceGetAPI {

    private static final Logger LOG = LoggerFactory.getLogger(GetBuildPriceRESTController.class);

    private final GetBuildPriceService buildPriceService;

    public GetBuildPriceRESTController(GetBuildPriceService buildPriceService){
        this.buildPriceService = buildPriceService;
    }


    @Override
    public List<BuildPrice> getBuildPrice(int carId) {
        if (carId < 1) throw new InvalidInputException("Invalid carId: " + carId);

        if (carId == 5) throw new NotManufacturedException("Invalid carId: " + carId + ", they're no longer manufactured.");

        List<BuildPrice> listBuildPrices = buildPriceService.getByCarId(carId);
        /*
        if(carId == 113){
            Log.debug("No cars found for carId: {}", carId);
            return new ArrayList<>();
        }

        List<BuildPrice> listBuildPrice = new ArrayList<>();
        listBuildPrice.add(new BuildPrice(carId, 1, 56.34, 130.43, 5, 30000.00, "automatic", "teal", "lightblue", "yes", serviceUtil.getServiceAddress()));
        listBuildPrice.add(new BuildPrice(carId, 2, 87.45, 367.45, 7, 56000.00, "manual", "orange", "red", "no", serviceUtil.getServiceAddress()));
        listBuildPrice.add(new BuildPrice(carId, 3, 100.56, 400.53, 7, 89000.00, "manual", "blue", "gold", "yes", serviceUtil.getServiceAddress()));
*/

        LOG.debug("/buildPrice found response size: {}", listBuildPrices.size());
        return listBuildPrices;
    }
}