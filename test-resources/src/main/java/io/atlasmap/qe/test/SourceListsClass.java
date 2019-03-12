package io.atlasmap.qe.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SourceListsClass {
    private List<String> strings;
    private List<Integer> integers;
    private List<Double> doubles;
    private List<Float> floats;
    private List<StringObject> objects;
    private List<SourceMappingTestClass> sourceMappingTestclass;
    private Map<String,String> map;
    private Set<String> set;
    private String[] array;

    public SourceListsClass() {
        this.strings = new ArrayList<>();
        this.integers = new ArrayList<>();
        this.doubles = new ArrayList<>();
        this.floats = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.sourceMappingTestclass = new ArrayList<>();
        this.map = new HashMap<>();
        this.set = new HashSet<>();
        this.array = new String[10];

        for (int i = 1; i < 10; i++) {
            this.strings.add("String" + i);
            this.integers.add(i);
            this.doubles.add((double) i);
            this.floats.add((float) i);
            this.objects.add(new StringObject(i + ""));
            this.map.put("key"+i,"value"+i);
            this.array[i] = "Str"+i;
            this.set.add("setStr"+i);
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

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

}
