package io.syndesis.qe.test;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mmelko on 26/10/2017.
 */
public class SourceMappingTestClass implements Serializable, MappingTestClassConverter {

    private String sourceString;
    private String sourceCombineString;
    private int sourceInteger;
    private long sourceLong;
    private float sourceFloat;
    private double sourceDouble;
    private Date sourceDate;
    private String sourceAnotherString;

    public SourceMappingTestClass() {
        this.sourceString = "string";
        this.sourceCombineString = "combine";
        this.sourceInteger = 1;
        this.sourceLong = 2L;
        this.sourceFloat = 3f;
        this.sourceDouble = 4d;
        this.sourceDate = new Date(0);
        this.sourceAnotherString = "Another";
    }

    @Override
    public String toString() {
        return "SourceMappingTestClass{" +
                "sourceString='" + sourceString + '\'' +
                ", sourceCombineString='" + sourceCombineString + '\'' +
                ", sourceInteger=" + sourceInteger +
                ", sourceLong=" + sourceLong +
                ", sourceFloat=" + sourceFloat +
                ", sourceDouble=" + sourceDouble +
                ", sourceDate=" + sourceDate +
                ", sourceAnotherString='" + sourceAnotherString + '\'' +
                '}';
    }


    public String getSourceString() {
        return sourceString;
    }

    public void setSourceString(String sourceString) {
        this.sourceString = sourceString;
    }

    public String getSourceCombineString() {
        return sourceCombineString;
    }

    public void setSourceCombineString(String sourceCombineString) {
        this.sourceCombineString = sourceCombineString;
    }

    public int getSourceInteger() {
        return sourceInteger;
    }

    public void setSourceInteger(int sourceInteger) {
        this.sourceInteger = sourceInteger;
    }

    public long getSourceLong() {
        return sourceLong;
    }

    public void setSourceLong(long sourceLong) {
        this.sourceLong = sourceLong;
    }

    public float getSourceFloat() {
        return sourceFloat;
    }

    public void setSourceFloat(float sourceFloat) {
        this.sourceFloat = sourceFloat;
    }

    public double getSourceDouble() {
        return sourceDouble;
    }

    public void setSourceDouble(double sourceDouble) {
        this.sourceDouble = sourceDouble;
    }

    public Date getSourceDate() {
        return sourceDate;
    }

    public void setSourceDate(Date sourceDate) {
        this.sourceDate = sourceDate;
    }

    public String getSourceAnotherString() {
        return sourceAnotherString;
    }

    public void setSourceAnotherString(String sourceAnotherString) {
        this.sourceAnotherString = sourceAnotherString;
    }

    @Override
    public void setAndConvertValue(String field, Object value) throws ParseException {

        switch (field) {
            case "sourceCombineString": {
                this.setSourceCombineString(value.toString());
                break;
            }
            case "sourceString": {
                this.setSourceString(value.toString());
                break;
            }
            case "sourceInteger": {
                this.setSourceInteger(NumberFormat.getInstance().parse(value.toString()).intValue());
                break;
            }
            case "sourceLong": {
                this.setSourceLong((NumberFormat.getInstance().parse(value.toString())).longValue());
                break;
            }
            case "sourceFloat": {
                this.setSourceFloat((NumberFormat.getInstance().parse(value.toString())).floatValue());
                break;
            }
            case "sourceDouble": {
                this.setSourceDouble((NumberFormat.getInstance().parse(value.toString())).doubleValue());
                break;
            }
            case "sourceDate": {
                if (value instanceof Date) {
                    this.setSourceDate((Date) value);
                } else if (value instanceof Number) {
                    this.setSourceDate(new Date(((Number) value).longValue()));
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    this.setSourceDate(format.parse(value.toString()));
                }
                break;
            }
            case "sourceAnotherString": {
                this.setSourceAnotherString(value.toString());
            }
        }
    }

    @Override
    public Object getValue(String field) {
        switch (field) {
            case "sourceCombineString":
                return getSourceCombineString();
            case "sourceString":
                return getSourceString();
            case "sourceInteger":
                return getSourceInteger();
            case "sourceLong":
                return getSourceLong();
            case "sourceFloat":
                return getSourceFloat();
            case "sourceDouble":
                return getSourceDouble();
            case "sourceDate":
                return getSourceDate();
            case "sourceAnotherString":
                return getSourceAnotherString();
        }
        return null;
    }
}
