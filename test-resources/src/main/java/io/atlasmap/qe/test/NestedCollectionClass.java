package io.atlasmap.qe.test;

import lombok.Data;


@Data
public abstract class NestedCollectionClass {

    @Data
    public static class NestedClass {
        private String nestedField;
        private NestedClass[] nestedArray;
        private NestedClass[] nestedRenamedArray;

        public NestedClass(String nestedField) {
            this.nestedField = nestedField;
        }
    }

    private String rootField;
    private NestedClass[] rootArray;
    private NestedClass[] rootRenamedArray;
}
