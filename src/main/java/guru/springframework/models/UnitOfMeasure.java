package guru.springframework.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Document
public class UnitOfMeasure{

    @Id
    private String id;

    private String description; // Unit Of Measure

}
