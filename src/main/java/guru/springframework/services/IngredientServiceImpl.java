package guru.springframework.services;

import guru.springframework.Converters.IngredientCommandToIngredient;
import guru.springframework.Converters.IngredientToIngredientCommand;
import guru.springframework.command.IngredientCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.models.Ingredient;
import guru.springframework.models.Recipe;
import guru.springframework.models.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

         return recipeReactiveRepository
                 .findById(recipeId)
                 .flatMapIterable(Recipe::getIngredients)
                 .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId)) // filter one ingredient
                 .single()
                 .map(ingredient -> {
                     // convert the mono to command
                     IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
                     ingredientCommand.setRecipeId(recipeId);
                     return ingredientCommand;
                 });

    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {

        Recipe recipe = recipeReactiveRepository.findById(command.getRecipeId()).block();
        if(recipe==null){
            // ToDo
            log.error("Recipe not found for id: " + command.getRecipeId());
            //throw new NotFoundException("Recipe not found");
            return Mono.just(new IngredientCommand());
        } else {

            // find if the ingredient exist
            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient1 -> ingredient1.getId().equals(command.getId()))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                // update

                Ingredient foundIngredient = ingredientOptional.get();
                foundIngredient.setDescription(command.getDescription());
                foundIngredient.setAmount(command.getAmount());

                // load UOM
                UnitOfMeasure unitOfMeasure = unitOfMeasureReactiveRepository.findById(foundIngredient.getUom().getId()).block();
                foundIngredient.setUom(unitOfMeasure);

            } else {
                // add new
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

            // return the ingredientCommand
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                    .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                    .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
                    .findFirst();
            if(!savedIngredientOptional.isPresent()){
                // ToDo
                log.error("Ingredient not found for id: " + command.getRecipeId());
                throw new NotFoundException("Recipe not found");
            } else {
                IngredientCommand savedIngredientCommand = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
                return Mono.just(savedIngredientCommand);
            }
        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String ingredientId) {

        log.debug("delete ingredient :" + ingredientId);

        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();
        if(recipe==null){
            // todo
            throw new NotFoundException("recipe not found by recipeId :" + recipeId);
        } else {

            // find the ingredient
            Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if(!optionalIngredient.isPresent()){
                // todo
                log.error("Ingredient not found id:" + ingredientId);
                throw new NotFoundException("Ingredient not found id:" + ingredientId);
            }

            Ingredient ingredient = optionalIngredient.get();
            // set the relationship to null -> this will caouse JPA to delete the ingredient

            recipe.getIngredients().remove(ingredient);

            // update the recipe
            recipeReactiveRepository.save(recipe).block();

        }
        return Mono.empty();
    }


}
