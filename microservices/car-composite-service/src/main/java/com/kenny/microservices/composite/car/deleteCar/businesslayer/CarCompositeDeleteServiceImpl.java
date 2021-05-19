package com.kenny.microservices.composite.car.deleteCar.businesslayer;

import com.kenny.Utils.exceptions.NotFoundException;
import com.kenny.Utils.http.ServiceUtil;
import com.kenny.api.composite.car.BuildPriceSummary;
import com.kenny.api.composite.car.CarAggregate;
import com.kenny.api.composite.car.CustomerSummary;
import com.kenny.api.composite.car.ServiceAddress;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.api.core.customer.Customer;
import com.kenny.microservices.composite.car.deleteCar.integrationlayer.CarCompositeDeleteIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarCompositeDeleteServiceImpl implements CarCompositeDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(CarCompositeDeleteServiceImpl.class);

    private final CarCompositeDeleteIntegration integration;
    private final ServiceUtil serviceUtil;

    public CarCompositeDeleteServiceImpl(CarCompositeDeleteIntegration integration, ServiceUtil serviceUtil){
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public void deleteCarModel(int carId) {

        LOG.debug("deleteCompositeCar: starting to delete a car aggregate for carId: {}", carId);

        integration.deleteCarModel(carId);
        integration.deleteBuildPrices(carId);
        integration.deleteCustomer(carId);

        LOG.debug("deleteCompositeCar: Deleted a car aggregate for carId: {}", carId);
    }

}
