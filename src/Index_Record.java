class Index_Record {
    private final int  starting_point;
    private final int   length;

    public Index_Record(int starting_point, int length) {
        this.starting_point = starting_point;
        this.length = length;
    }

    public int getStarting_point() {
        return starting_point;
    }

    public int getLength() {
        return length;
    }
}
