import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.print.*;

public class ResumeBuilder {
    private static HashMap<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static final String DATA_FILE = "users.dat";

    public static void main(String[] args) {
        loadUsers();
        showHomePage();
    }

    private static void showHomePage() {
        JFrame frame = new JFrame("Resume Builder - Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Header
        JLabel titleLabel = new JLabel("Professional Resume Builder", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // Info Panel
        JTextArea infoArea = new JTextArea();
        infoArea.setText("Welcome to Professional Resume Builder!\n\n" +
                "Features:\n" +
                "• Create professional resumes in minutes\n" +
                "• Easy-to-use interface\n" +
                "• Customizable templates\n" +
                "• Save and edit your resume anytime\n" +
                "• Export to PDF format\n" +
                "• Job matching based on your skills\n\n" +
                "Get started by signing up or logging in!");
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(240, 240, 240));
        infoArea.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JScrollPane infoScroll = new JScrollPane(infoArea);
        infoScroll.setBorder(null);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");

        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        loginButton.setBackground(new Color(0, 102, 204));
        signupButton.setBackground(new Color(0, 153, 76));
        
        loginButton.setForeground(Color.WHITE);
        signupButton.setForeground(Color.WHITE);
        
        loginButton.setPreferredSize(new Dimension(120, 40));
        signupButton.setPreferredSize(new Dimension(120, 40));

        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });

