package guru.springframework.repositories;

import guru.springframework.models.Ingredient;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
