package guru.springframework.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {
    Category category;

    @Before
    public void setup(){
        category = new Category();
    }

    @Test
    public void testGetId() {

        String idValue = "4";

        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}