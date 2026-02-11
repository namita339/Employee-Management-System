package team.track;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class Main_class extends JFrame {
    Main_class(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 1120, 630);
        add(img);
        JLabel heading = new JLabel("Team Track");
        heading.setBounds(490, 140, 200, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        img.add(heading);
        JButton add = new JButton("Add Employee");
        add.setBounds(390, 220, 160, 40);
        styleButton(add);
        add.addActionListener(e -> {
            new Add_Employee();
            setVisible(false);
        });
        img.add(add);
        JButton view = new JButton("View Employee");
        view.setBounds(580, 220, 160, 40);
        styleButton(view);
        view.addActionListener(e -> {
            new ViewEmployees();
            setVisible(false);
        });
        img.add(view);
        JButton rem = new JButton("Remove Employee");
        rem.setBounds(390, 290, 160, 40);
        styleButton(rem);
        rem.addActionListener(e -> {
            new RemoveEmployee();
        });
        img.add(rem);
        JButton upcoming = new JButton("Upcoming Birthdays");
        upcoming.setBounds(580, 290, 160, 40);
        styleButton(upcoming);
        upcoming.addActionListener(e -> {
            new UpcomingBirthdays();
            setVisible(false);
        });
        img.add(upcoming);
        setSize(1120, 630);
        setLocation(250, 100);
        setLayout(null);
        setUndecorated(false); // if needed
        setVisible(true);
    }
    private void styleButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
    }

    public static void main(String[] args) {
        new Main_class();
    }
}


