package team.track;
import javax.swing.*;
import java.awt.*;
public class splash extends JFrame{
    splash(){

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/TeamTrack.png"));
        Image i2 = i1.getImage().getScaledInstance(1170,650, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,1000,560);
        add(image);


        setSize(1000,560);
        setLocation(200,50);
        setLayout(null);
        setVisible(true);

        try{
            Thread.sleep(2000);
            setVisible(false);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new splash();
    }
}
