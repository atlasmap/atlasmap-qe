package io.atlasmap.qe.test;

import io.atlasmap.qe.test.converter.CharacterToBooleanConverter;
import io.atlasmap.qe.test.converter.NumberToBooleanConverter;
import io.atlasmap.qe.test.converter.NumberToDateConverter;
import io.atlasmap.qe.test.converter.StringToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Configuration
@ComponentScan(basePackageClasses = ValidatorSpringConfig.class)
public class ValidatorSpringConfig {

    @Inject
    @Bean(name = "conversionService")
    public ConversionServiceFactoryBean getConversionService(StringToDateConverter stringToDateConverter,
                                                             NumberToDateConverter numberToDateConverter,
                                                             NumberToBooleanConverter numberToBooleanConverter,
                                                             CharacterToBooleanConverter characterToBooleanConverter) {

        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();

        Set<Converter> converters = new HashSet<>();

        converters.add(stringToDateConverter);
        converters.add(numberToDateConverter);
        converters.add(numberToBooleanConverter);
        converters.add(characterToBooleanConverter);

        bean.setConverters(converters);
        return bean;
    }
}
