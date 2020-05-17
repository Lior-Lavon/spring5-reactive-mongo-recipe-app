package guru.springframework.services;

import guru.springframework.Converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.command.UnitOfMeasureCommand;
import guru.springframework.models.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUOM() {

        return unitOfMeasureReactiveRepository
                .findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
