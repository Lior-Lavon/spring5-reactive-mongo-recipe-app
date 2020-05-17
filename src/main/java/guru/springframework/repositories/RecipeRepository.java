package guru.springframework.repositories;

import guru.springframework.models.Recipe;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, String> {

    Optional<Recipe> findByDescription(String description);

}
