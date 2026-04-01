package lab_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class WhoisLookupGUI extends JFrame implements ActionListener {

    private JTextField domainField;
    private JTextArea resultArea;
    private JButton lookupButton;
    public WhoisLookupGUI() {

        setTitle("Simple WHOIS Lookup Tool");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        domainField = new JTextField(25);
        lookupButton = new JButton("Lookup");

        topPanel.add(new JLabel("Enter Domain:"));
        topPanel.add(domainField);
        topPanel.add(lookupButton);

        add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(scrollPane, BorderLayout.CENTER);

        lookupButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String domain = domainField.getText().trim();
        resultArea.setText("");

        if (domain.isEmpty()) {
            resultArea.setText("Please enter a domain name.");
            return;
        }

        try {
            Socket socket = new Socket("whois.internic.net", 43);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            writer.write(domain + "\r\n");
            writer.flush();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                resultArea.append(line + "\n");
            }
            socket.close();

        } catch (IOException ex) {
            resultArea.setText("Error: Unable to perform WHOIS lookup.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WhoisLookupGUI gui = new WhoisLookupGUI();
            gui.setVisible(true);
        });
    }
}
