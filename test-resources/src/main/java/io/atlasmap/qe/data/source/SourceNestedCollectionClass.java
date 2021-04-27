package io.atlasmap.qe.data.source;

import io.atlasmap.qe.data.NestedCollectionClass;
import lombok.ToString;


@ToString(callSuper=true)
public class SourceNestedCollectionClass extends NestedCollectionClass {

    public SourceNestedCollectionClass() {
        NestedClass array0 = new NestedCollectionClass.NestedClass("javaFirstArrayValue0");
        NestedClass array1 = new NestedCollectionClass.NestedClass("javaFirstArrayValue1");

        NestedCollectionClass.NestedClass[] someArray = new NestedCollectionClass.NestedClass[] {
            array0, array1
        };
        setRootArray(someArray);

        NestedCollectionClass.NestedClass array00 = new NestedCollectionClass.NestedClass("javaSecondArrayValue0-0");
        NestedCollectionClass.NestedClass array01 = new NestedCollectionClass.NestedClass("javaSecondArrayValue0-1");

        array00.setNestedArray(new NestedCollectionClass.NestedClass[] { new NestedCollectionClass.NestedClass("javaThirdArrayValue0-0-0"),
            new NestedCollectionClass.NestedClass("javaThirdArrayValue0-0-1")});
        array01.setNestedArray(new NestedCollectionClass.NestedClass[] { new NestedCollectionClass.NestedClass("javaThirdArrayValue0-1-0"),
            new NestedCollectionClass.NestedClass("javaThirdArrayValue0-1-1"), new NestedCollectionClass.NestedClass("javaThirdArrayValue0-1-2")});

        array0.setNestedArray(new NestedCollectionClass.NestedClass[] {array00, array01});

        NestedCollectionClass.NestedClass array10 = new NestedCollectionClass.NestedClass("javaSecondArrayValue1-0");
        NestedCollectionClass.NestedClass array11 = new NestedCollectionClass.NestedClass("javaSecondArrayValue1-1");
        array10.setNestedArray(new NestedCollectionClass.NestedClass[] { new NestedCollectionClass.NestedClass("javaThirdArrayValue1-0-0"),
            new NestedCollectionClass.NestedClass("javaThirdArrayValue1-0-1"), new NestedCollectionClass.NestedClass("javaThirdArrayValue1-0-2")});
        array11.setNestedArray(new NestedCollectionClass.NestedClass[] { new NestedCollectionClass.NestedClass("javaThirdArrayValue1-1-0"),
            new NestedCollectionClass.NestedClass("javaThirdArrayValue1-1-1")});
        array1.setNestedArray(new NestedCollectionClass.NestedClass[] { array10, array11 });
    }
}
