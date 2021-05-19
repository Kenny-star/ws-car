package com.kenny.microservices.core.buildPrice;

import com.kenny.microservices.core.buildPrice.Dao.BuildPriceEntity;
import com.kenny.microservices.core.buildPrice.GetBuildPrice.datalayer.GetBuildPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
public class PersistenceTests {

    @Autowired
    private GetBuildPriceRepository repository;
    private BuildPriceEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();

        BuildPriceEntity entity = new BuildPriceEntity(1, 2, 3, 4.0, 5.0, 6.0, "a", "b", "c", "d", "e");
        savedEntity = repository.save(entity);

        assertThat(savedEntity, samePropertyValuesAs(entity));
    }


    @Test
    public void createBuildPrice() {

        BuildPriceEntity newEntity = new BuildPriceEntity(1, 3, 3, 4.0, 5.0, 6.0, "a", "b", "c", "d", "e");
        repository.save(newEntity);

        BuildPriceEntity foundEntity = repository.findById(newEntity.getId()).get();

        assertThat(foundEntity, samePropertyValuesAs(newEntity));

        assertEquals(2, repository.count());
    }

    @Test
    public void updateBuildPrice() {
        savedEntity.setAutomatic("a1");
        repository.save(savedEntity);

        BuildPriceEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("a1", foundEntity.getAutomatic());
    }

    @Test
    public void deleteBuildPrice() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void getBuildPriceByCarId() {
        List<BuildPriceEntity> entityList = repository.findByCarId(savedEntity.getCarId());

        assertThat(entityList, hasSize(1));

        assertThat(entityList.get(0), samePropertyValuesAs(savedEntity));

    }

}