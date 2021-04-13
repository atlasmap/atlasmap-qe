package io.atlasmap.qe.test.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class NumberToBooleanConverter implements Converter<Number, Boolean> {

    @Override
    public Boolean convert(Number value) {
        return value.doubleValue() != 0;
    }
}
