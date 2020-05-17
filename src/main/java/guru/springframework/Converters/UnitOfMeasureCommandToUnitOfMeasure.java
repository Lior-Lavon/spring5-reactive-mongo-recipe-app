package guru.springframework.Converters;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import guru.springframework.command.UnitOfMeasureCommand;
import guru.springframework.models.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if(source == null){
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        if(source.getId()!=null && !source.getId().isBlank())
            uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
    }

}
