package io.atlasmap.qe.test;

import java.util.ArrayList;
import java.util.List;

public class SourceListsClass {
    private List<String> strings;
    private List<Integer> integers;
    private List<Double> doubles;
    private List<Float> floats;
    private List<StringObject> objects;
    private List<SourceMappingTestClass> sourceMappingTestclass;

    public SourceListsClass() {
        this.strings = new ArrayList<>();
        this.integers = new ArrayList<>();
        this.doubles = new ArrayList<>();
        this.floats = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.sourceMappingTestclass = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            this.strings.add("String " + i);
            this.integers.add(i);
            this.doubles.add((double) i);
            this.floats.add((float) i);
            this.objects.add(new StringObject(i + ""));
        }

    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(List<Integer> integers) {
        this.integers = integers;
    }

    public List<Double> getDoubles() {
        return doubles;
    }

    public void setDoubles(List<Double> doubles) {
        this.doubles = doubles;
    }

    public List<Float> getFloats() {
        return floats;
    }

    public void setFloats(List<Float> floats) {
        this.floats = floats;
    }

    public List<StringObject> getObjects() {
        return objects;
    }

    public void setObjects(List<StringObject> objects) {
        this.objects = objects;
    }

    public List<SourceMappingTestClass> getSourceMappingTestclass() {
        return sourceMappingTestclass;
    }

    public void setSourceMappingTestclass(List<SourceMappingTestClass> sourceMappingTestclass) {
        this.sourceMappingTestclass = sourceMappingTestclass;
    }


}
