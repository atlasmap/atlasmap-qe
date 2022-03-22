package io.atlasmap.qe.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.atlasmap.qe.data.source.SourceMappingTestClass;
import io.atlasmap.qe.data.target.TargetMappingTestClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;


/**
 * Created by mmelko on 15/11/2017.
 */
@Slf4j
@Component
public class MappingValidator {
    private static final Logger LOG = LogManager.getLogger(MappingValidator.class);

    private String mappingLocation;
    private SourceMappingTestClass source;
    private TargetMappingTestClass target;
    private Map<String, Object> expectedMap;
    private Map<String, Object> sourceMap;

    private final AtlasMapper atlasMapper;

    private final ConversionService conversionService;

    @Inject
    public MappingValidator(AtlasMapper atlasMapper, ConversionService conversionService) {
        this.atlasMapper = atlasMapper;
        this.conversionService = conversionService;
    }

    public void initializeValues(Map<String, Object> resourceMap) {
        source = new SourceMappingTestClass();
        target = new TargetMappingTestClass();
        expectedMap = new HashMap<>();
        sourceMap = new HashMap<>();
        initValidator(resourceMap);
    }

    public TargetMappingTestClass processMapping(SourceMappingTestClass input) {
        sourceMap = new HashMap<>();
        sourceMap.put(input.getClass().getName(), input);
        Map<String, Object> targetMap = processMappingInputMap(sourceMap);
        return (TargetMappingTestClass) targetMap.get(TargetMappingTestClass.class.getName());
    }

    public TargetMappingTestClass processMapping() {
        return processMapping(this.source);
    }

    public Object processSingleObjectMapping(Object input, String expected) {
        return processSingleObjectMapping(input, input.getClass().getName(), expected);
    }

    public Object processSingleObjectMapping(Object input, String inputName, String expected) {
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put(inputName, input);
        Map<String, Object> processed = processMappingInputMap(sourceMap);

        return getExpectedValue(processed, expected);
    }

    public Object processMapping(String expected) {
        //   sourceMap.put(source.getClass().getName(), source);
        Map<String, Object> processed = processMappingInputMap(sourceMap);
        return getExpectedValue(processed, expected);
    }

    public Map<String, Object> processMappingInputMap(Map<String, Object> input) {
        return atlasMapper.processMapping(input, mappingLocation);
    }

    public boolean verifyMapping(SourceMappingTestClass source, TargetMappingTestClass target, boolean equals) {
        this.sourceMap.put(source.getClass().getName(), source);
        TargetMappingTestClass processedTarget = (TargetMappingTestClass) processMapping(TargetMappingTestClass.class.getName());
        LOG.info("cleaning");

        if (processedTarget == null) {
            LOG.error("TARGET IS NULL!!!!!!!!!!!!");
        }
        this.target = new TargetMappingTestClass();
        this.source = new SourceMappingTestClass();
        if (equals) {
            assertThat(processedTarget).isEqualTo(target);
        }
        return target.equals(processedTarget);
    }

    public boolean verifyMultiObjectMapping() {
        this.sourceMap.put(this.source.getClass().getName(), this.source);
        this.expectedMap.put(this.target.getClass().getName(), this.target); // FIXME: why is this here?
        final boolean res = verifyMultiObjectMappingWithMap(this.sourceMap);
        return res;
    }

    public boolean verifyMultiObjectMappingWithMap(Map<String, Object> input) {
        final Boolean res = verifyMappingInputExpected(input, this.expectedMap);
        clear();
        return res;
    }

    public boolean verifyMappingInputExpected(Map<String, Object> input, Map<String, Object> expected) {
        Map<String, Object> processed = processMappingInputMap(input);
        assertThat(processed.size()).isGreaterThan(0);
        if (processed.isEmpty()) {
            return false;
        }

        expected.forEach((k, v) -> {
            Object actual = getExpectedValue(processed, k);
            assertThat(actual).isEqualTo(v);
        });
        return true;
    }

    private Object getExpectedValue(Map<String, Object> processed, String expected) {
        Object expectedValue;
        if (processed.containsKey(expected)) {
            expectedValue = processed.get(expected);
        } else {
            expectedValue = processed.entrySet().stream().filter(t -> t.getKey().matches(expected + "-.*")).findFirst().get().getValue();
        }
        return expectedValue;
    }

    public boolean verifyMapping() {
        return this.verifyMapping(this.source, this.target, true);
    }

    public boolean verifyMapping(boolean check) {
        return this.verifyMapping(this.source, this.target, check);
    }

    public boolean verifyMapping(TargetMappingTestClass target) {
        return this.verifyMapping(this.source, this.target, true);
    }

    public void map(String source, String target) throws ParseException {
        setTargetValue(target, getSourceValue(source));
    }

    public void setTargetValue(String field, Object value) {
        setValueOfBeanProperty(target, field, value);
    }

    public void setSourceValue(String field, Object value) {
        setValueOfBeanProperty(source, field, value);
    }

    public Object getSourceValue(String field) {
        return getValueOfBeanProperty(this.source, field);
    }

    public Object getTargetValue(String field) {
        return getValueOfBeanProperty(this.target, field);
    }

    public void setValueOfBeanProperty(Object bean, String fieldName, Object value) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        beanWrapper.setPropertyValue(fieldName, conversionService.convert(value,
            Objects.requireNonNull(beanWrapper.getPropertyType(fieldName))));
    }

    public Object getValueOfBeanProperty(Object bean, String fieldName) {
        return PropertyAccessorFactory.forBeanPropertyAccess(bean).getPropertyValue(fieldName);
    }

    public TargetMappingTestClass getTarget() {
        return target;
    }

    public void setTarget(TargetMappingTestClass target) {
        this.target = target;
    }

    public void setMappingLocation(String mappingLocation) {
        this.mappingLocation = mappingLocation;
    }

    public SourceMappingTestClass getSource() {
        return source;
    }

    public Object getSource(String name) {
        return this.sourceMap.get(name);
    }

    public void setSource(SourceMappingTestClass source) {
        this.source = source;
    }

    public Map<String, Object> getExpectedMap() {
        return expectedMap;
    }

    public void setExpectedMap(Map<String, Object> expectedMap) {
        this.expectedMap = expectedMap;
    }

    public void addSource(String name, Object s) {
        this.sourceMap.put(name, s);
    }

    public void clear() {
        this.sourceMap.clear();
        this.expectedMap.clear();
        this.source = new SourceMappingTestClass();
        this.target = new TargetMappingTestClass();
    }

    public void initValidator(Map<String, Object> resourceMap) {
        sourceMap = resourceMap;

        sourceMap.put(source.getClass().getName(), source);
    }

//    public static void main(String[] args) {
//        MappingValidator mv = new MappingValidator();
//        if (args.length > 1) {
//            mv.setMappingLocation(args[0]);
//            Object result = null;
//            try {
//                result = mv.processMapping(args[1]);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println(result.toString());
//        }
//    }
}