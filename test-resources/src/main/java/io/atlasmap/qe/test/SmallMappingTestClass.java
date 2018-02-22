package io.atlasmap.qe.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mmelko on 27/10/2017.
 */
public class SmallMappingTestClass implements Serializable {

    private String objectField1;

    private String objectField2;

    private Long objectLong;

    private List<String> listOfStrings;

    private List<Integer> listOfIntegers;

    private List<Double> listOfDoubles;

    @Override
    public String toString() {
        return "SmallMappingTestClass{" +
                "objectField1='" + objectField1 + '\'' +
                ", objectField2='" + objectField2 + '\'' +
                ", objectLong=" + objectLong +
                ", listOfStrings=" + listOfStrings +
                ", listOfIntegers=" + listOfIntegers +
                ", listOfDoubles=" + listOfDoubles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SmallMappingTestClass that = (SmallMappingTestClass) o;
        return Objects.equals(objectField1, that.objectField1) &&
                Objects.equals(objectField2, that.objectField2) &&
                Objects.equals(objectLong, that.objectLong) &&
                Objects.equals(listOfStrings, that.listOfStrings) &&
                Objects.equals(listOfIntegers, that.listOfIntegers) &&
                Objects.equals(listOfDoubles, that.listOfDoubles);
    }

    @Override
    public int hashCode() {

        return Objects.hash(objectField1, objectField2, objectLong, listOfStrings, listOfIntegers, listOfDoubles);
    }

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

    public String getObjectField1() {
        return objectField1;
    }

    public void setObjectField1(String objectField1) {
        this.objectField1 = objectField1;
    }

    public String getObjectField2() {
        return objectField2;
    }

    public void setObjectField2(String objectField2) {
        this.objectField2 = objectField2;
    }

    public void setObjectLong(Long objectLong1) {
        this.objectLong = objectLong1;
    }

    public List<String> getListOfStrings() {

        return listOfStrings;
    }

    public void setListOfStrings(List<String> listOfStrings) {
        this.listOfStrings = listOfStrings;
    }

    public List<Integer> getListOfIntegers() {
        return listOfIntegers;
    }

    public void setListOfIntegers(List<Integer> listOfIntegers) {
        this.listOfIntegers = listOfIntegers;
    }

    public List<Double> getListOfDoubles() {
        return listOfDoubles;
    }

    public void setListOfDoubles(List<Double> listOfDoubles) {
        this.listOfDoubles = listOfDoubles;
    }


    public void doSomething() {
        System.out.println("Useless method");
    }
}

