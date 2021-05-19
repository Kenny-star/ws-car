package com.kenny.microservices.core.buildPrice.GetBuildPrice.businesslayer;

import com.kenny.api.core.buildPrice.BuildPrice;

import java.util.List;

public interface GetBuildPriceService {
    public List<BuildPrice> getByCarId(int carId);

}
