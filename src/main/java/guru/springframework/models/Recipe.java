package guru.springframework.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document
public class Recipe{

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    private String directions;
    private Difficulty difficulty;
    private Set<Ingredient> ingredients = new HashSet<>(); // Unique set on Ingredient !!
    private Byte[] image;
    private Notes notes; // 1-1

    private Set<Category> categories = new HashSet<>();

    public void addIngredient(Ingredient ingredient){
        //ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
    }

    public void setNotes(Notes notes) {
        //notes.setRecipe(this);
        this.notes = notes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
