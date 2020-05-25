package net.leezw.lineparser;


public class TestParser implements ObjParser<TestEntity> {

    @Override public TestEntity parser(String value) {
        return new TestEntity(value);
    }
}
