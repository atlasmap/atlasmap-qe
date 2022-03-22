package io.atlasmap.qe.mapper.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CharacterToBooleanConverter implements Converter<Character, Boolean> {

    @Override
    public Boolean convert(Character value) {
        return value != '\000';
    }
}
