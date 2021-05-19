package com.kenny.microservices.composite.car.createCar.integrationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.exceptions.NotFoundException;
import com.kenny.Utils.exceptions.NotManufacturedException;
import com.kenny.Utils.http.HttpErrorInfo;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.api.core.buildPrice.buildPriceCreateAPI;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.api.core.carModel.carModelCreateAPI;
import com.kenny.api.core.customer.Customer;
import com.kenny.api.core.customer.customerCreateAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class CarCompositeCreateIntegration implements buildPriceCreateAPI, carModelCreateAPI, customerCreateAPI {
    private static final Logger Log = LoggerFactory.getLogger(CarCompositeCreateIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String buildPriceServiceUrl;
    private final String carModelServiceUrl;
    private final String customerServiceUrl;

    public CarCompositeCreateIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,

            @Value("${app.carmodel-service.host}") String carModelServiceHost,
            @Value("${app.carmodel-service.port}") int carModelServicePort,

            @Value("${app.buildprice-service.host}") String buildPriceServiceHost,
            @Value("${app.buildprice-service.port}") int buildPriceServicePort,

            @Value("${app.customer-service.host}") String customerServiceHost,
            @Value("${app.customer-service.port}") int customerServicePort

    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        carModelServiceUrl = "http://" + carModelServiceHost + ":" + carModelServicePort + "/carmodel";
        buildPriceServiceUrl = "http://" + buildPriceServiceHost + ":" + buildPriceServicePort + "/buildprice";
        customerServiceUrl = "http://" + customerServiceHost + ":" + customerServicePort + "/customer";
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        switch(ex.getStatusCode()){
            case NOT_FOUND:
                throw new NotFoundException(getErrorMessage(ex));

            case UNPROCESSABLE_ENTITY:
                throw new InvalidInputException(getErrorMessage(ex));

            case GONE:
                throw new NotManufacturedException(getErrorMessage(ex));

            default:
                Log.warn("Got an unexpected HTTP error: {}. will rethrow it.", ex.getStatusCode());
                Log.warn("Error body: {}", ex.getResponseBodyAsString());
                throw ex;
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {

        try{
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();

        }catch(IOException ioex){
            return ioex.getMessage();
        }
    }


    @Override
    public CarModel createCarModel(CarModel model) {
        try{
            String url = carModelServiceUrl;
            Log.debug("Will call createCarModel API on URL: {}", url);
            return restTemplate.postForObject(url, model, CarModel.class);

        }catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public BuildPrice createBuildPrice(BuildPrice model) {
        try{
            String url = buildPriceServiceUrl;
            Log.debug("Will call createRecommendation API on URL: {}", url);

            BuildPrice buildPrice = restTemplate.postForObject(url, model, BuildPrice.class);
            Log.debug("Created a recommendation with productId: {} and recommendationId: {}", buildPrice.getCarId(), buildPrice.getBuildPriceId());

            return buildPrice;

        }catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public Customer createCustomer(Customer model) {
        try{
            String url = customerServiceUrl;
            Log.debug("Will call createCustomers API on URL: {}", url);

            Customer customer = restTemplate.postForObject(url,model,Customer.class);
            Log.debug("Created a review for carId: {} and customerId: {}", customer.getCarId(), customer.getCustomerId());

            return customer;

        }catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

}
