public class Date {
    private String month;
    private String year;

    public Date(String month, String year) {
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return month + " " + year;
    }
}
