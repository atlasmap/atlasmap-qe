package io.atlasmap.qe.mapper.converter;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@Component
public class StringToDateConverter implements Converter<String, Date> {

    @SneakyThrows
    @Override
    public Date convert(String value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.parse(value);
    }
}
