class TestType implements Cacheable {
    int data;

    public TestType(int data) {
        this.data = data;
    }

    @Override
    public String getID() {
        return Integer.toString(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestType nodeType = (TestType) o;

        return data == nodeType.data;
    }

    @Override
    public int hashCode() {
        return data;
    }
}
