import javax.swing.*;
import java.awt.*;

public class ResumeDisplay extends JFrame {
    public ResumeDisplay(Resume resume) {
        setTitle("Generated Resume");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea resumeTextArea = new JTextArea();
        resumeTextArea.setEditable(false);
        resumeTextArea.setText(resume.toString());

        JScrollPane scrollPane = new JScrollPane(resumeTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Optional: Add a close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> System.exit(0));
        add(closeButton, BorderLayout.SOUTH);
    }
}
