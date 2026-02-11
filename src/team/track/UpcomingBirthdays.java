package team.track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class UpcomingBirthdays extends JFrame implements ActionListener {

    JTable table;
    JButton back;

    UpcomingBirthdays() {
        setTitle("Upcoming Birthdays");
        setLayout(new BorderLayout());

        table = new JTable();
        try {
            conn c = new conn();

            String query = "SELECT EmpId, name AS Name, DOB AS Birthday " +
                    "FROM Employee " +
                    "WHERE (" +
                    "  DATE_FORMAT(DOB, '%m-%d') >= DATE_FORMAT(CURDATE(), '%m-%d') " +
                    "  AND DATE_FORMAT(DOB, '%m-%d') <= DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 7 DAY), '%m-%d')" +
                    ") " +
                    "OR (" +
                    "  DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 7 DAY), '%m-%d') < DATE_FORMAT(CURDATE(), '%m-%d') " +
                    "  AND (" +
                    "    DATE_FORMAT(DOB, '%m-%d') >= DATE_FORMAT(CURDATE(), '%m-%d') " +
                    "    OR DATE_FORMAT(DOB, '%m-%d') <= DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 7 DAY), '%m-%d')" +
                    "  )" +
                    ") " +
                    "ORDER BY MONTH(DOB), DAY(DOB)";

            ResultSet rs = c.statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        back = new JButton("Back");
        back.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(back);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpcomingBirthdays();
    }
}
