package io.atlasmap.qe.test;

public class TargetNestedCollectionClass extends BaseClass {

    public TargetNestedCollectionClass(String sourceType) {
        NestedClass array0 = new BaseClass.NestedClass(sourceType + "FirstArrayValue0");
        NestedClass array1 = new BaseClass.NestedClass(sourceType + "FirstArrayValue1");

        BaseClass.NestedClass[] someArray = new BaseClass.NestedClass[] {
            array0, array1
        };
        setRootArray(someArray);

        BaseClass.NestedClass array00 = new BaseClass.NestedClass(sourceType + "SecondArrayValue0-0");
        BaseClass.NestedClass array01 = new BaseClass.NestedClass(sourceType + "SecondArrayValue0-1");

        array00.setNestedArray(new BaseClass.NestedClass[] {new BaseClass.NestedClass(sourceType + "ThirdArrayValue0-0-0"),
            new BaseClass.NestedClass(sourceType + "ThirdArrayValue0-0-1")});
        array01.setNestedArray(new BaseClass.NestedClass[] {new BaseClass.NestedClass(sourceType + "ThirdArrayValue0-1-0"),
            new BaseClass.NestedClass(sourceType + "ThirdArrayValue0-1-1"), new BaseClass.NestedClass(sourceType + "ThirdArrayValue0-1-2")});

        array0.setNestedArray(new BaseClass.NestedClass[] {array00, array01});

        BaseClass.NestedClass array10 = new BaseClass.NestedClass(sourceType + "SecondArrayValue1-0");
        BaseClass.NestedClass array11 = new BaseClass.NestedClass(sourceType + "SecondArrayValue1-1");
        array10.setNestedArray(new BaseClass.NestedClass[] {new BaseClass.NestedClass(sourceType + "ThirdArrayValue1-0-0"),
            new BaseClass.NestedClass(sourceType + "ThirdArrayValue1-0-1"), new BaseClass.NestedClass(sourceType + "ThirdArrayValue1-0-2")});
        array11.setNestedArray(new BaseClass.NestedClass[] {new BaseClass.NestedClass(sourceType + "ThirdArrayValue1-1-0"),
            new BaseClass.NestedClass(sourceType + "ThirdArrayValue1-1-1")});
        array1.setNestedArray(new BaseClass.NestedClass[] {array10, array11});
    }

    public TargetNestedCollectionClass() {
    }

    @Override
    public String toString() {
        return "TargetNestedCollectionClass{} " + super.toString();
    }
}
