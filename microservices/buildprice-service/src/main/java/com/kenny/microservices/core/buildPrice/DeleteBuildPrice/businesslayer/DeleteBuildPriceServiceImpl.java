package com.kenny.microservices.core.buildPrice.DeleteBuildPrice.businesslayer;

import com.kenny.Utils.http.ServiceUtil;
import com.kenny.microservices.core.buildPrice.DeleteBuildPrice.datalayer.DeleteBuildPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteBuildPriceServiceImpl implements DeleteBuildPriceService {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteBuildPriceServiceImpl.class);

    private final DeleteBuildPriceRepository repository;

    private final DeleteBuildPriceMapper mapper;

    private final ServiceUtil serviceUtil;

    public DeleteBuildPriceServiceImpl(DeleteBuildPriceRepository repository, DeleteBuildPriceMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public void deleteBuildPrices(int carId) {
        LOG.debug("BuildPriceService deleteBuildPrices: tries to delete all buildPrices for the product with carId: {}", carId);
        repository.deleteAll(repository.findByCarId(carId));
    }
}


