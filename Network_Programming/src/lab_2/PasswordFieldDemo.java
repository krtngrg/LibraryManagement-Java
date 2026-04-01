package lab_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class PasswordFieldDemo {

    public static void main(String[] args) {
        JFrame frame = new JFrame("JPasswordField Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter Password:");
        JPasswordField pf = new JPasswordField(10);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] pwd = pf.getPassword();
                System.out.println("Password entered: " + new String(pwd));
                Arrays.fill(pwd, '\0');
                pf.setText("");
            }
        });

        frame.add(label);
        frame.add(pf);
        frame.add(submitButton);
        frame.setVisible(true);
    }
}