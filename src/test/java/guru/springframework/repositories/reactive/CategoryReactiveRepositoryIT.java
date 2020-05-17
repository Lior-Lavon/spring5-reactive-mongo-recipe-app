package guru.springframework.repositories.reactive;

import guru.springframework.models.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {

        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testCategorySave(){
        Category category = new Category();
        category.setDescription("test");

        categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();
        assertEquals(Long.valueOf(1), count);
    }

    @Test
    public void findByDescription() {
        Category category = new Category();
        category.setDescription("test");

        categoryReactiveRepository.save(category).then().block();

        Category categoryMono = categoryReactiveRepository.findByDescription("test").block();
        assertNotNull(categoryMono.getId());
    }
}