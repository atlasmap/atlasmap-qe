package io.atlasmap.qe.test;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "StringObject{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
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
        StringObject that = (StringObject) o;
        return Objects.equals(lastName, that.lastName) &&
                Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lastName, firstName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

}
