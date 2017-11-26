package io.syndesis.qe.test;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mmelko on 26/10/2017.
 */
public class MappingTestClass implements Serializable {

    private String mappingString;
    private String combineString;
    private int intNumber;
    private long longNumber;
    private float floatNumber;
    private double doubleNumber;
    private Date currentDate;
    private String tempString;

    public MappingTestClass() {
        this.mappingString = "String";
        this.combineString = "nothing";
        this.intNumber = 5;
        this.longNumber = 5L;
        this.floatNumber = 5f;
        this.doubleNumber = 5d;
        this.currentDate = new Date();
        this.tempString = " this is only temp";

    }

    public String getMappingString() {
        return mappingString;
    }

    public void setMappingString(String mappingString) {
        this.mappingString = mappingString;
    }

    public String getCombineString() {
        return combineString;
    }

    public void setCombineString(String combineString) {
        this.combineString = combineString;
    }

    public int getIntNumber() {
        return intNumber;
    }

    public void setIntNumber(int intNumber) {
        this.intNumber = intNumber;
    }

    public long getLongNumber() {
        return longNumber;
    }

    public void setLongNumber(long longNumber) {
        this.longNumber = longNumber;
    }

    public float getFloatNumber() {
        return floatNumber;
    }

    public void setFloatNumber(float floatNumber) {
        this.floatNumber = floatNumber;
    }

    public double getDoubleNumber() {
        return doubleNumber;
    }

    public void setDoubleNumber(double doubleNumber) {
        this.doubleNumber = doubleNumber;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getTempString() {
        return tempString;
    }

    public void setTempString(String tempString) {
        this.tempString = tempString;
    }
    //TODO

}
