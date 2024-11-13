import java.util.List;
import java.util.ArrayList;

public class Experience {
    private String jobTitle;
    private String company;
    private Date duration;
    private List<String> tasks;  // New field for tasks

    // Constructor with tasks as an additional parameter
    public Experience(String jobTitle, String company, Date duration, List<String> tasks) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.duration = duration;
        this.tasks = tasks != null ? tasks : new ArrayList<>(); // Initializes to an empty list if null
    }

    // Getter methods
    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public Date getDuration() {
        return duration;
    }

    public List<String> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return jobTitle + " at " + company + " (Duration: " + duration + ")";
    }
}
