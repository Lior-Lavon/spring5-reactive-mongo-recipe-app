package guru.springframework.services;

import guru.springframework.Converters.RecipeCommandToRecipe;
import guru.springframework.Converters.RecipeToRecipeCommand;
import guru.springframework.command.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.models.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@Slf4j
public class RecipeServiceImplTest {

    // Inject the main class
    RecipeServiceImpl recipeService;

    // Inject the repository
    @Mock
    RecipeReactiveRepository recipeReactiveRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    String ID1 = "1";
    String ID2 = "2";
    String ID3 = "3";

    @Before
    public void setUp() throws Exception {
        // add the RecipeRepository
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() {
        // Create test set
        Recipe recipe = new Recipe();
        recipe.setId(ID1);

        //Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = recipeService.findById(ID1).block();

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() {

        // Create test set
        Recipe recipe = new Recipe();

        // tell mockito -> when 'recipeRepository.findAll()' is called -> Then -> return 'recipeData'
        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(recipe));

        // call recipeService.getRecipes that will call behind to recipeRepository.findAll()
        List<Recipe> recipes = recipeService.getRecipes().collectList().block();

        assertEquals(1, recipes.size());

        // make sure that the recipeRepository.findAll was called once and only once
        verify(recipeReactiveRepository, times(1)).findAll();
    }

    @Test
    public void deleteRecipe(){

        // given
        recipeService.deleteById(ID1);

        // when
        // no need as there is no return value

        // then
        verify(recipeReactiveRepository, times(1)).deleteById(anyString());
    }


}