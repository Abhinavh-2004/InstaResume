import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ResumeBuilderAppSwing extends JFrame {
    private Resume resume;
    private JButton generateButton;

    public ResumeBuilderAppSwing() {
        resume = new Resume();
        setTitle("Resume Builder");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(new JLabel("Resume Builder", JLabel.CENTER), gbc);
        gbc.gridy++;

        // Personal Info
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField streetField = new JTextField(20);
        JTextField cityField = new JTextField(20);
        JTextField stateField = new JTextField(20);
        JTextField zipField = new JTextField(10);

        nameField.setName("name");
        emailField.setName("email");
        phoneField.setName("phone");
        streetField.setName("street");
        cityField.setName("city");
        stateField.setName("state");
        zipField.setName("zip");

        addField(inputPanel, gbc, "Name:", nameField);
        addField(inputPanel, gbc, "Email:", emailField);
        addField(inputPanel, gbc, "Phone:", phoneField);
        addField(inputPanel, gbc, "Street:", streetField);
        addField(inputPanel, gbc, "City:", cityField);
        addField(inputPanel, gbc, "State:", stateField);
        addField(inputPanel, gbc, "Zip:", zipField);

        // Objective
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        inputPanel.add(new JLabel("Objective:"), gbc);
        gbc.gridy++;
        JTextArea objectiveArea = new JTextArea(3, 20);
        objectiveArea.setLineWrap(true);
        objectiveArea.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(objectiveArea), gbc);

        // Education Fields
        JTextField degreeField = new JTextField(20);
        JTextField institutionField = new JTextField(20);
        JTextField gradMonthField = new JTextField(10);
        JTextField gradYearField = new JTextField(10);

        addField(inputPanel, gbc, "Education - Degree:", degreeField);
        addField(inputPanel, gbc, "Institution:", institutionField);
        addField(inputPanel, gbc, "Graduation Month:", gradMonthField);
        addField(inputPanel, gbc, "Graduation Year:", gradYearField);

        // Experience Fields
        JTextField jobTitleField = new JTextField(20);
        JTextField companyField = new JTextField(20);
        JTextField durationMonthField = new JTextField(10);
        JTextField durationYearField = new JTextField(10);

        addField(inputPanel, gbc, "Experience - Job Title:", jobTitleField);
        addField(inputPanel, gbc, "Company:", companyField);
        addField(inputPanel, gbc, "Duration Month:", durationMonthField);
        addField(inputPanel, gbc, "Duration Year:", durationYearField);

        // Skill Field
        JTextField skillField = new JTextField(20);
        addField(inputPanel, gbc, "Skill:", skillField);

        // Buttons for Education, Experience, Skills, and Generate Resume
        JButton addEducationButton = new JButton("Add Education");
        JButton addExperienceButton = new JButton("Add Experience");
        JButton addSkillButton = new JButton("Add Skill");
        generateButton = new JButton("Generate Resume");
        generateButton.setEnabled(false);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(addEducationButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(addExperienceButton, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(addSkillButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(generateButton, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // Event Handlers
        addEducationButton.addActionListener(e -> {
            String degree = degreeField.getText();
            String institution = institutionField.getText();
            String month = gradMonthField.getText();
            String year = gradYearField.getText();

            if (!degree.isEmpty() && !institution.isEmpty() && !month.isEmpty() && !year.isEmpty()) {
                Date graduationDate = new Date(month, year);
                resume.addEducation(new Education(degree, institution, graduationDate));
                clearFields(degreeField, institutionField, gradMonthField, gradYearField);
                JOptionPane.showMessageDialog(this, "Education added!");
            }
        });

        addExperienceButton.addActionListener(e -> {
            String jobTitle = jobTitleField.getText();
            String company = companyField.getText();
            String month = durationMonthField.getText();
            String year = durationYearField.getText();

            if (!jobTitle.isEmpty() && !company.isEmpty() && !month.isEmpty() && !year.isEmpty()) {
                Date duration = new Date(month, year);

                // Collect tasks for the experience
                List<String> tasks = new ArrayList<>();
                String task;
                do {
                    task = JOptionPane.showInputDialog(this, "Enter a task (leave empty to finish):");
                    if (task != null && !task.isEmpty()) {
                        tasks.add(task);
                    }
                } while (task != null && !task.isEmpty());

                resume.addExperience(new Experience(jobTitle, company, duration, tasks));
                clearFields(jobTitleField, companyField, durationMonthField, durationYearField);
                JOptionPane.showMessageDialog(this, "Experience added!");
            }
        });

        addSkillButton.addActionListener(e -> {
            String skill = skillField.getText();
            if (!skill.isEmpty()) {
                resume.addSkill(skill);
                skillField.setText("");
                JOptionPane.showMessageDialog(this, "Skill added!");
            }
        });

        generateButton.addActionListener(e -> {
            resume.setName(nameField.getText());
            resume.setEmail(emailField.getText());
            resume.setPhone(phoneField.getText());
            Address address = new Address(streetField.getText(), cityField.getText(), stateField.getText(), zipField.getText());
            resume.setAddress(address);
            resume.setObjective(objectiveArea.getText());

            // Open the ResumeDisplay JFrame
            ResumeDisplay display = new ResumeDisplay(resume);
            display.setVisible(true);
            this.dispose();
        });

        addValidation(nameField, emailField, phoneField, streetField, cityField, stateField, zipField);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void addValidation(JTextField... fields) {
        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    checkFields(fields);
                }
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    checkFields(fields);
                }
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    checkFields(fields);
                }
            });
        }
    }

    private void checkFields(JTextField... fields) {
        boolean valid = true;
        for (JTextField field : fields) {
            valid &= validateField(field);
        }
        generateButton.setEnabled(valid);
    }

    private boolean validateField(JTextField field) {
        String text = field.getText().trim();

        if (field == null) return false;

        // Name validation (non-empty)
        if (field.getName().equals("name")) {
            return !text.isEmpty();
        }
        // Email validation
        else if (field.getName().equals("email")) {
            return Pattern.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$", text)
                    || Pattern.matches("^[A-Za-z0-9+_.-]+\\.(com|org|edu)$", text);
        }
        // Phone validation (10 digits)
        else if (field.getName().equals("phone")) {
            return Pattern.matches("^[0-9]{10}$", text);
        }
        // Street, City, State validation (non-empty)
        else if (field.getName().equals("street") || field.getName().equals("city") || field.getName().equals("state")) {
            return !text.isEmpty();
        }
        // Zip code validation (5 digits)
        else if (field.getName().equals("zip")) {
            return Pattern.matches("^[0-9]{6}$", text);
        }

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResumeBuilderAppSwing app = new ResumeBuilderAppSwing();
            app.setVisible(true);
        });
    }
}
