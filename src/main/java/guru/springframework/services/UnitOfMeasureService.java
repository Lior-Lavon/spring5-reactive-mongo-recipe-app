package guru.springframework.services;

import guru.springframework.command.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUOM();
}
