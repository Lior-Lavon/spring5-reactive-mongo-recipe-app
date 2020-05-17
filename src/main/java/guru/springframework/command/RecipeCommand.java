package guru.springframework.command;

import guru.springframework.models.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand extends BaseEntity{

    @NotBlank
    @Size(min = 3, max = 225) // number of char
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(1000)
    private Integer servings;

    //@NotBlank
    private String source;

    @URL
    // @NotBlank // optional
    private String url;

    @NotBlank
    private String directions;

    private Difficulty difficulty;
    private List<CategoryCommand> categories = new ArrayList<>();
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private Byte[] image;
    private NotesCommand notes;
}
