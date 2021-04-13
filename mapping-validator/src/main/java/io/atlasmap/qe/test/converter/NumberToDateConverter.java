package io.atlasmap.qe.test.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class NumberToDateConverter implements Converter<Number, Date> {

    @Override
    public Date convert(Number value) {
        return new Date(value.longValue());
    }
}
