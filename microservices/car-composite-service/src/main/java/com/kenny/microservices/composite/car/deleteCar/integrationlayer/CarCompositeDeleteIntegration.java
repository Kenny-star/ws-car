package com.kenny.microservices.composite.car.deleteCar.integrationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.exceptions.NotFoundException;
import com.kenny.Utils.exceptions.NotManufacturedException;
import com.kenny.Utils.http.HttpErrorInfo;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.api.core.buildPrice.buildPriceDeleteAPI;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.api.core.carModel.carModelDeleteAPI;
import com.kenny.api.core.customer.Customer;
import com.kenny.api.core.customer.customerCreateAPI;
import com.kenny.api.core.customer.customerDeleteAPI;
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
public class CarCompositeDeleteIntegration implements buildPriceDeleteAPI, carModelDeleteAPI, customerDeleteAPI {
    private static final Logger Log = LoggerFactory.getLogger(CarCompositeDeleteIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String buildPriceServiceUrl;
    private final String carModelServiceUrl;
    private final String customerServiceUrl;

    public CarCompositeDeleteIntegration(
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
    public void deleteCarModel(int carId) {
        try{
            String url = carModelServiceUrl + "/" + carId;
            Log.debug("Will call deleteCarModel API on url: {}", url);

            restTemplate.delete(url);

        }catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public void deleteBuildPrices(int carId) {
        try{
            String url = buildPriceServiceUrl + "?carId=" + carId;
            Log.debug("Will call deleteBuildPrices API on URL: {}", url);

            restTemplate.delete(url);

        }catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public void deleteCustomer(int carId) {
        try{
            String url = customerServiceUrl + "?carId=" + carId;
            Log.debug("Will call deleteCustomers API on URL: {}", url);

            restTemplate.delete(url);
        }catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

}
