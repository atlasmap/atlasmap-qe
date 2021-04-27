package io.atlasmap.qe.data.target;

import io.atlasmap.qe.data.NestedCollectionClass;
import lombok.ToString;


@ToString(callSuper=true)
public class TargetNestedCollectionClass extends NestedCollectionClass {

    public TargetNestedCollectionClass(String sourceType) {
        NestedClass array0 = new NestedCollectionClass.NestedClass(sourceType + "FirstArrayValue0");
        NestedClass array1 = new NestedCollectionClass.NestedClass(sourceType + "FirstArrayValue1");

        NestedCollectionClass.NestedClass[] someArray = new NestedCollectionClass.NestedClass[] {
            array0, array1
        };
        setRootArray(someArray);

        NestedCollectionClass.NestedClass array00 = new NestedCollectionClass.NestedClass(sourceType + "SecondArrayValue0-0");
        NestedCollectionClass.NestedClass array01 = new NestedCollectionClass.NestedClass(sourceType + "SecondArrayValue0-1");

        array00.setNestedArray(new NestedCollectionClass.NestedClass[] {new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue0-0-0"),
            new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue0-0-1")});
        array01.setNestedArray(new NestedCollectionClass.NestedClass[] {new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue0-1-0"),
            new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue0-1-1"), new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue0-1-2")});

        array0.setNestedArray(new NestedCollectionClass.NestedClass[] {array00, array01});

        NestedCollectionClass.NestedClass array10 = new NestedCollectionClass.NestedClass(sourceType + "SecondArrayValue1-0");
        NestedCollectionClass.NestedClass array11 = new NestedCollectionClass.NestedClass(sourceType + "SecondArrayValue1-1");
        array10.setNestedArray(new NestedCollectionClass.NestedClass[] {new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue1-0-0"),
            new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue1-0-1"), new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue1-0-2")});
        array11.setNestedArray(new NestedCollectionClass.NestedClass[] {new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue1-1-0"),
            new NestedCollectionClass.NestedClass(sourceType + "ThirdArrayValue1-1-1")});
        array1.setNestedArray(new NestedCollectionClass.NestedClass[] {array10, array11});
    }

    public TargetNestedCollectionClass() {
    }
}
