import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class SatelliteNameGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Satellite CyberAttack");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);

            try {
                // Load the background image
                Image backgroundImage = ImageIO.read(new File("C:\\Users\\adars\\Satellite\\data\\test1.jpg"));
                BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);

                // Create and add components
                JTextField satelliteNameField = new JTextField(20);
                JButton submitButton = new JButton("Submit");

                // Create a JComboBox for selecting satellite type
                String[] satelliteTypes = {"Lower Earth Orbit", "Middle Earth Orbit", "Higher Earth Orbit"};
                JComboBox<String> satelliteTypeComboBox = new JComboBox<>(satelliteTypes);

                backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

                // Create JLabels for "Satellite Name" and "Satellite Type" and set their foreground color to white
                JLabel nameLabel = new JLabel("Satellite Name: ");
                nameLabel.setForeground(Color.WHITE);
                JLabel typeLabel = new JLabel("Satellite Type: ");
                typeLabel.setForeground(Color.WHITE);

                backgroundPanel.add(nameLabel);
                backgroundPanel.add(satelliteNameField);
                backgroundPanel.add(typeLabel);
                backgroundPanel.add(satelliteTypeComboBox);
                backgroundPanel.add(submitButton);

                frame.add(backgroundPanel);
                frame.setVisible(true);

                // Add action listener for the submit button
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String satelliteName = satelliteNameField.getText();
                        String satelliteType = (String) satelliteTypeComboBox.getSelectedItem();

                        // Retrieve cyberattack information from the database based on name and type
                        String cyberAttackInfo = getCyberAttackInfo(satelliteName, satelliteType);

                        // Display the information in a dialog
                        JOptionPane.showMessageDialog(frame, cyberAttackInfo);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Function to retrieve cyberattack information from the database
    private static String getCyberAttackInfo(String satelliteName, String satelliteType) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/satellitedata";
        String dbUsername = "root";
        String dbPassword = "1234";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            String query = "SELECT CyberAttackTypes FROM ";

            if ("Lower Earth Orbit".equals(satelliteType)) {
                query += "lowerearthorbitsatellites";
            } else if ("Middle Earth Orbit".equals(satelliteType)) {
                query += "MiddleEarthOrbitSatellites";
            } else if ("Higher Earth Orbit".equals(satelliteType)) {
                query += "higherearthorbitsatellites"; // Replace with the actual table name
            }

            query += " WHERE SatelliteName = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, satelliteName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("CyberAttackTypes");
            } else {
                return "No information found for the specified satellite.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: " + e.getMessage());
            return "Error occurred while fetching data from the database.";
        }
    }
}

class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
