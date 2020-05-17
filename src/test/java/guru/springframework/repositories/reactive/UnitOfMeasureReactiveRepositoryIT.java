package guru.springframework.repositories.reactive;

import guru.springframework.models.UnitOfMeasure;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryIT {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {

        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void testUOMSave(){

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription("test");

        // block triggers the reactive flow
        unitOfMeasureReactiveRepository.save(uom).block();

        Long count = unitOfMeasureReactiveRepository.count().block();
        assertEquals(Long.valueOf(1), count);

    }
}