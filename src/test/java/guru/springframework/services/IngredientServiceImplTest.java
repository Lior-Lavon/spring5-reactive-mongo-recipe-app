package guru.springframework.services;

import guru.springframework.Converters.IngredientCommandToIngredient;
import guru.springframework.Converters.IngredientToIngredientCommand;
import guru.springframework.Converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.Converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.command.IngredientCommand;
import guru.springframework.models.Ingredient;
import guru.springframework.models.Recipe;
import guru.springframework.models.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    String ID1 = "1";
    String ID2 = "2";
    String ID3 = "3";

    @Before
    public void setUp() throws Exception {
        // add the RecipeRepository
        MockitoAnnotations.initMocks(this);

        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        ingredientService = new IngredientServiceImpl(recipeReactiveRepository, unitOfMeasureReactiveRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {

        // given
        Recipe recipe = new Recipe();
        recipe.setId(ID1);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ID1);
        ingredient1.setDescription("aaaa");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ID2);
        ingredient2.setDescription("bbbb");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(ID3);
        ingredient3.setDescription("cccc");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        // when
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(ID1, ID2).block();

        // then
        assertNotNull(ingredientCommand);
        assertEquals("2" , ingredientCommand.getId());
        //assertEquals("1" , ingredientCommand.getRecipeId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    public void testSaveIngredientCommand(){

        // Recipe
        Recipe recipe1 = new Recipe();
        recipe1.setId(ID1);

        // IngredientCommand
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID3);
        ingredient.setRecipeId(ID1);
        ingredient.setDescription("description");
        ingredient.setAmount(BigDecimal.valueOf(5L));

        // UnitOfMeasure
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID1);
        ingredient.setUom(unitOfMeasure);

        Recipe recipe2 = new Recipe();
        recipe2.setId(ID1);
        recipe2.getIngredients().add(ingredient);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe1));
        when(unitOfMeasureReactiveRepository.findById(anyString())).thenReturn(Mono.just(unitOfMeasure));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe2));

        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientToIngredientCommand.convert(ingredient)).block();

        assertNotNull(savedIngredientCommand);
        assertEquals(ID3, savedIngredientCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any());

    }

    @Test
    public void testDeleteById(){

        // given
        Recipe recipe1= new Recipe();
        recipe1.setId(ID1);

        Ingredient ing1 = new Ingredient();
        ing1.setId(ID2);
        ing1.setDescription("aaa");

        Ingredient ing2 = new Ingredient();
        ing2.setId(ID3);
        ing2.setDescription("bbb");

        Set<Ingredient> ingredientSet1 = new HashSet<>();
        ingredientSet1.add(ing1);
        ingredientSet1.add(ing2);

        recipe1.setIngredients(ingredientSet1);

        Recipe recipe2= new Recipe();
        recipe2.setId(ID1);

        Set<Ingredient> ingredientSet2 = new HashSet<>();
        ingredientSet2.add(ing1);

        recipe2.setIngredients(ingredientSet2);


        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe1));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe2));

        ingredientService.deleteById(ID1, ID2);

        Recipe savedRecipe = recipeReactiveRepository.findById(ID1).block();

        assertNotNull(savedRecipe);
        assertEquals(1, savedRecipe.getIngredients().size());
        verify(recipeReactiveRepository, times(2)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any());
    }
}