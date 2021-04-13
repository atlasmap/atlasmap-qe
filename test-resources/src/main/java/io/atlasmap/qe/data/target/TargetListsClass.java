package io.atlasmap.qe.data.target;


import io.atlasmap.qe.data.source.SourceListsClass;

public class TargetListsClass extends SourceListsClass {
    public TargetListsClass() {
        this.getStrings().clear();
        this.getDoubles().clear();
        this.getIntegers().clear();
        this.getFloats().clear();
    }
}
