package guru.springframework.services;

import guru.springframework.Converters.IngredientCommandToIngredient;
import guru.springframework.Converters.IngredientToIngredientCommand;
import guru.springframework.Converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.Converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.command.IngredientCommand;
import guru.springframework.command.UnitOfMeasureCommand;
import guru.springframework.models.Ingredient;
import guru.springframework.models.Recipe;
import guru.springframework.models.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

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
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
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
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(ID1, ID2);

        // then
        assertNotNull(ingredientCommand);
        assertEquals("2" , ingredientCommand.getId());
        //assertEquals("1" , ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(any());
    }

    @Test
    public void testSaveIngredientCommand(){

        // Recipe
        Recipe recipe = new Recipe();
        recipe.setId(ID2);

        // IngredientCommand
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID3);
        ingredient.setDescription("description");
        ingredient.setAmount(BigDecimal.valueOf(5L));

        // UnitOfMeasure
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID1);
        ingredient.setUom(unitOfMeasure);

        recipe.getIngredients().add(ingredient);

//        Recipe savedRecipe = new Recipe();
//        savedRecipe.setId(2L);
//        savedRecipe.addIngredient(new Ingredient());
//        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(unitOfMeasureRepository.findById(any())).thenReturn(Optional.of(unitOfMeasure));
        when(recipeRepository.save(any())).thenReturn(recipe);

        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientToIngredientCommand.convert(ingredient));

        assertNotNull(savedIngredientCommand);
        assertEquals(ID3, savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(any());
        verify(recipeRepository, times(1)).save(any());

    }

    @Test
    public void testDeleteById(){

        // given
        Recipe recipe= new Recipe();
        recipe.setId(ID1);

        Ingredient ing1 = new Ingredient();
        ing1.setId(ID2);
        ing1.setDescription("aaa");

        Ingredient ing2 = new Ingredient();
        ing2.setId(ID3);
        ing2.setDescription("bbb");

        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(ing1);
        ingredientSet.add(ing2);

        recipe.setIngredients(ingredientSet);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(any())).thenReturn(optionalRecipe);

        ingredientService.deleteById(ID1, ID2);

        Optional<Recipe> savedOptionalRecipe = recipeRepository.findById(ID1);
        Recipe savedRecipe = savedOptionalRecipe.get();

        assertNotNull(savedRecipe);
        assertEquals(1, savedRecipe.getIngredients().size());
        verify(recipeRepository, times(2)).findById(any());
        verify(recipeRepository, times(1)).save(any());
    }
}