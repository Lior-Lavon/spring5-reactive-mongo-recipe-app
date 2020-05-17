package guru.springframework.repositories;

import guru.springframework.bootstrap.BootstrapDataSQL;
import guru.springframework.models.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

// Integration tests done with the DB
// Load Spring Context
@RunWith(SpringRunner.class)
//@DataJpaTest
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    // Spring context will start up and we will get an instance of the UnitOfMeasureRepository injected
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {

        // load the content to the database before the test
        BootstrapDataSQL bootStrap = new BootstrapDataSQL(recipeRepository, categoryRepository, unitOfMeasureRepository);
        bootStrap.onApplicationEvent(null);
    }

    @Test
    public void findByDescription() {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", unitOfMeasureOptional.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", unitOfMeasureOptional.get().getDescription());
    }
}