        signupButton.addActionListener(e -> {
            frame.dispose();
            showSignupPage();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(signupButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(infoScroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void showLoginPage() {
        JFrame frame = new JFrame("Resume Builder - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login to Your Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        mainPanel.add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        mainPanel.add(loginButton, gbc);

        gbc.gridy = 4;
        JButton backButton = new JButton("Back to Home");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        mainPanel.add(backButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                currentUser = users.get(username);
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                showResumeForm();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!");
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showHomePage();
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void showSignupPage() {
        JFrame frame = new JFrame("Resume Builder - Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Create New Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        mainPanel.add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);
        
        gbc.gridx = 1;
        JPasswordField confirmPasswordField = new JPasswordField(15);
        mainPanel.add(confirmPasswordField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(0, 153, 76));
        signupButton.setForeground(Color.WHITE);
        mainPanel.add(signupButton, gbc);

        gbc.gridy = 5;
        JButton backButton = new JButton("Back to Home");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        mainPanel.add(backButton, gbc);

        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match!");
                return;
            }

            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(frame, "Username already exists!");
                return;
            }

            users.put(username, new User(username, password));
            saveUsers();
            JOptionPane.showMessageDialog(frame, "Account created successfully!");
            frame.dispose();
            showLoginPage();
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showHomePage();
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void showResumeForm() {
        JFrame frame = new JFrame("Resume Builder - Create Resume");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Personal Information Tab
        JPanel personalPanel = createPersonalInfoPanel();
        tabbedPane.addTab("Personal Info", personalPanel);

        // Education Tab
        JPanel educationPanel = createEducationPanel();
        tabbedPane.addTab("Education", educationPanel);

        // Experience Tab
        JPanel experiencePanel = createExperiencePanel();
        tabbedPane.addTab("Experience", experiencePanel);

        // Skills Tab
        JPanel skillsPanel = createSkillsPanel();
        tabbedPane.addTab("Skills", skillsPanel);

        // Preview and Jobs Tab
        JPanel previewPanel = createPreviewPanel();
        tabbedPane.addTab("Preview & Jobs", previewPanel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Logout button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            currentUser = null;
            showHomePage();
        });
        bottomPanel.add(logoutButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Full Name:", "Email:", "Phone:", "Address:", "LinkedIn:", "Summary:"};
        JTextField[] fields = new JTextField[labels.length - 1];
        JTextArea summaryArea = new JTextArea(4, 30);

        for (int i = 0; i < labels.length - 1; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            panel.add(new JLabel(labels[i]), gbc);
            
            gbc.gridx = 1;
            fields[i] = new JTextField(20);
            if (currentUser.getResume() != null) {
                switch(i) {
                    case 0: fields[i].setText(currentUser.getResume().getFullName()); break;
                    case 1: fields[i].setText(currentUser.getResume().getEmail()); break;
                    case 2: fields[i].setText(currentUser.getResume().getPhone()); break;
                    case 3: fields[i].setText(currentUser.getResume().getAddress()); break;
                    case 4: fields[i].setText(currentUser.getResume().getLinkedIn()); break;
                }
            }
            panel.add(fields[i], gbc);
        }

        gbc.gridx = 0; gbc.gridy = labels.length - 1;
        panel.add(new JLabel(labels[labels.length - 1]), gbc);
        
        gbc.gridx = 1;
        if (currentUser.getResume() != null) {
            summaryArea.setText(currentUser.getResume().getSummary());
        }
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        panel.add(summaryScroll, gbc);

        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        JButton saveButton = new JButton("Save Personal Info");
        saveButton.setBackground(new Color(0, 102, 204));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            if (currentUser.getResume() == null) {
                currentUser.setResume(new Resume());
            }
            Resume resume = currentUser.getResume();
            resume.setFullName(fields[0].getText());
            resume.setEmail(fields[1].getText());
            resume.setPhone(fields[2].getText());
            resume.setAddress(fields[3].getText());
            resume.setLinkedIn(fields[4].getText());
            resume.setSummary(summaryArea.getText());
            saveUsers();
            JOptionPane.showMessageDialog(panel, "Personal information saved!");
        });
        panel.add(saveButton, gbc);

        return panel;
    }

    private static JPanel createEducationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        DefaultListModel<Education> listModel = new DefaultListModel<>();
        JList<Education> educationList = new JList<>(listModel);
        
        if (currentUser.getResume() != null) {
            for (Education edu : currentUser.getResume().getEducation()) {
                listModel.addElement(edu);
            }
        }

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField degreeField = new JTextField();
        JTextField institutionField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField gpaField = new JTextField();

        formPanel.add(new JLabel("Degree:"));
        formPanel.add(degreeField);
        formPanel.add(new JLabel("Institution:"));
        formPanel.add(institutionField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearField);
        formPanel.add(new JLabel("GPA:"));
        formPanel.add(gpaField);

        JButton addButton = new JButton("Add Education");
        addButton.setBackground(new Color(0, 153, 76));
        addButton.setForeground(Color.WHITE);

        JButton removeButton = new JButton("Remove Selected");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> {
            Education edu = new Education(degreeField.getText(), institutionField.getText(), yearField.getText(), gpaField.getText());
            listModel.addElement(edu);
            if (currentUser.getResume() == null) {
                currentUser.setResume(new Resume());
            }
            currentUser.getResume().addEducation(edu);
            saveUsers();
            
            degreeField.setText("");
            institutionField.setText("");
            yearField.setText("");
            gpaField.setText("");
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = educationList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                if (currentUser.getResume() != null) {
                    currentUser.getResume().getEducation().remove(selectedIndex);
                    saveUsers();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(formPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(new JScrollPane(educationList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createExperiencePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        DefaultListModel<Experience> listModel = new DefaultListModel<>();
        JList<Experience> experienceList = new JList<>(listModel);
        
        if (currentUser.getResume() != null) {
            for (Experience exp : currentUser.getResume().getExperience()) {
                listModel.addElement(exp);
            }
        }

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField companyField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField durationField = new JTextField();
        JTextArea descriptionArea = new JTextArea(3, 20);

        formPanel.add(new JLabel("Company:"));
        formPanel.add(companyField);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);
        formPanel.add(new JLabel("Duration:"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));

        JButton addButton = new JButton("Add Experience");
        addButton.setBackground(new Color(0, 153, 76));
        addButton.setForeground(Color.WHITE);

        JButton removeButton = new JButton("Remove Selected");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> {
            Experience exp = new Experience(companyField.getText(), positionField.getText(), 
                                          durationField.getText(), descriptionArea.getText());
            listModel.addElement(exp);
            if (currentUser.getResume() == null) {
                currentUser.setResume(new Resume());
            }
            currentUser.getResume().addExperience(exp);
            saveUsers();
            
            companyField.setText("");
            positionField.setText("");
            durationField.setText("");
            descriptionArea.setText("");
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = experienceList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                if (currentUser.getResume() != null) {
                    currentUser.getResume().getExperience().remove(selectedIndex);
                    saveUsers();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(formPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(new JScrollPane(experienceList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createSkillsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> skillsList = new JList<>(listModel);
        
        if (currentUser.getResume() != null) {
            for (String skill : currentUser.getResume().getSkills()) {
                listModel.addElement(skill);
            }
        }

        JPanel inputPanel = new JPanel();
        JTextField skillField = new JTextField(20);
        JButton addButton = new JButton("Add Skill");
        addButton.setBackground(new Color(0, 153, 76));
        addButton.setForeground(Color.WHITE);

        JButton removeButton = new JButton("Remove Selected");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> {
            String skill = skillField.getText().trim();
            if (!skill.isEmpty()) {
                listModel.addElement(skill);
                if (currentUser.getResume() == null) {
                    currentUser.setResume(new Resume());
                }
                currentUser.getResume().addSkill(skill);
                saveUsers();
                skillField.setText("");
            }
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = skillsList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                if (currentUser.getResume() != null) {
                    currentUser.getResume().getSkills().remove(selectedIndex);
                    saveUsers();
                }
            }
        });

        inputPanel.add(new JLabel("Skill:"));
        inputPanel.add(skillField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        panel.add(new JScrollPane(skillsList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createPreviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTextArea previewArea = new JTextArea();
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        previewArea.setEditable(false);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton generatePreviewButton = new JButton("Generate Preview");
        generatePreviewButton.setBackground(new Color(0, 102, 204));
        generatePreviewButton.setForeground(Color.WHITE);

        JButton findJobsButton = new JButton("Find Suitable Jobs");
        findJobsButton.setBackground(new Color(153, 0, 153));
        findJobsButton.setForeground(Color.WHITE);

        JButton exportPdfButton = new JButton("Export to PDF");
        exportPdfButton.setBackground(new Color(220, 20, 60));
        exportPdfButton.setForeground(Color.WHITE);

        generatePreviewButton.addActionListener(e -> {
            if (currentUser.getResume() != null) {
                previewArea.setText(currentUser.getResume().generateResumeText());
            } else {
                previewArea.setText("No resume data available. Please fill in the forms.");
            }
        });

        findJobsButton.addActionListener(e -> {
            if (currentUser.getResume() != null) {
                String jobSuggestions = currentUser.getResume().suggestJobs();
                JOptionPane.showMessageDialog(panel, jobSuggestions, "Job Suggestions", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Please create your resume first!");
            }
        });

        exportPdfButton.addActionListener(e -> {
            if (currentUser.getResume() != null) {
                exportToPDF();
            } else {
                JOptionPane.showMessageDialog(panel, "Please create your resume first!");
            }
        });

        buttonPanel.add(generatePreviewButton);
        buttonPanel.add(findJobsButton);
        buttonPanel.add(exportPdfButton);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(previewArea), BorderLayout.CENTER);

        return panel;
    }

    private static void exportToPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF As");
        fileChooser.setSelectedFile(new File(currentUser.getResume().getFullName() + "_Resume.pdf"));
        
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }
            
            try {
                PDFExporter.exportResumeToPDF(currentUser.getResume(), fileToSave.getAbsolutePath());
                JOptionPane.showMessageDialog(null, 
                    "Resume successfully exported to PDF!\nLocation: " + fileToSave.getAbsolutePath(),
                    "PDF Export Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error exporting to PDF: " + ex.getMessage(),
                    "Export Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (HashMap<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            // First run, no data file exists
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}