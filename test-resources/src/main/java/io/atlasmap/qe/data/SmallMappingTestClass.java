package io.atlasmap.qe.data;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mmelko on 27/10/2017.
 */
@Data
public class SmallMappingTestClass implements Serializable {

    private String objectField1;
    private String objectField2;
    private Long objectLong;
    private List<String> listOfStrings;
    private List<Integer> listOfIntegers;
    private List<Double> listOfDoubles;

    public SmallMappingTestClass() {
        initObjectVariable(1);
    }

    public SmallMappingTestClass(int offset) {
        initObjectVariable(offset);
    }

    public void initObjectVariable(int offset) {

        this.objectField1 = offset + "_field1";
        this.objectField2 = offset + "_field2";
        this.objectLong = 3L * offset;
        this.listOfStrings = new ArrayList<>();

        listOfIntegers = new ArrayList<>();
        listOfDoubles = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            this.listOfStrings.add(offset + "_" + i);
            this.listOfIntegers.add(i * offset);
            this.listOfDoubles.add((double) i * offset);
        }
    }
}

