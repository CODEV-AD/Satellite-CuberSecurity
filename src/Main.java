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
     private static final String JDBC_URL = "jdbc:mysql://localhost:3306/satellitedata";
     private static final String DB_USERNAME = "root";
     private static final String DB_PASSWORD = "1234";

     public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
             JFrame frame = new JFrame("Satellite CyberAttack");
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setSize(700, 300);

             try {
                 // Load the background image
                 Image backgroundImage = ImageIO.read(new File("C:\\Users\\adars\\Satellite\\data\\test1.jpg"));
                 BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);

                 // Create and add components
                 JTextField satelliteNameField = new JTextField(20);
                 JButton submitButton = new JButton("Submit CyberAttack Query");
                 JButton addDataButton = new JButton("Add Satellite Data");

                 // Create a JComboBox for selecting satellite type
                 String[] satelliteTypes = {"Lower Earth Orbit", "Middle Earth Orbit", "Higher Earth Orbit"};
                 JComboBox<String> satelliteTypeComboBox = new JComboBox<>(satelliteTypes);

                 backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

                 // Create JLabels for "Satellite Name" and "Satellite Type"
                 JLabel nameLabel = new JLabel("Satellite Name: ");
                 nameLabel.setForeground(Color.WHITE);
                 JLabel typeLabel = new JLabel("Satellite Type: ");
                 typeLabel.setForeground(Color.WHITE);

                 backgroundPanel.add(nameLabel);
                 backgroundPanel.add(satelliteNameField);
                 backgroundPanel.add(typeLabel);
                 backgroundPanel.add(satelliteTypeComboBox);
                 backgroundPanel.add(submitButton);
                 backgroundPanel.add(addDataButton);

                 frame.add(backgroundPanel);
                 frame.setVisible(true);

                 // Add action listener for the submit button
                 submitButton.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                         String satelliteName = satelliteNameField.getText();
                         String satelliteType = (String) satelliteTypeComboBox.getSelectedItem();
                         String cyberAttackInfo = getCyberAttackInfo(satelliteName, satelliteType);
                         JOptionPane.showMessageDialog(frame, cyberAttackInfo);
                     }
                 });

                 // Add action listener for the add data button
                 addDataButton.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                         openAddDataForm();
                     }
                 });

             } catch (IOException e) {
                 e.printStackTrace();
             }
         });
     }

     private static void openAddDataForm() {
         JFrame addDataFrame = new JFrame("Add Satellite Data");
         addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         addDataFrame.setSize(400, 250);

         // Load the background image
         Image backgroundImage;
         try {
             backgroundImage = ImageIO.read(new File("C:\\Users\\adars\\Satellite\\data\\test1.jpg"));
         } catch (IOException e) {
             e.printStackTrace();
             return; // If there's an issue loading the image, exit the method
         }

         BackgroundPanel addDataBackgroundPanel = new BackgroundPanel(backgroundImage);
         JPanel addDataPanel = new JPanel(new GridLayout(6, 2, 10, 10));

         JTextField satelliteNameField = new JTextField(20);
         JComboBox<String> orbitTypeComboBox = new JComboBox<>(new String[]{"Lower Earth Orbit", "Middle Earth Orbit", "Higher Earth Orbit"});
         JTextField servicesProvidedField = new JTextField(20);
         JTextField cyberAttackTypesField = new JTextField(20);
         JTextField ownerCountryField = new JTextField(20);
         JButton submitDataButton = new JButton("Submit Data");

         JLabel nameLabel = new JLabel("Satellite Name: ");
         JLabel orbitLabel = new JLabel("Orbit Type: ");
         JLabel servicesLabel = new JLabel("Services Provided: ");
         JLabel cyberAttackLabel = new JLabel("Cyber Attack Types: ");
         JLabel ownerCountryLabel = new JLabel("Owner Country: ");

         addDataPanel.add(nameLabel);
         addDataPanel.add(satelliteNameField);
         addDataPanel.add(orbitLabel);
         addDataPanel.add(orbitTypeComboBox);
         addDataPanel.add(servicesLabel);
         addDataPanel.add(servicesProvidedField);
         addDataPanel.add(cyberAttackLabel);
         addDataPanel.add(cyberAttackTypesField);
         addDataPanel.add(ownerCountryLabel);
         addDataPanel.add(ownerCountryField);
         addDataPanel.add(new JLabel()); // empty label for spacing
         addDataPanel.add(submitDataButton);

         addDataBackgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
         addDataBackgroundPanel.add(addDataPanel);

         addDataFrame.add(addDataBackgroundPanel);
         addDataFrame.setVisible(true);

         // Add action listener for the submit data button
         submitDataButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 String satelliteName = satelliteNameField.getText();
                 String orbitType = (String) orbitTypeComboBox.getSelectedItem();
                 String servicesProvided = servicesProvidedField.getText();
                 String cyberAttackTypes = cyberAttackTypesField.getText();
                 String ownerCountry = ownerCountryField.getText();

                 // Add the data to the database
                 addSatelliteDataToDatabase(satelliteName, orbitType, servicesProvided, cyberAttackTypes, ownerCountry);

                 // Show a confirmation message
                 JOptionPane.showMessageDialog(addDataFrame, "Your data is submitted.");

                 // Close the add data form
                 addDataFrame.dispose();
             }
         });
     }

     // ... (existing code)

     private static void addSatelliteDataToDatabase(String satelliteName, String orbitType, String servicesProvided, String cyberAttackTypes, String ownerCountry) {
         try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD)) {
             // Determine the correct table based on orbitType
             String tableName;
             switch (orbitType) {
                 case "Lower Earth Orbit":
                     tableName = "lowerearthorbitsatellites";
                     break;
                 case "Middle Earth Orbit":
                     tableName = "middleearthorbitsatellites";
                     break;
                 case "Higher Earth Orbit":
                     tableName = "higherearthorbitsatellites";
                     break;
                 default:
                     throw new IllegalArgumentException("Invalid orbit type");
             }

             // Insert data into the respective table
             String query = "INSERT INTO " + tableName + " (SatelliteName, ServicesProvided, CyberAttackTypes, OwnerCountry) VALUES (?, ?, ?, ?)";

             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                 preparedStatement.setString(1, satelliteName);
                 preparedStatement.setString(2, servicesProvided);
                 preparedStatement.setString(3, cyberAttackTypes);
                 preparedStatement.setString(4, ownerCountry);

                 int rowsAffected = preparedStatement.executeUpdate();

                 if (rowsAffected > 0) {
                     JOptionPane.showMessageDialog(null, "Data stored successfully in the database.");
                 } else {
                     JOptionPane.showMessageDialog(null, "Failed to store data in the database.");
                 }
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(null, "Error occurred while storing data in the database.");
         }
     }


