import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResumeBuilderAppSwing extends JFrame {
    private Resume resume;

    public ResumeBuilderAppSwing() {
        resume = new Resume();
        setTitle("Resume Builder");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        // Personal Info
        add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Address (Street):"));
        JTextField streetField = new JTextField();
        add(streetField);

        add(new JLabel("City:"));
        JTextField cityField = new JTextField();
        add(cityField);

        add(new JLabel("State:"));
        JTextField stateField = new JTextField();
        add(stateField);

        add(new JLabel("Zip:"));
        JTextField zipField = new JTextField();
        add(zipField);

        add(new JLabel("Objective:"));
        JTextArea objectiveArea = new JTextArea();
        add(new JScrollPane(objectiveArea));

        // Education fields
        add(new JLabel("Education - Degree:"));
        JTextField degreeField = new JTextField();
        add(degreeField);

        add(new JLabel("Institution:"));
        JTextField institutionField = new JTextField();
        add(institutionField);

        add(new JLabel("Graduation Month:"));
        JTextField gradMonthField = new JTextField();
        add(gradMonthField);

        add(new JLabel("Graduation Year:"));
        JTextField gradYearField = new JTextField();
        add(gradYearField);

        JButton addEducationButton = new JButton("Add Education");
        add(addEducationButton);

        // Experience fields
        add(new JLabel("Experience - Job Title:"));
        JTextField jobTitleField = new JTextField();
        add(jobTitleField);

        add(new JLabel("Company:"));
        JTextField companyField = new JTextField();
        add(companyField);

        add(new JLabel("Duration Month:"));
        JTextField durationMonthField = new JTextField();
        add(durationMonthField);

        add(new JLabel("Duration Year:"));
        JTextField durationYearField = new JTextField();
        add(durationYearField);

        JButton addExperienceButton = new JButton("Add Experience");
        add(addExperienceButton);

        // Skills field
        add(new JLabel("Skill:"));
        JTextField skillField = new JTextField();
        add(skillField);

        JButton addSkillButton = new JButton("Add Skill");
        add(addSkillButton);

        // Generate Resume Button
        JButton generateButton = new JButton("Generate Resume");
        add(generateButton);

        // Text Area for displaying the resume
        JTextArea resumeDisplayArea = new JTextArea();
        resumeDisplayArea.setEditable(false);
        add(new JScrollPane(resumeDisplayArea));

        // Event Handlers
        addEducationButton.addActionListener(e -> {
            String degree = degreeField.getText();
            String institution = institutionField.getText();
            String month = gradMonthField.getText();
            String year = gradYearField.getText();
            if (!degree.isEmpty() && !institution.isEmpty() && !month.isEmpty() && !year.isEmpty()) {
                Date graduationDate = new Date(month, year);
                resume.addEducation(new Education(degree, institution, graduationDate));
                degreeField.setText("");
                institutionField.setText("");
                gradMonthField.setText("");
                gradYearField.setText("");
            }
        });

        addExperienceButton.addActionListener(e -> {
            String jobTitle = jobTitleField.getText();
            String company = companyField.getText();
            String month = durationMonthField.getText();
            String year = durationYearField.getText();
            if (!jobTitle.isEmpty() && !company.isEmpty() && !month.isEmpty() && !year.isEmpty()) {
                Date duration = new Date(month, year);
                resume.addExperience(new Experience(jobTitle, company, duration));
                jobTitleField.setText("");
                companyField.setText("");
                durationMonthField.setText("");
                durationYearField.setText("");
            }
        });

        addSkillButton.addActionListener(e -> {
            String skill = skillField.getText();
            if (!skill.isEmpty()) {
                resume.addSkill(skill);
                skillField.setText("");
            }
        });

        generateButton.addActionListener(e -> {
            resume.setName(nameField.getText());
            resume.setEmail(emailField.getText());
            resume.setPhone(phoneField.getText());
            Address address = new Address(streetField.getText(), cityField.getText(), stateField.getText(), zipField.getText());
            resume.setAddress(address);
            resume.setObjective(objectiveArea.getText());

            // Display the resume
            JFrame displayFrame = new JFrame("Generated Resume");
            displayFrame.setSize(400, 600);
            JTextArea displayArea = new JTextArea(resume.toString());
            displayArea.setEditable(false);
            displayFrame.add(new JScrollPane(displayArea));
            displayFrame.setVisible(true);
            displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.dispose(); // Close the input frame
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResumeBuilderAppSwing app = new ResumeBuilderAppSwing();
            app.setVisible(true);
        });
    }
}
