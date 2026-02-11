package team.track;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class NonExecutiveDashboard extends JFrame {

    String username;
    JTable table;
    DefaultTableModel model;
    Connection conn;
    JLabel birthdayLabel;
    Timer flashTimer;

    public NonExecutiveDashboard() {
        this.username = username;

        setTitle("Non-Executive Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        connectDatabase();
        initUI();

        JPanel topPanel = new JPanel();
        JButton btnViewProfile = new JButton("View My Profile");
        JButton btnSearch = new JButton("Search Myself");
        JButton btnChangePassword = new JButton("Change Password");
        JButton btnLogout = new JButton("Logout");

        topPanel.add(btnViewProfile);
        topPanel.add(btnSearch);
        topPanel.add(btnChangePassword);
        topPanel.add(btnLogout);
        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnViewProfile.addActionListener(e -> loadMyProfile());
        btnSearch.addActionListener(e -> searchMyself());
        btnChangePassword.addActionListener(e -> changePassword());
        btnLogout.addActionListener(e -> {
            dispose();
            new login().setVisible(true);
        });

        setVisible(true);
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeamTrack", "root", "1234");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed.");
            e.printStackTrace();
        }
    }

    private void initUI() {
        birthdayLabel = new JLabel(" ", SwingConstants.CENTER);
        birthdayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        birthdayLabel.setOpaque(true);
        add(birthdayLabel, BorderLayout.SOUTH);
        showUpcomingBirthdays();
    }

    private void showUpcomingBirthdays() {
        String query = "SELECT name, dob FROM Employee " +
                "WHERE DATE_FORMAT(dob, '%m-%d') BETWEEN DATE_FORMAT(CURDATE(), '%m-%d') " +
                "AND DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 7 DAY), '%m-%d')";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder birthdays = new StringBuilder();
            while (rs.next()) {
                String name = rs.getString("name");
                Date dob = rs.getDate("dob");
                String dobStr = (dob != null) ? dob.toString() : "N/A";
                birthdays.append(name).append(" (").append(dobStr).append("), ");
            }

            if (birthdays.length() > 0) {
                birthdays.setLength(birthdays.length() - 2);
                birthdayLabel.setText("\uD83C\uDF89 Upcoming Birthdays: " + birthdays);
                startFlashing();
            } else {
                birthdayLabel.setText("No upcoming birthdays \uD83C\uDF82");
                birthdayLabel.setForeground(Color.BLACK);
                birthdayLabel.setBackground(null);
                if (flashTimer != null) flashTimer.stop();
            }

        } catch (SQLException e) {
            birthdayLabel.setText("Error fetching birthdays");
            e.printStackTrace();
        }
    }

    private void loadMyProfile() {
        model.setColumnIdentifiers(new String[]{"Username", "Name", "Role", "DOB"});
        model.setRowCount(0);
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM Employee WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("username"),
                            rs.getString("name"),
                            rs.getString("role"),
                            rs.getDate("dob")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchMyself() {
        String input = JOptionPane.showInputDialog(this, "Enter your name:");
        if (input == null || input.trim().isEmpty()) return;

        model.setColumnIdentifiers(new String[]{"Username", "Name", "Role", "DOB"});
        model.setRowCount(0);

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM Employee WHERE username = ? AND name LIKE ?")) {
            stmt.setString(1, username);
            stmt.setString(2, "%" + input + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("username"),
                            rs.getString("name"),
                            rs.getString("role"),
                            rs.getDate("dob")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void changePassword() {
        JPasswordField oldPassField = new JPasswordField();
        JPasswordField newPassField = new JPasswordField();
        Object[] fields = {
                "Old Password:", oldPassField,
                "New Password:", newPassField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        String oldPass = new String(oldPassField.getPassword());
        String newPass = new String(newPassField.getPassword());

        try (PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT * FROM Employee WHERE username = ? AND password = ?")) {
            checkStmt.setString(1, username);
            checkStmt.setString(2, oldPass);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(
                            "UPDATE Employee SET password = ? WHERE username = ?")) {
                        updateStmt.setString(1, newPass);
                        updateStmt.setString(2, username);
                        updateStmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Password changed successfully.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect current password.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void startFlashing() {
        if (flashTimer != null && flashTimer.isRunning()) return;

        flashTimer = new Timer(500, new ActionListener() {
            private boolean isRed = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRed) {
                    birthdayLabel.setBackground(Color.YELLOW);
                    birthdayLabel.setForeground(Color.RED);
                } else {
                    birthdayLabel.setBackground(Color.RED);
                    birthdayLabel.setForeground(Color.WHITE);
                }
                isRed = !isRed;
            }
        });
        flashTimer.start();
    }
}
