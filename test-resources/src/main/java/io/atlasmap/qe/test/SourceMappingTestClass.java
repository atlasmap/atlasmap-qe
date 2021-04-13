package io.atlasmap.qe.test;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * Simple class that is used to verify mappings from Java objects.
 *
 * @author Ondřej Kuhejda
 */
@Data
public class SourceMappingTestClass implements Serializable {
    private String sourceString = "sourceString";
    private String sourceCombineString = "sourceCombine";
    private int sourceInteger = 1;
    private long sourceLong = 2L;
    private float sourceFloat = 3f;
    private double sourceDouble = 4d;
    private BigDecimal sourceBigDecimal = new BigDecimal("12345");
    private Date sourceDate = Date.from(ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")).toInstant());
    private String sourceAnotherString = "sourceAnotherString";
    private boolean sourceBoolean = true;
    private short sourceShort = 5;
    private byte sourceByte = Byte.MAX_VALUE;
    private char sourceChar = 'A';
    private BigInteger sourceBigInteger = new BigInteger("12345");
    private SmallMappingTestClass smallMappingTestClass;
    private SimpleEnum sourceEnum = SimpleEnum.VALUE1;
}
