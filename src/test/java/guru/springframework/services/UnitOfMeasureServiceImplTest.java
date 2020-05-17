package guru.springframework.services;

import guru.springframework.Converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.command.UnitOfMeasureCommand;
import guru.springframework.models.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {

        // add the RecipeRepository
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    String ID1 = "1";
    String ID2 = "2";
    String ID3 = "3";

    @Test
    public void listAllUOM() {

        // given
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(ID1);
        uom1.setDescription("aaa");
        unitOfMeasureSet.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(ID3);
        uom2.setDescription("bbb");
        unitOfMeasureSet.add(uom2);

        // Flux.just(uom1, uom2) -> create a publisher with two uom
        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

        // when
        List<UnitOfMeasureCommand> unitOfMeasureCommandList = service.listAllUOM().collectList().block();

        // then
        assertNotNull(unitOfMeasureCommandList);
        assertEquals(2, unitOfMeasureCommandList.size());
    }
}