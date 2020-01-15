package io.atlasmap.qe.test;

import java.util.Arrays;
import java.util.Objects;

public abstract class BaseClass {

    public static class NestedClass {
        private String nestedField;
        private NestedClass[] nestedArray;
        private NestedClass[] nestedRenamedArray;

        public String getNestedField() {
            return nestedField;
        }

        public void setNestedField(String nestedField) {
            this.nestedField = nestedField;
        }

        public NestedClass() {
        }

        public NestedClass(String nestedField) {
            this.nestedField = nestedField;
        }

        public NestedClass[] getNestedArray() {
            return nestedArray;
        }

        public void setNestedArray(NestedClass[] nestedArray) {
            this.nestedArray = nestedArray;
        }

        public NestedClass[] getNestedRenamedArray() {
            return nestedRenamedArray;
        }

        public void setNestedRenamedArray(NestedClass[] nestedRenamedArray) {
            this.nestedRenamedArray = nestedRenamedArray;
        }

        @Override
        public String toString() {
            return "NestedClass{" +
                "nestedField='" + nestedField + '\'' +
                ", nestedArray=" + Arrays.toString(nestedArray) +
                ", nestedRenamedArray=" + Arrays.toString(nestedRenamedArray) +
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
            NestedClass that = (NestedClass) o;
            return Objects.equals(nestedField, that.nestedField) &&
                Arrays.equals(nestedArray, that.nestedArray) &&
                Arrays.equals(nestedRenamedArray, that.nestedRenamedArray);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(nestedField);
            result = 31 * result + Arrays.hashCode(nestedArray);
            result = 31 * result + Arrays.hashCode(nestedRenamedArray);
            return result;
        }
    }

    private String rootField;
    private NestedClass[] rootArray;
    private NestedClass[] rootRenamedArray;

    public String getRootField() {
        return rootField;
    }

    public void setRootField(String rootField) {
        this.rootField = rootField;
    }

    public NestedClass[] getRootArray() {
        return rootArray;
    }

    public void setRootArray(NestedClass[] rootArray) {
        this.rootArray = rootArray;
    }

    public NestedClass[] getRootRenamedArray() {
        return rootRenamedArray;
    }

    public void setRootRenamedArray(NestedClass[] rootRenamedArray) {
        this.rootRenamedArray = rootRenamedArray;
    }

    @Override
    public String toString() {
        return "BaseClass{" +
            "rootField='" + rootField + '\'' +
            ", rootArray=" + Arrays.toString(rootArray) +
            ", rootRenamedArray=" + Arrays.toString(rootRenamedArray) +
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
        BaseClass baseClass = (BaseClass) o;
        return Objects.equals(rootField, baseClass.rootField) &&
            Arrays.equals(rootArray, baseClass.rootArray) &&
            Arrays.equals(rootRenamedArray, baseClass.rootRenamedArray);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rootField);
        result = 31 * result + Arrays.hashCode(rootArray);
        result = 31 * result + Arrays.hashCode(rootRenamedArray);
        return result;
    }
}