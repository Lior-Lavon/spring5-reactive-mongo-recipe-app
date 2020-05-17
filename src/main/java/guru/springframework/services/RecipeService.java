package guru.springframework.services;

import guru.springframework.command.RecipeCommand;
import guru.springframework.models.Recipe;
import org.bson.types.ObjectId;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(String id);

    void deleteById(String id);

}
