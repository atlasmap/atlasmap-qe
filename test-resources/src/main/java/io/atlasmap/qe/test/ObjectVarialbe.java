package io.atlasmap.qe.test;

import java.io.Serializable;

/**
 * Created by mmelko on 27/10/2017.
 */
public class ObjectVarialbe implements Serializable {

    private String objectField1;

    private String objectField2;

    private Long objectLong;

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
}

