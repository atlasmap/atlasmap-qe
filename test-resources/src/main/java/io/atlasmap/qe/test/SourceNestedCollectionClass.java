package io.atlasmap.qe.test;

public class SourceNestedCollectionClass extends BaseClass {

    public SourceNestedCollectionClass() {

        NestedClass array0 = new BaseClass.NestedClass("javaFirstArrayValue0");
        NestedClass array1 = new BaseClass.NestedClass("javaFirstArrayValue1");

        BaseClass.NestedClass[] someArray = new BaseClass.NestedClass[] {
            array0, array1
        };
        setRootArray(someArray);

        BaseClass.NestedClass array00 = new BaseClass.NestedClass("javaSecondArrayValue0-0");
        BaseClass.NestedClass array01 = new BaseClass.NestedClass("javaSecondArrayValue0-1");

        array00.setNestedArray(new BaseClass.NestedClass[] { new BaseClass.NestedClass("javaThirdArrayValue0-0-0"),
            new BaseClass.NestedClass("javaThirdArrayValue0-0-1")});
        array01.setNestedArray(new BaseClass.NestedClass[] { new BaseClass.NestedClass("javaThirdArrayValue0-1-0"),
            new BaseClass.NestedClass("javaThirdArrayValue0-1-1"), new BaseClass.NestedClass("javaThirdArrayValue0-1-2")});

        array0.setNestedArray(new BaseClass.NestedClass[] {array00, array01});

        BaseClass.NestedClass array10 = new BaseClass.NestedClass("javaSecondArrayValue1-0");
        BaseClass.NestedClass array11 = new BaseClass.NestedClass("javaSecondArrayValue1-1");
        array10.setNestedArray(new BaseClass.NestedClass[] { new BaseClass.NestedClass("javaThirdArrayValue1-0-0"),
            new BaseClass.NestedClass("javaThirdArrayValue1-0-1"), new BaseClass.NestedClass("javaThirdArrayValue1-0-2")});
        array11.setNestedArray(new BaseClass.NestedClass[] { new BaseClass.NestedClass("javaThirdArrayValue1-1-0"),
            new BaseClass.NestedClass("javaThirdArrayValue1-1-1")});
        array1.setNestedArray(new BaseClass.NestedClass[] { array10, array11 });
    }

    @Override
    public String toString() {
        return "SourceNestedCollectionClass{} " + super.toString();
    }
}
