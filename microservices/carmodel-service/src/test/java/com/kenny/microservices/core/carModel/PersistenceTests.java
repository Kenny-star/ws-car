package com.kenny.microservices.core.carModel;

import com.kenny.microservices.core.carModel.Dao.CarModelEntity;
import com.kenny.microservices.core.carModel.GetCarModel.datalayer.GetCarModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class PersistenceTests {
    @Autowired
    private GetCarModelRepository repository;
    private CarModelEntity savedEntity;
    @BeforeEach
    public void setupDb() {
        repository.deleteAll();
        CarModelEntity entity = new CarModelEntity(1, "n-1", "m-1", 1.0, "p-1", 1, "cn-1", "ct-1");
        savedEntity = repository.save(entity);
        assertThat(savedEntity, samePropertyValuesAs(entity));
    }
    @Test
    public void createCarModelEntity() {
        CarModelEntity newEntity = new CarModelEntity(2, "n-2", "m-2", 2.0, "p-2", 2, "cn-2", "ct-2");
        repository.save(newEntity);
        CarModelEntity foundEntity = repository.findById(newEntity.getId()).get();
        //assertEqualsProduct(newEntity, foundEntity);
        assertThat(foundEntity, samePropertyValuesAs(newEntity));
        assertEquals(2, repository.count());
    }
    @Test
    public void updateCarModelEntity() {
        savedEntity.setName("n2");
        repository.save(savedEntity);
        CarModelEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("n2", foundEntity.getName());
    }
    @Test
    public void deleteCarModelEntity() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }
    @Test
    public void getCarModelEntity() {
        Optional<CarModelEntity> entity = repository.findByCarId(savedEntity.getCarId());
        assertTrue(entity.isPresent());
        //assertEqualsProduct(savedEntity, entity.get());
        assertThat(entity.get(), samePropertyValuesAs(savedEntity));
    }
    private void assertEqualsProduct(CarModelEntity expectedEntity, CarModelEntity actualEntity) {
        assertEquals(expectedEntity.getId(),               actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),          actualEntity.getVersion());
        assertEquals(expectedEntity.getCarId(),        actualEntity.getCarId());
        assertEquals(expectedEntity.getName(),           actualEntity.getName());
        assertEquals(expectedEntity.getModel(),           actualEntity.getModel());
        assertEquals(expectedEntity.getCost(),           actualEntity.getCost());
        assertEquals(expectedEntity.getProvider(),           actualEntity.getProvider());
        assertEquals(expectedEntity.getRelease_year(),           actualEntity.getRelease_year());
        assertEquals(expectedEntity.getCountry(),           actualEntity.getCountry());
        assertEquals(expectedEntity.getContent(),           actualEntity.getContent());

    }
}
