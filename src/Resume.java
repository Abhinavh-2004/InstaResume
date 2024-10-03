import java.util.ArrayList;
import java.util.List;

public class Resume {
    private String name;
    private String email;
    private String phone;
    private Address address;
    private String objective;
    private List<Education> educationList;
    private List<Experience> experienceList;
    private List<String> skills;

    public Resume() {
        educationList = new ArrayList<>();
        experienceList = new ArrayList<>();
        skills = new ArrayList<>();
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void addEducation(Education education) {
        educationList.add(education);
    }

    public void addExperience(Experience experience) {
        experienceList.add(experience);
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public String getObjective() {
        return objective;
    }

    public List<Education> getEducationList() {
        return educationList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public List<String> getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n")
                .append("Email: ").append(email).append("\n")
                .append("Phone: ").append(phone).append("\n")
                .append("Address: ").append(address).append("\n")
                .append("Objective: ").append(objective).append("\n\n")
                .append("Education:\n");

        if (educationList.isEmpty()) {
            sb.append("No education entries added.\n");
        } else {
            for (Education edu : educationList) {
                sb.append(edu).append("\n");
            }
        }

        sb.append("\nExperience:\n");
        if (experienceList.isEmpty()) {
            sb.append("No experience entries added.\n");
        } else {
            for (Experience exp : experienceList) {
                sb.append(exp).append("\n");
            }
        }

        sb.append("\nSkills:\n");
        if (skills.isEmpty()) {
            sb.append("No skills added.\n");
        } else {
            for (String skill : skills) {
                sb.append(skill).append("\n");
            }
        }
        return sb.toString();
    }
}
