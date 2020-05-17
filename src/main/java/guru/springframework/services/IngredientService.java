package guru.springframework.services;

import guru.springframework.command.IngredientCommand;
import org.bson.types.ObjectId;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(String recipeId, String ingredientId);
}
