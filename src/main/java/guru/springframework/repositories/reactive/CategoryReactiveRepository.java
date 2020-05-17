package guru.springframework.repositories.reactive;

import guru.springframework.models.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

    // expect only one result
    Mono<Category> findByDescription(String description);
}
