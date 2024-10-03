public class Experience {
    private String jobTitle;
    private String company;
    private Date duration;

    public Experience(String jobTitle, String company, Date duration) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return jobTitle + " at " + company + " (Duration: " + duration + ")";
    }
}
