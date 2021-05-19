package com.kenny.microservices.core.buildPrice.GetBuildPrice.businesslayer;

import com.kenny.Utils.http.ServiceUtil;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.microservices.core.buildPrice.Dao.BuildPriceEntity;
import com.kenny.microservices.core.buildPrice.GetBuildPrice.datalayer.GetBuildPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBuildPriceServiceImpl implements GetBuildPriceService {
    private static final Logger LOG = LoggerFactory.getLogger(GetBuildPriceServiceImpl.class);

    private final GetBuildPriceRepository repository;

    private final GetBuildPriceMapper mapper;

    private final ServiceUtil serviceUtil;

    public GetBuildPriceServiceImpl(GetBuildPriceRepository repository, GetBuildPriceMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<BuildPrice> getByCarId(int carId) {

        List<BuildPriceEntity> entityList = repository.findByCarId(carId);
        List<BuildPrice> list = mapper.entityListToModelList(entityList);
        list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));

        LOG.debug("BuildPrices getByCarId: response size: {}", list.size());
        return list;
    }

}