// ... (existing code)

     private static String getTableName(String orbitType) {
         switch (orbitType) {
             case "Lower Earth Orbit":
                 return "lowerearthorbitsatellites";
             case "Middle Earth Orbit":
                 return "middleeartorbitsatellites";
             case "Higher Earth Orbit":
                 return "highereartorbitsatellites";
             default:
                 return null;
         }
     }


     private static String getCyberAttackInfo(String satelliteName, String satelliteType) {
         String jdbcUrl = "jdbc:mysql://localhost:3306/satellitedata";
         String dbUsername = "root";
         String dbPassword = "1234";

         try {
             Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             String query = "SELECT ServicesProvided, CyberAttackTypes, OwnerCountry FROM ";

             if ("Lower Earth Orbit".equals(satelliteType)) {
                 query += "lowerearthorbitsatellites";
             } else if ("Middle Earth Orbit".equals(satelliteType)) {
                 query += "MiddleEarthOrbitSatellites";
             } else if ("Higher Earth Orbit".equals(satelliteType)) {
                 query += "higherearthorbitsatellites";
             }

             query += " WHERE SatelliteName = ?";

             PreparedStatement preparedStatement = connection.prepareStatement(query);
             preparedStatement.setString(1, satelliteName);

             ResultSet resultSet = preparedStatement.executeQuery();

             if (resultSet.next()) {
                 String servicesProvided = resultSet.getString("ServicesProvided");
                 String cyberAttackTypes = resultSet.getString("CyberAttackTypes");
                 String ownerCountry = resultSet.getString("OwnerCountry");

                 return String.format("Services Provided: %s%nCyberAttack Types: %s%nOwner Country: %s",
                         servicesProvided, cyberAttackTypes, ownerCountry);
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
