package io.syndesis.qe.test;

import java.text.ParseException;

public interface MappingTestClassConverter {
    public void setAndConvertValue(String field, Object value) throws ParseException;

    public Object getValue(String field);
}
