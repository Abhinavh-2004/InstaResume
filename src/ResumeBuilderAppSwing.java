import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

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

        // Set names for validation
        nameField.setName("name");
        emailField.setName("email");
        phoneField.setName("phone");
        zipField.setName("zip");
        streetField.setName("street");
        cityField.setName("city");
        stateField.setName("state");

        addField(inputPanel, gbc, "Name:", nameField);
        addField(inputPanel, gbc, "Email:", emailField);
        addField(inputPanel, gbc, "Phone:", phoneField);
        addField(inputPanel, gbc, "Street:", streetField);
        addField(inputPanel, gbc, "City:", cityField);
        addField(inputPanel, gbc, "State:", stateField);
        addField(inputPanel, gbc, "Zip:", zipField);

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

        // Skills Field
        JTextField skillField = new JTextField(20);
        addField(inputPanel, gbc, "Skill:", skillField);

        // Buttons
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

        addValidation(nameField, emailField, phoneField, zipField, streetField, cityField, stateField);

        addEducationButton.addActionListener(e -> {
            if (validateField(degreeField) && validateField(institutionField)
                    && validateField(gradMonthField) && validateField(gradYearField)) {
                Date graduationDate = new Date(gradMonthField.getText(), gradYearField.getText());
                resume.addEducation(new Education(degreeField.getText(), institutionField.getText(), graduationDate));
                clearFields(degreeField, institutionField, gradMonthField, gradYearField);
                JOptionPane.showMessageDialog(this, "Education added!");
            }
        });

        addExperienceButton.addActionListener(e -> {
            if (validateField(jobTitleField) && validateField(companyField)
                    && validateField(durationMonthField) && validateField(durationYearField)) {
                Date duration = new Date(durationMonthField.getText(), durationYearField.getText());
                resume.addExperience(new Experience(jobTitleField.getText(), companyField.getText(), duration));
                clearFields(jobTitleField, companyField, durationMonthField, durationYearField);
                JOptionPane.showMessageDialog(this, "Experience added!");
            }
        });

        addSkillButton.addActionListener(e -> {
            if (validateField(skillField)) {
                resume.addSkill(skillField.getText());
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

            ResumeDisplay display = new ResumeDisplay(resume);
            display.setVisible(true);
            this.dispose();
        });
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
            field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
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

        // Check for email validation
        if (field.getName().equals("email")) {
            // Convert the email to lowercase
            String email = text.toLowerCase();

            // Validate email: only allows gmail.com or .com, .org, .edu domains
            return Pattern.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$", email)
                    || Pattern.matches("^[A-Za-z0-9+_.-]+\\.(com|org|edu)$", email);
        }

        // Check for phone number validation (only 10 digits allowed)
        else if (field.getName().equals("phone")) {
            return Pattern.matches("^[0-9]{10}$", text); // Validates 10 digit phone number
        }

        // Check for zip code validation (Indian PIN codes, allowing 6 digits)
        else if (field.getName().equals("zip")) {
            return Pattern.matches("^[0-9]{6}$", text); // Validates 6 digit zip code (PIN code)
        }

        // Check for city, state, and street - no special characters allowed
        else if (field.getName().equals("city") || field.getName().equals("state") || field.getName().equals("street")) {
            return Pattern.matches("^[A-Za-z0-9\\s'-]+$", text); // Validates no special characters
        }

        else {
            return !text.isEmpty();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResumeBuilderAppSwing app = new ResumeBuilderAppSwing();
            app.setVisible(true);
        });
    }
}
