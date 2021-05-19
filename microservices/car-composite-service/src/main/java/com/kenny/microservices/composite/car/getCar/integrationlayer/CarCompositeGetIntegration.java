package com.kenny.microservices.composite.car.getCar.integrationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.exceptions.NotFoundException;
import com.kenny.Utils.exceptions.NotManufacturedException;
import com.kenny.Utils.http.HttpErrorInfo;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.api.core.buildPrice.buildPriceGetAPI;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.api.core.carModel.carModelGetAPI;
import com.kenny.api.core.customer.Customer;
import com.kenny.api.core.customer.customerGetAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarCompositeGetIntegration implements buildPriceGetAPI, carModelGetAPI, customerGetAPI {
    private static final Logger Log = LoggerFactory.getLogger(CarCompositeGetIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String buildPriceServiceUrl;
    private final String carModelServiceUrl;
    private final String customerServiceUrl;

    public CarCompositeGetIntegration(
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

    @Override
    public CarModel getCarModel(int carId) {
        try{
            String url = carModelServiceUrl + "/" + carId;
            Log.debug("Will call getCarModel API on URL: {}", url);

            CarModel carModel = restTemplate.getForObject(url, CarModel.class);

            Log.debug("Found a car with id: {}", carModel.getCarId());

            return carModel;
        }catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
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
    public List<BuildPrice> getBuildPrice(int carId) {
        try {
            String url = buildPriceServiceUrl + "?carId=" + carId;
            Log.debug("Will call getBuildPrice API on URL: {}", url);

            List<BuildPrice> buildPrices = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<BuildPrice>>() {
                    }).getBody();

            Log.debug("Found {} buildPrice for a car with id: {}", buildPrices.size(), carId);
            return buildPrices;

        } catch (Exception ex) {
            Log.warn("Got an exception while requesting buildPrice, return zero buildPrice: {}", ex.getMessage());
            return new ArrayList<>();
        }

    }
    @Override
    public List<Customer> getCustomer(int carId) {
        try {
            String url = customerServiceUrl + "?carId=" +  carId;

            Log.debug("Will call getCustomers API on URL: {}", url);
            List<Customer> customers = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Customer>>() {
                    }).getBody();

            Log.debug("Found {} customers for a car with id: {}", customers.size(), carId);
            return customers;

        }catch(Exception ex){
            Log.warn("Got an exception while requesting customers, return zero customers: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

}
