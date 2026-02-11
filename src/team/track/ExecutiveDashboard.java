package team.track;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ExecutiveDashboard extends JFrame {

    String username;
    JTable table;
    DefaultTableModel model;
    Connection conn;
    JLabel birthdayLabel;
    Timer flashTimer;

    public ExecutiveDashboard() {
        this.username = username;

        setTitle("Executive Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        connectDatabase();
        initUI();

        JPanel topPanel = new JPanel();
        JButton btnViewBirthdays = new JButton("View Birthdays");
        JButton btnViewEmployees = new JButton("View Employees");
        JButton btnSearch = new JButton("Search");
        JButton btnChangePassword = new JButton("Change Password");
        JButton btnLogout = new JButton("Logout");

        topPanel.add(btnViewBirthdays);
        topPanel.add(btnViewEmployees);
        topPanel.add(btnSearch);
        topPanel.add(btnChangePassword);
        topPanel.add(btnLogout);
        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);



        btnViewBirthdays.addActionListener(e -> loadBirthdays());
        btnViewEmployees.addActionListener(e -> loadEmployees());
        btnSearch.addActionListener(e -> searchEmployee());
        btnChangePassword.addActionListener(e -> changePassword());
        btnLogout.addActionListener(e -> {
            dispose();
            new login().setVisible(true); // Assuming Login is your login JFrame class
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

    private void loadBirthdays() {
        model.setColumnIdentifiers(new String[]{"Name", "DOB"});
        model.setRowCount(0);
        try {
            String today = LocalDate.now().toString().substring(5);
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT name, dob FROM Employee WHERE DATE_FORMAT(dob, '%m-%d') = ?")) {
                stmt.setString(1, today);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getString("name"),
                                rs.getDate("dob")
                        });
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployees() {
        model.setColumnIdentifiers(new String[]{"Username", "Name", "Role", "DOB"});
        model.setRowCount(0);
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Employee")) {
            while (rs.next()) {
                String username = rs.getString("username");
                if (username != null) {
                    model.addRow(new Object[]{
                            username,
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

    private void searchEmployee() {
        String name = JOptionPane.showInputDialog(this, "Enter name to search:");
        if (name == null || name.trim().isEmpty()) return;

        model.setColumnIdentifiers(new String[]{"Username", "Name", "Role", "DOB"});
        model.setRowCount(0);

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Employee WHERE name LIKE ?")) {
            stmt.setString(1, "%" + name + "%");
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

    public static java.sql.Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date utilDate = sdf.parse(dateStr);
            return new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
