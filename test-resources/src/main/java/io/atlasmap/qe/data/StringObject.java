package io.atlasmap.qe.data;

import lombok.Data;


@Data
public class StringObject {
    private String lastName;
    private String firstName;

    public StringObject() {
        this.firstName = "John";
        this.lastName = "Snow";
    }

    public StringObject(String p) {
        this.firstName = p + "_John";
        this.lastName = p + "_Snow";
    }

    public StringObject (String first,String last) {
        this.firstName = first;
        this.lastName = last;
    }
}
