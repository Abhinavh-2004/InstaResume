import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResumeDisplay extends JFrame {

    public ResumeDisplay(Resume resume) {
        setTitle("Resume");
        setSize(600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set a light background color and create padding for a cleaner look
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Header Section for Name and Contact Information
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel(resume.getName());
        nameLabel.setFont(new Font("Serif", Font.BOLD, 30));
        nameLabel.setForeground(Color.DARK_GRAY);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel contactLabel = new JLabel(
                String.format("%s | %s | %s", resume.getEmail(), resume.getPhone(), resume.getAddress().toString()));
        contactLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        contactLabel.setForeground(Color.GRAY);
        contactLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(nameLabel, BorderLayout.NORTH);
        headerPanel.add(contactLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Main Panel for Body Sections (Objective, Education, Experience, Skills)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add sections to the main panel
        if (resume.getObjective() != null && !resume.getObjective().isEmpty()) {
            mainPanel.add(createSection("Objective", resume.getObjective()));
        }
        mainPanel.add(createSection("Education", formatSection(resume.getEducationList())));
        mainPanel.add(createSection("Experience", formatSection(resume.getExperienceList())));
        mainPanel.add(createSection("Skills", formatSection(resume.getSkills())));

        add(mainPanel, BorderLayout.CENTER);

        // Footer with Close Button
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setBackground(new Color(70, 130, 180));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> System.exit(0));
        footerPanel.add(closeButton);
        add(footerPanel, BorderLayout.SOUTH);

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

    // Method to create sections with titles and content
    private JPanel createSection(String title, String content) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        titleLabel.setForeground(Color.DARK_GRAY);
        sectionPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        contentArea.setForeground(Color.BLACK);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        contentArea.setBackground(Color.WHITE);
        sectionPanel.add(contentArea, BorderLayout.CENTER);

        return sectionPanel;
    }

    private String formatSection(Object data) {
        StringBuilder formatted = new StringBuilder();
        if (data instanceof List) {
            if (!((List<?>) data).isEmpty()) {
                if (((List<?>) data).get(0) instanceof Education) {
                    for (Education edu : (List<Education>) data) {
                        formatted.append(edu.getDegree()).append(" in ").append(edu.getInstitution()).append("\n")
                                .append("Graduation Date: ").append(edu.getGraduationDate()).append("\n\n");
                    }
                } else if (((List<?>) data).get(0) instanceof Experience) {
                    for (Experience exp : (List<Experience>) data) {
                        formatted.append(exp.getJobTitle()).append(" at ").append(exp.getCompany()).append("\n")
                                .append("Duration: ").append(exp.getDuration()).append("\n\n");
                    }
                } else {
                    for (String skill : (List<String>) data) {
                        formatted.append("- ").append(skill).append("\n");
                    }
                }
            }
        }
        return formatted.toString();
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
