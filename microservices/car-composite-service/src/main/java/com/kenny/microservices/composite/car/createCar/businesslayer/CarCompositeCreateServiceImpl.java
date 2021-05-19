package com.kenny.microservices.composite.car.createCar.businesslayer;

import com.kenny.Utils.exceptions.NotFoundException;
import com.kenny.Utils.http.ServiceUtil;
import com.kenny.api.composite.car.BuildPriceSummary;
import com.kenny.api.composite.car.CarAggregate;
import com.kenny.api.composite.car.CustomerSummary;
import com.kenny.api.composite.car.ServiceAddress;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.api.core.customer.Customer;
import com.kenny.microservices.composite.car.createCar.integrationlayer.CarCompositeCreateIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarCompositeCreateServiceImpl implements CarCompositeCreateService {
    private static final Logger LOG = LoggerFactory.getLogger(CarCompositeCreateServiceImpl.class);

    private final CarCompositeCreateIntegration integration;
    private final ServiceUtil serviceUtil;

    public CarCompositeCreateServiceImpl(CarCompositeCreateIntegration integration, ServiceUtil serviceUtil){
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }
    @Override
    public void createCarModel(CarAggregate model) {
        try{
            LOG.debug("createCompositeCar: creates a new composite entity for carId: {}", model.getCarId());

            CarModel carModel = new CarModel(model.getCarId(), model.getName(), model.getModel(), model.getCost(), model.getProvider(), model.getRelease_year()
                                            , model.getCountry(), model.getContent(), null );

            integration.createCarModel(carModel);

            if(model.getBuildPrice() != null){
                model.getBuildPrice().forEach(r -> {
                    BuildPrice buildPrice = new BuildPrice(model.getCarId(), r.getBuildPriceId(),
                            r.getSeats(), r.getCost(), r.getRims(), r.getBumper(), r.getAutomatic(), r.getColor(), r.getLedLights(), r.getGpsEmbedded(), r.getContent(), null);

                    integration.createBuildPrice(buildPrice);
                });
            }
            if(model.getCustomer() != null){
                model.getCustomer().forEach(r -> {
                    Customer customer = new Customer(model.getCarId(), r.getCustomerId(), r.getFirstName(), r.getLastName(), r.getAge(), r.getPhoneNumber(), r.getEmailAddress()
                                                    , r.isLease(), r.isFinance(), r.getContent(), null);

                    integration.createCustomer(customer);
                });
            }

            LOG.debug("createCompositeCar: composite entities created for carId: {}", model.getCarId());

        }catch(RuntimeException ex){
            LOG.warn("createCompositeCar failed", ex);
            throw ex;
        }
    }
    private CarAggregate createCarAggregate(CarModel carModel, List<BuildPrice> buildPrices, List<Customer> customers, String serviceAddress){
        int carId = carModel.getCarId();
        String name = carModel.getName();
        String model = carModel.getModel();
        Double cost = carModel.getCost();
        String provider = carModel.getProvider();
        int release_year = carModel.getRelease_year();
        String country = carModel.getCountry();
        String content = carModel.getContent();


        List<BuildPriceSummary> buildPriceSummaries = (buildPrices == null) ? null :
                buildPrices.stream()
                        .map(r -> new BuildPriceSummary(r.getBuildPriceId(), r.getSeats(), r.getCost(), r.getRims(), r.getBumper(), r.getAutomatic(), r.getColor(), r.getLedLights(), r.getGpsEmbedded(), r.getContent()))
                        .collect(Collectors.toList());

        List<CustomerSummary> customerSummaries = (customers == null) ? null:
                customers.stream()
                        .map(r -> new CustomerSummary(r.getCustomerId(), r.getFirstName(), r.getLastName(), r.getAge(), r.getPhoneNumber(), r.getEmailAddress(), r.isLease(), r.isFinance(), r.getContent()))
                        .collect(Collectors.toList());

        String carAddress = carModel.getServiceAddress();
        String buildPriceAddress = (buildPrices != null && buildPrices.size() > 0)
                ? buildPrices.get(0).getServiceAddress(): "";
        String customerAddress = (customers != null & customers.size() > 0)
                ? customers.get(0).getServiceAddress() : "";
        ServiceAddress serviceAddresses = new ServiceAddress(serviceAddress, carAddress, buildPriceAddress, customerAddress);

        return new CarAggregate(carId, name, model, cost, provider, release_year, country, content, buildPriceSummaries,customerSummaries,serviceAddresses);
    }
}
