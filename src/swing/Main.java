package swing;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("FHIR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(600,600);

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        final JTextField patientID = new JTextField();
        patientID.setBounds(200,200,200,30);
        button1.setBounds(50,100,95,30);
        button2.setBounds(100,100,95,30);

        frame.add(button1);
        frame.add(button2);
        frame.add(patientID);
        frame.setVisible(true);
    }

}
