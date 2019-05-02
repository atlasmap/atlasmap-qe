package io.atlasmap.qe.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

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
    private boolean targetBoolean;
    private short targetShort;
    private byte targetByte;
    private char targetChar;
    private BigInteger targetBigInteger;
    private BigDecimal targetBigDecimal;
    private SmallMappingTestClass targetSmallMappingTestClass;
    private DatesObject dateObjectVariable;
    private SimpleEnum targetEnum;

    public DatesObject getDateObjectVariable() {
        return dateObjectVariable;
    }

    public void setDateObjectVariable(DatesObject dateObjectVariable) {
        this.dateObjectVariable = dateObjectVariable;
    }

    public TargetMappingTestClass() {
        this.targetString = "targetString";
        this.targetCombineString = "targetCombineString";
        this.targetInteger = 5;
        this.targetLong = 4L;
        this.targetFloat = 3f;
        this.targetDouble = 2d;
        this.targetDate = new Date(0);
        this.targetAnotherString = "targetAnotherString";
        this.targetBoolean = false;
        this.targetShort = 1;
        this.targetByte = Byte.MIN_VALUE;
        this.targetChar = 'x';
        try {
            this.dateObjectVariable = new DatesObject("01-01-1989");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        TargetMappingTestClass that = (TargetMappingTestClass) o;
        return targetInteger == that.targetInteger &&
                targetLong == that.targetLong &&
                Float.compare(that.targetFloat, targetFloat) == 0 &&
                Double.compare(that.targetDouble, targetDouble) == 0 &&
                targetBoolean == that.targetBoolean &&
                targetShort == that.targetShort &&
                targetByte == that.targetByte &&
                targetChar == that.targetChar &&
                Objects.equals(targetString, that.targetString) &&
                Objects.equals(targetCombineString, that.targetCombineString) &&
                Objects.equals(sdf.format(targetDate), sdf.format(that.targetDate)) &&
                Objects.equals(targetAnotherString, that.targetAnotherString)
                &&
                Objects.equals(targetSmallMappingTestClass, that.targetSmallMappingTestClass);
    }

    @Override
    public String toString() {
        return "TargetMappingTestClass{" +
                "targetString='" + targetString + '\'' +
                "| targetCombineString='" + targetCombineString + '\'' +
                "| targetInteger=" + targetInteger +
                "| targetLong=" + targetLong +
                "| targetFloat=" + targetFloat +
                "| targetDouble=" + targetDouble +
                "| targetDate=" + targetDate +
                "| targetAnotherString='" + targetAnotherString + '\'' +
                "| targetBoolean=" + targetBoolean +
                "| targetShort=" + targetShort +
                "| targetByte=" + targetByte +
                "| targetChar=" + targetChar +
                "| targetMappingTestClass=" + targetSmallMappingTestClass +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(targetString, targetCombineString, targetInteger, targetLong, targetFloat, targetDouble, targetDate, targetAnotherString, targetBoolean, targetShort, targetByte, targetChar);
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
                if (value instanceof Character) {
                    this.setTargetInteger(Integer.valueOf((Character) value));
                } else {
                    this.setTargetInteger(NumberFormat.getInstance().parse(value.toString()).intValue());
                }
                break;
            }

            case "targetBigDecimal": {
                if (value instanceof Character) {
                    this.setTargetBigDecimal(BigDecimal.valueOf((Character) value));
                } else {
                    this.setTargetBigDecimal(new BigDecimal(value.toString()));
                }
                break;
            }
            case "targetLong": {
                if (value instanceof Character) {
                    this.setTargetLong(Long.valueOf((Character) value));
                } else {
                    this.setTargetLong((NumberFormat.getInstance().parse(value.toString())).longValue());
                }
                break;
            }
            case "targetFloat": {
                if (value instanceof Character) {
                    this.setTargetFloat(Float.valueOf((Character) value));
                } else {
                    this.setTargetFloat((NumberFormat.getInstance().parse(value.toString())).floatValue());
                }
                break;
            }
            case "targetDouble": {
                if (value instanceof Character) {
                    this.setTargetDouble(Double.valueOf((Character) value));
                } else {
                    this.setTargetDouble((NumberFormat.getInstance().parse(value.toString())).doubleValue());
                }
                break;
            }
            case "targetShort": {
                if (value instanceof Character) {
                    this.setTargetShort((short) ((Character) value).charValue());
                } else {
                    this.setTargetShort((NumberFormat.getInstance().parse(value.toString())).shortValue());
                }
                break;
            }
            case "targetByte": {
                try {
                    this.setTargetByte((NumberFormat.getInstance().parse(value.toString()).byteValue()));
                } catch (Exception e) {
                    this.setTargetChar(value.toString().toCharArray()[0]);
                }

                break;
            }

            case "targetChar": {
                if (value instanceof Character) {
                    setTargetChar((Character) value);
                } else {
                    try {
                        this.setTargetChar(Character.valueOf((char) NumberFormat.getInstance().parse(value.toString()).intValue()));
                    } catch (Exception e) {
                        this.setTargetChar(value.toString().toCharArray()[0]);
                    }
                }

                break;
            }

            case "targetBoolean": {
                if (value instanceof Number) {
                    final int temp = ((Number) value).intValue();
                    if (temp == 0) {
                        this.setTargetBoolean(false);
                    } else {
                        this.setTargetBoolean(true);
                    }
                } else if (value instanceof Character) {
                    this.setTargetDouble(Double.valueOf((Character) value));
                } else {
                    this.setTargetBoolean(Boolean.parseBoolean(value.toString()));
                }
                break;
            }
            case "targetDate": {
                if (value instanceof Date) {
                    this.setTargetDate((Date) value);
                } else if (value instanceof Number) {
                    this.setTargetDate(new Date(((Number) value).longValue()));
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh");
                    format.setTimeZone(TimeZone.getTimeZone("UTC"));
                    this.setTargetDate(format.parse(value.toString()));
                }
                break;
            }
            case "targetAnotherString": {
                this.setTargetAnotherString(value.toString());
                break;
            }
            case "targetEnum": {
                this.setTargetEnum(SimpleEnum.valueOf(value.toString()));
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
            case "targetBigInteger":
                return getTargetBigInteger();
            case "tagetBigDecimal":
                return getTargetBigDecimal();
            case "targetLong":
                return getTargetLong();
            case "targetFloat":
                return getTargetFloat();
            case "targetDouble":
                return getTargetDouble();
            case "targetDate":
                return getTargetDate();
            case "targetShort":
                return getTargetShort();
            case "targetByte":
                return getTargetByte();
            case "targetChar":
                return getTargetChar();
            case "targetBoolean":
                return isTargetBoolean();
            case "targetAnotherString":
                return getTargetAnotherString();
            case "targetEnum":
                return getTargetEnum();
        }
        return null;
    }

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

    public BigInteger getTargetBigInteger() {
        return targetBigInteger;
    }

    public void setTargetBigInteger(BigInteger targetBigInteger) {
        this.targetBigInteger = targetBigInteger;
    }

    public BigDecimal getTargetBigDecimal() {
        return targetBigDecimal;
    }

    public void setTargetBigDecimal(BigDecimal targetBigDecimal) {
        this.targetBigDecimal = targetBigDecimal;
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

    public boolean isTargetBoolean() {
        return targetBoolean;
    }

    public void setTargetBoolean(boolean targetBoolean) {
        this.targetBoolean = targetBoolean;
    }

    public short getTargetShort() {
        return targetShort;
    }

    public void setTargetShort(short targetShort) {
        this.targetShort = targetShort;
    }

    public byte getTargetByte() {
        return targetByte;
    }

    public void setTargetByte(byte targetByte) {
        this.targetByte = targetByte;
    }

    public char getTargetChar() {
        return targetChar;
    }

    public void setTargetChar(char targetChar) {
        this.targetChar = targetChar;
    }

    public SmallMappingTestClass getTargetSmallMappingTestClass() {
        return targetSmallMappingTestClass;
    }

    public void setTargetSmallMappingTestClass(SmallMappingTestClass targetSmallMappingTestClass) {
        this.targetSmallMappingTestClass = targetSmallMappingTestClass;
    }

    public SimpleEnum getTargetEnum() {
        return targetEnum;
    }

    public void setTargetEnum(SimpleEnum targetEnum) {
        this.targetEnum = targetEnum;
    }
}
