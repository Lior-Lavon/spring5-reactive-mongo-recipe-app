package guru.springframework.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
//@EqualsAndHashCode(exclude = {"recipe"})
@EqualsAndHashCode(callSuper = false)
public class Notes{

    // setting the id value for every new note
    private String id = UUID.randomUUID().toString();
    private Recipe recipe; // 1-1

    private String recipeNotes;

}
