package guru.springframework.services;

import guru.springframework.command.IngredientCommand;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);

    Mono<Void> deleteById(String recipeId, String ingredientId);
}
