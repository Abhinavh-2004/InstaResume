import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResumeDisplay extends JFrame {

    public ResumeDisplay(Resume resume) {
        setTitle("Resume");
        setSize(600, 1000);  // Portrait size for a clean, readable view
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set a modern, light-colored background
        getContentPane().setBackground(new Color(255, 255, 255));

        // Create GridBagLayout for the main layout of the frame
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around each component
        gbc.anchor = GridBagConstraints.CENTER;  // Center alignment

        // Header Panel for Name and Contact Info
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(255, 255, 255));

        JLabel nameLabel = new JLabel(resume.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        nameLabel.setForeground(new Color(0, 0, 0));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel contactLabel = new JLabel(String.format("Email: %s | Phone: %s | Address: %s",
                resume.getEmail(), resume.getPhone(), resume.getAddress().toString()));
        contactLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        contactLabel.setForeground(new Color(50, 50, 50));
        contactLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(nameLabel);
        headerPanel.add(contactLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(headerPanel, gbc);

        // Objective Section
        if (resume.getObjective() != null && !resume.getObjective().isEmpty()) {
            JTextArea objectiveArea = new JTextArea();
            objectiveArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
            objectiveArea.setEditable(false);
            objectiveArea.setText("Objective: " + resume.getObjective());
            objectiveArea.setLineWrap(true);
            objectiveArea.setWrapStyleWord(true);
            objectiveArea.setCaretPosition(0);
            objectiveArea.setBackground(new Color(255, 255, 255));

            JPanel objectivePanel = createSectionPanel("Objective", objectiveArea);
            gbc.gridy = 1;
            add(objectivePanel, gbc);
        }

        // Education Section
        JTextArea educationArea = new JTextArea();
        educationArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        educationArea.setEditable(false);
        educationArea.setText(formatSection("Education", resume.getEducationList()));
        educationArea.setLineWrap(true);
        educationArea.setWrapStyleWord(true);
        educationArea.setCaretPosition(0);
        educationArea.setBackground(new Color(255, 255, 255));

        JPanel educationPanel = createSectionPanel("Education", educationArea);
        gbc.gridy = 2;
        add(educationPanel, gbc);

        // Experience Section
        JTextArea experienceArea = new JTextArea();
        experienceArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        experienceArea.setEditable(false);
        experienceArea.setText(formatSection("Experience", resume.getExperienceList()));
        experienceArea.setLineWrap(true);
        experienceArea.setWrapStyleWord(true);
        experienceArea.setCaretPosition(0);
        experienceArea.setBackground(new Color(255, 255, 255));

        JPanel experiencePanel = createSectionPanel("Experience", experienceArea);
        gbc.gridy = 3;
        add(experiencePanel, gbc);

        // Skills Section
        JTextArea skillsArea = new JTextArea();
        skillsArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        skillsArea.setEditable(false);
        skillsArea.setText(formatSection("Skills", resume.getSkills()));
        skillsArea.setLineWrap(true);
        skillsArea.setWrapStyleWord(true);
        skillsArea.setCaretPosition(0);
        skillsArea.setBackground(new Color(255, 255, 255));

        JPanel skillsPanel = createSectionPanel("Skills", skillsArea);
        gbc.gridy = 4;
        add(skillsPanel, gbc);

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setBackground(new Color(70, 130, 180));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        closeButton.addActionListener(e -> System.exit(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.add(closeButton);

        gbc.gridy = 5;
        add(buttonPanel, gbc);

        // Print the LaTeX formatted string to the terminal
        System.out.println(generateLatexString(resume));
    }

    // Generate a LaTeX-formatted resume string
    private String generateLatexString(Resume resume) {
        StringBuilder latex = new StringBuilder();

        latex.append("\\documentclass[a4paper,10pt]{article}\n")
                .append("\\usepackage[utf8]{inputenc}\n")
                .append("\\usepackage{geometry}\n")
                .append("\\geometry{margin=1in}\n")
                .append("\\begin{document}\n\n");

        latex.append("\\begin{center}\n")
                .append("\\textbf{\\LARGE ").append(resume.getName()).append("}\\\\\n")
                .append(resume.getAddress()).append(" \\\\ ")
                .append(resume.getEmail()).append(" \\quad | \\quad ")
                .append(resume.getPhone()).append(" \n")
                .append("\\end{center}\n\n");

        latex.append("\\section*{Objective}\n")
                .append(resume.getObjective() != null ? resume.getObjective() : "Seeking an entry-level software engineering position.")
                .append("\n\n");

        latex.append("\\section*{Education}\n");
        for (Education edu : resume.getEducationList()) {
            latex.append("\\textbf{").append(edu.getDegree()).append("} - ").append(edu.getInstitution()).append(", ")
                    .append(edu.getGraduationDate()).append("\\\\\n\n");
        }

        latex.append("\\section*{Experience}\n");
        for (Experience exp : resume.getExperienceList()) {
            latex.append("\\textbf{").append(exp.getJobTitle()).append("} - ").append(exp.getCompany())
                    .append(" (").append(exp.getDuration()).append(")\\\\\n\\begin{itemize}\n");
            for (String task : exp.getTasks()) {
                latex.append("\\item ").append(task).append("\n");
            }
            latex.append("\\end{itemize}\n\n");
        }

        latex.append("\\section*{Skills}\n\\begin{itemize}\n");
        for (String skill : resume.getSkills()) {
            latex.append("\\item ").append(skill).append("\n");
        }
        latex.append("\\end{itemize}\n\n");

        latex.append("\\end{document}");

        return latex.toString();
    }

    // Format the sections like Education, Experience, Skills with headings
    private String formatSection(String sectionTitle, Object data) {
        StringBuilder formatted = new StringBuilder();
        formatted.append(sectionTitle).append("\n");
        formatted.append("-----------\n");

        if (sectionTitle.equals("Education")) {
            for (Education edu : (List<Education>) data) {
                formatted.append(edu.getDegree()).append(" in ").append(edu.getInstitution()).append("\n");
                formatted.append("Graduation Date: ").append(edu.getGraduationDate()).append("\n\n");
            }
        } else if (sectionTitle.equals("Experience")) {
            for (Experience exp : (List<Experience>) data) {
                formatted.append(exp.getJobTitle()).append(" at ").append(exp.getCompany()).append("\n");
                formatted.append("Duration: ").append(exp.getDuration()).append("\n\n");
            }
        } else if (sectionTitle.equals("Skills")) {
            for (String skill : (List<String>) data) {
                formatted.append("- ").append(skill).append("\n");
            }
        }
        return formatted.toString();
    }

    // Helper method to create a panel for each section with a title
    private JPanel createSectionPanel(String title, JTextArea area) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(new Color(255, 255, 255));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 0, 0));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        area.setFont(new Font("SansSerif", Font.PLAIN, 16));
        area.setForeground(new Color(50, 50, 50));
        area.setBackground(new Color(255, 255, 255));
        area.setEditable(false);

        sectionPanel.add(titleLabel);
        sectionPanel.add(Box.createVerticalStrut(10));  // Space between title and text area
        sectionPanel.add(area);
        sectionPanel.add(Box.createVerticalStrut(10));  // Space after text area

        return sectionPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Resume resume = new Resume();
            // Assume you've populated the resume object with real data
            ResumeDisplay display = new ResumeDisplay(resume);
            display.setVisible(true);
        });
    }
}
