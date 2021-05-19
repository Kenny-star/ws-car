package com.kenny.microservices.core.buildPrice.CreateBuildPrice.businesslayer;

import com.kenny.api.core.buildPrice.BuildPrice;

import java.util.List;

public interface CreateBuildPriceService {
    public BuildPrice createBuildPrice(BuildPrice model);

}
