package team.track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class EmployeeLogin extends JFrame implements ActionListener {

    JTextField tusername;
    JPasswordField tpassword;
    JButton login, back;

    public EmployeeLogin() {
        getContentPane().setBackground(new Color(198, 255, 221));
        setLayout(null);

        JLabel heading = new JLabel("Employee Login");
        heading.setBounds(250, 30, 400, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        add(heading);

        JLabel username = new JLabel("Username");
        username.setBounds(150, 120, 100, 30);
        add(username);

        tusername = new JTextField();
        tusername.setBounds(250, 120, 150, 30);
        add(tusername);

        JLabel password = new JLabel("Password");
        password.setBounds(150, 170, 100, 30);
        add(password);

        tpassword = new JPasswordField();
        tpassword.setBounds(250, 170, 150, 30);
        add(tpassword);

        login = new JButton("Login");
        login.setBounds(250, 230, 150, 40);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        back = new JButton("Back");
        back.setBounds(100, 230, 120, 40);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(600, 400);
        setLocation(400, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = tusername.getText().trim();
            String password = new String(tpassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both username and password");
                return;
            }

            try {
                conn c = new conn();
                String query = "SELECT role FROM login WHERE username = '" + username + "' AND password = '" + password + "'";
                ResultSet rs = c.statement.executeQuery(query);

                if (rs.next()) {
                    String role = rs.getString("role");

                    if (role != null) {
                        if (role.equalsIgnoreCase("Executive")) {
                            setVisible(false);
                            new ExecutiveDashboard(); // replace with your actual dashboard class
                        } else if (role.equalsIgnoreCase("Non-Executive")) {
                            setVisible(false);
                            new NonExecutiveDashboard(); // replace with your actual dashboard class
                        } else {
                            JOptionPane.showMessageDialog(null, "Unknown role: " + role);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Role not assigned to this user.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
            new Main_class(); // replace with your actual main class
        }
    }

    public static void main(String[] args) {
        new EmployeeLogin();
    }
}
