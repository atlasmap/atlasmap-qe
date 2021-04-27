package io.atlasmap.qe.data.source;


import io.atlasmap.qe.data.StringObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Data
public class SourceListsClass {

    private List<String> strings;
    private List<Integer> integers;
    private List<Double> doubles;
    private List<Float> floats;
    private List<StringObject> objects;
    private Map<String,String> map;
    private Set<String> set;
    private String[] array;

    public SourceListsClass() {
        this.strings = new ArrayList<>();
        this.integers = new ArrayList<>();
        this.doubles = new ArrayList<>();
        this.floats = new ArrayList<>();
        this.objects = new ArrayList<>();
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
}
