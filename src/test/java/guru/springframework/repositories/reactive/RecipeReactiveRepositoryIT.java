package guru.springframework.repositories.reactive;

import guru.springframework.models.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testRecipeSave(){
        Recipe recipe = new Recipe();
        recipe.setDescription("test");

        recipeReactiveRepository.save(recipe).then().block();

        Long count = recipeReactiveRepository.count().block();
        assertEquals(Long.valueOf(1), count);

    }
}