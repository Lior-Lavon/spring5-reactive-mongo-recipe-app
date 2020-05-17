package guru.springframework.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
//@EqualsAndHashCode(callSuper = false)
public class Ingredient{

    // setting the id value for every new ingredient
    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;
    private String recipeId;

    @DBRef
    private UnitOfMeasure uom;

    public Ingredient() {

    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

}
