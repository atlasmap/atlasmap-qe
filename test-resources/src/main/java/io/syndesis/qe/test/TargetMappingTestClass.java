package io.syndesis.qe.test;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
/**
 * Created by mmelko on 26/10/2017.
 **/
public class TargetMappingTestClass implements MappingTestClassConverter, Serializable {

    private String targetString;
    private String targetCombineString;
    private int targetInteger;
    private long targetLong;
    private float targetFloat;
    private double targetDouble;
    private Date targetDate;
    private String targetAnotherString;

    public String getTargetString() {
        return targetString;
    }

    public void setTargetString(String targetString) {
        this.targetString = targetString;
    }

    public String getTargetCombineString() {
        return targetCombineString;
    }

    public void setTargetCombineString(String targetCombineString) {
        this.targetCombineString = targetCombineString;
    }

    public int getTargetInteger() {
        return targetInteger;
    }

    public void setTargetInteger(int targetInteger) {
        this.targetInteger = targetInteger;
    }

    public long getTargetLong() {
        return targetLong;
    }

    public void setTargetLong(long targetLong) {
        this.targetLong = targetLong;
    }

    public float getTargetFloat() {
        return targetFloat;
    }

    public void setTargetFloat(float targetFloat) {
        this.targetFloat = targetFloat;
    }

    public double getTargetDouble() {
        return targetDouble;
    }

    public void setTargetDouble(double targetDouble) {
        this.targetDouble = targetDouble;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getTargetAnotherString() {
        return targetAnotherString;
    }

    public void setTargetAnotherString(String targetAnotherString) {
        this.targetAnotherString = targetAnotherString;
    }

    public TargetMappingTestClass() {
        this.targetString = "String";
        this.targetCombineString = "nothing";
        this.targetInteger = 1;
        this.targetLong = 2L;
        this.targetFloat = 3f;
        this.targetDouble = 4d;
        this.targetDate = new Date(0);
        this.targetAnotherString = "Another";
    }

    @Override
    public String toString() {
        return "TargetMappingTestClass{" +
                "targetString='" + targetString + '\'' +
                ", targetCombineString='" + targetCombineString + '\'' +
                ", targetInteger=" + targetInteger +
                ", targetLong=" + targetLong +
                ", targetFloat=" + targetFloat +
                ", targetDouble=" + targetDouble +
                ", targetDate=" + targetDate +
                ", targetAnotherString='" + targetAnotherString + '\'' +
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
        TargetMappingTestClass that = (TargetMappingTestClass) o;
        return targetInteger == that.targetInteger &&
                targetLong == that.targetLong &&
                Float.compare(that.targetFloat, targetFloat) == 0 &&
                Double.compare(that.targetDouble, targetDouble) == 0 &&
                Objects.equals(targetString, that.targetString) &&
                Objects.equals(targetCombineString, that.targetCombineString) &&
                Objects.equals(targetDate, that.targetDate) &&
                Objects.equals(targetAnotherString, that.targetAnotherString);
    }

    @Override
    public void setAndConvertValue(String field, Object value) throws ParseException {

        switch (field) {
            case "targetCombineString": {
                this.setTargetCombineString(value.toString());
                break;
            }
            case "targetString": {
                this.setTargetString(value.toString());
                break;
            }
            case "targetInteger": {
                this.setTargetInteger(NumberFormat.getInstance().parse(value.toString()).intValue());
                break;
            }
            case "targetLong": {
                this.setTargetLong((NumberFormat.getInstance().parse(value.toString())).longValue());
                break;
            }
            case "targetFloat": {
                this.setTargetFloat((NumberFormat.getInstance().parse(value.toString())).floatValue());
                break;
            }
            case "targetDouble": {
                this.setTargetDouble((NumberFormat.getInstance().parse(value.toString())).doubleValue());
                break;
            }
            case "targetDate": {
                if (value instanceof Date) {
                    this.setTargetDate((Date) value);
                } else if (value instanceof Number) {
                    this.setTargetDate(new Date(((Number) value).longValue()));
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    this.setTargetDate(format.parse(value.toString()));
                }
                break;
            }
            case "targetAnotherString": {
                this.setTargetAnotherString(value.toString());
            }
        }
    }

    @Override
    public Object getValue(String field) {
        switch (field) {
            case "targetCombineString":
                return getTargetCombineString();
            case "targetString":
                return getTargetString();
            case "targetInteger":
                return getTargetInteger();
            case "targetLong":
                return getTargetLong();
            case "targetFloat":
                return getTargetFloat();
            case "targetDouble":
                return getTargetDouble();
            case "targetDate":
                return getTargetDate();
            case "targetAnotherString":
                return getTargetAnotherString();
        }
        return null;
    }
}
