package guru.springframework.repositories.reactive;

import guru.springframework.models.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

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