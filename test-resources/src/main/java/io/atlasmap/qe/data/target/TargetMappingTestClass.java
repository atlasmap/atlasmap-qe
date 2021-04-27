package io.atlasmap.qe.data.target;

import io.atlasmap.qe.data.DatesObject;
import io.atlasmap.qe.data.SimpleEnum;
import io.atlasmap.qe.data.SmallMappingTestClass;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


/**
 * Simple class that is used to verify mappings to Java objects.
 *
 * @author Ondřej Kuhejda
 **/
@Data
public class TargetMappingTestClass implements Serializable {
    private String targetString = "targetString";;
    private String targetCombineString = "targetCombineString";
    private int targetInteger = 5;
    private long targetLong = 4L;
    private float targetFloat = 3f;
    private double targetDouble = 2d;
    private Date targetDate = new Date(0);
    private String targetAnotherString = "targetAnotherString";
    private boolean targetBoolean = false;
    private short targetShort = 1;
    private byte targetByte = Byte.MIN_VALUE;
    private char targetChar = 'x';
    private BigInteger targetBigInteger = new BigInteger("0");
    private BigDecimal targetBigDecimal = new BigDecimal("0");
    private SmallMappingTestClass targetSmallMappingTestClass = new SmallMappingTestClass();
    private DatesObject dateObjectVariable = new DatesObject("01-01-1989");
    private SimpleEnum targetEnum = SimpleEnum.VALUE2;
}
