package team.track;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Random;

public class Add_Employee extends JFrame implements ActionListener {
    Random ran = new Random();
    int number = ran.nextInt(999999);
    JTextField tname, tfname, taddress, tphone, taadhar, temail, tsalary, tdesignation, tusername;
    JLabel tempid;
    JDateChooser tdob;
    JComboBox<String> roleBox, Boxeducation;
    JButton add, back;

    Add_Employee() {
        getContentPane().setBackground(new Color(163, 255, 188));
        setLayout(null);

        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("serif", Font.BOLD, 25));
        add(heading);

        // Name
        JLabel name = new JLabel("Name");
        name.setBounds(50, 150, 150, 30);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(name);

        tname = new JTextField();
        tname.setBounds(200, 150, 150, 30);
        tname.setBackground(new Color(177, 252, 197));
        add(tname);

        // Father's Name
        JLabel fname = new JLabel("Father's Name");
        fname.setBounds(400, 150, 150, 30);
        fname.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(fname);

        tfname = new JTextField();
        tfname.setBounds(600, 150, 150, 30);
        tfname.setBackground(new Color(177, 252, 197));
        add(tfname);

        // DOB
        JLabel dob = new JLabel("DOB (yyyy-MM-dd):");
        dob.setBounds(50, 200, 200, 30);
        dob.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(dob);

        tdob = new JDateChooser();
        tdob.setDateFormatString("yyyy-MM-dd"); // Fixed line
        tdob.setBounds(200, 200, 150, 30);
        tdob.setBackground(new Color(177, 252, 197));
        add(tdob);

        // Salary
        JLabel salary = new JLabel("Salary");
        salary.setBounds(400, 200, 150, 30);
        salary.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(salary);

        tsalary = new JTextField();
        tsalary.setBounds(600, 200, 150, 30);
        tsalary.setBackground(new Color(177, 252, 197));
        add(tsalary);

        // Address
        JLabel address = new JLabel("Address");
        address.setBounds(50, 250, 150, 30);
        address.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(address);

        taddress = new JTextField();
        taddress.setBounds(200, 250, 150, 30);
        taddress.setBackground(new Color(177, 252, 197));
        add(taddress);

        // Phone
        JLabel phone = new JLabel("Phone");
        phone.setBounds(400, 250, 150, 30);
        phone.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(phone);

        tphone = new JTextField();
        tphone.setBounds(600, 250, 150, 30);
        tphone.setBackground(new Color(177, 252, 197));
        add(tphone);

        // Email
        JLabel email = new JLabel("Email");
        email.setBounds(50, 300, 150, 30);
        email.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(email);

        temail = new JTextField();
        temail.setBounds(200, 300, 150, 30);
        temail.setBackground(new Color(177, 252, 197));
        add(temail);

        // Education
        JLabel education = new JLabel("Highest Education");
        education.setBounds(400, 300, 200, 30);
        education.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(education);

        String[] items = {"BBA", "B.Tech", "BCA", "BA", "BSC", "B.COM", "MBA", "MCA", "MA", "MTech", "MSC", "PHD"};
        Boxeducation = new JComboBox<>(items);
        Boxeducation.setBackground(new Color(177, 252, 197));
        Boxeducation.setBounds(600, 300, 150, 30);
        add(Boxeducation);

        // Designation
        JLabel designation = new JLabel("Designation");
        designation.setBounds(50, 350, 150, 30);
        designation.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(designation);

        tdesignation = new JTextField();
        tdesignation.setBounds(200, 350, 150, 30);
        tdesignation.setBackground(new Color(177, 252, 197));
        add(tdesignation);

        // Aadhar
        JLabel aadhar = new JLabel("Aadhar Number");
        aadhar.setBounds(400, 350, 150, 30);
        aadhar.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(aadhar);

        taadhar = new JTextField();
        taadhar.setBounds(600, 350, 150, 30);
        taadhar.setBackground(new Color(177, 252, 197));
        add(taadhar);

        // Emp ID
        JLabel empid = new JLabel("Employee ID");
        empid.setBounds(50, 400, 150, 30);
        empid.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(empid);

        tempid = new JLabel("" + number);
        tempid.setBounds(200, 400, 150, 30);
        tempid.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        tempid.setForeground(Color.RED);
        add(tempid);

        // Role
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setBounds(400, 400, 150, 30);
        roleLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(roleLabel);

        String[] roles = {"Executive", "Non-Executive"};
        roleBox = new JComboBox<>(roles);
        roleBox.setBounds(600, 400, 150, 30);
        roleBox.setBackground(new Color(177, 252, 197));
        add(roleBox);

        // Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(50, 450, 150, 30);
        usernameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(usernameLabel);

        tusername = new JTextField();
        tusername.setBounds(200, 450, 150, 30);
        tusername.setBackground(new Color(177, 252, 197));
        add(tusername);

        // Buttons
        add = new JButton("ADD");
        add.setBounds(450, 550, 150, 40);
        add.setBackground(Color.black);
        add.setForeground(Color.WHITE);
        add.addActionListener(this);
        add(add);

        back = new JButton("BACK");
        back.setBounds(250, 550, 150, 40);
        back.setBackground(Color.black);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            if (tdob.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Please select a valid Date of Birth");
                return;
            }

            String name = tname.getText();
            String Fname = tfname.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String DOB = sdf.format(tdob.getDate());
            String Salary = tsalary.getText();
            String Address = taddress.getText();
            String Aadhaar = taadhar.getText();
            String Phone = tphone.getText();
            String Email = temail.getText();
            String Education = (String) Boxeducation.getSelectedItem();
            String Designation = tdesignation.getText();
            String EmpID = tempid.getText();
            String role = (String) roleBox.getSelectedItem();
            String username = tusername.getText();
            String defaultPassword = "123456";

            try {
                conn c = new conn();
                String query = "INSERT INTO Employee (name, Fname, DOB, Salary, Address, Phone, Email, Education, Designation, Aadhaar, EmpId, username, password, role) " +
                        "VALUES ('" + name + "', '" + Fname + "', '" + DOB + "', '" + Salary + "', '" + Address + "', '" + Phone + "', '" + Email + "', '" + Education + "', '" + Designation + "', '" + Aadhaar + "', '" + EmpID + "', '" + username + "', '" + defaultPassword + "', '" + role + "')";


                String loginQuery = "INSERT INTO login VALUES('" + username + "', '" + defaultPassword + "', '" + role + "')";

                c.statement.executeUpdate(query);
                c.statement.executeUpdate(loginQuery);

                JOptionPane.showMessageDialog(null, "Employee and Login added successfully.\nDefault Password: 123456");
                setVisible(false);
                new Main_class();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else {
            setVisible(false);
            new Main_class();
        }
    }

    public static void main(String[] args) {
        new Add_Employee();
    }
}

