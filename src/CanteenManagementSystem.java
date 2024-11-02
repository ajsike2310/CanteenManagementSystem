import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CanteenManagementSystem extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private Connection connection;
    private Map<String, String> validUsers;

    public CanteenManagementSystem() {
        connectDatabase();
        insertUsers();
        loadValidUsers();

        setTitle("CafeOrd - Login");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupLoginUI();

        setVisible(true);
    }

    private void setupLoginUI() {
        // Main panel with BorderLayout to center the form
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Form panel with GridBagLayout for alignment
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("Sign in to Canteen Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username or Email");
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        gbc.gridy = 2;
        formPanel.add(usernameField, gbc);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        gbc.gridy = 3;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        gbc.gridy = 4;
        formPanel.add(passwordField, gbc);

        // Sign In button
        JButton loginButton = new JButton("Sign In");
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> validateLogin());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void connectDatabase() {
        String url = "jdbc:mysql://localhost:3306/"; // No specific database yet
        String user = "your username";
        String password = "your password";

        try (Connection initialConnection = DriverManager.getConnection(url, user, password);
             PreparedStatement dropDbStmt = initialConnection.prepareStatement("DROP DATABASE IF EXISTS canteen_db");
             PreparedStatement createDbStmt = initialConnection.prepareStatement("CREATE DATABASE canteen_db")) {

            // Drop the database if it exists
            dropDbStmt.executeUpdate();
            System.out.println("Database dropped successfully (if it existed).");

            // Create the database
            createDbStmt.executeUpdate();
            System.out.println("Database created successfully.");

            // Reconnect to the newly created database
            connection = DriverManager.getConnection(url + "canteen_db", user, password);
            System.out.println("Connected to the new database successfully!");

            // Create the users table
            String createTableQuery = """
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(100) UNIQUE,
                    password VARCHAR(100)
                )
            """;
            try (PreparedStatement createTableStmt = connection.prepareStatement(createTableQuery)) {
                createTableStmt.executeUpdate();
                System.out.println("Table 'users' created successfully.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertUsers() {
        String insertQuery = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setInt(1, 1);
            stmt.setString(2, "user@example.com");
            stmt.setString(3, "password123");
            stmt.executeUpdate();
            stmt.setInt(1, 2);
            stmt.setString(2, "admin@canteen.com");
            stmt.setString(3, "adminpass");
            stmt.executeUpdate();
            stmt.setInt(1, 3);
            stmt.setString(2, "customer1@canteen.com");
            stmt.setString(3, "cust123");
            stmt.executeUpdate();
            stmt.setInt(1, 4);
            stmt.setString(2, "staff@canteen.com");
            stmt.setString(3, "staffpass");
            stmt.executeUpdate();

            System.out.println("Users inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting users into the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadValidUsers() {
        validUsers = new HashMap<>();
        validUsers.put("user@example.com", "password123");
        validUsers.put("admin@canteen.com", "adminpass");
        validUsers.put("customer1@canteen.com", "cust123");
        validUsers.put("staff@canteen.com", "staffpass");
    }

    private void validateLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (validUsers.containsKey(username) && validUsers.get(username).equals(password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            this.dispose();
            openCanteenManagementSystem();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password");
        }
    }

    private void openCanteenManagementSystem() {
        JFrame frame = new JFrame("Canteen Management System");
        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("CafeOrd - Place Your Order", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 5, 10, 10));

        String[] items = {"Biriyani (₹85)", "Chapathi (₹8)", "Fish (₹80.99)", "Meals (₹90)", "Salad (₹80.49)", "Porota (₹7.99)"};
        double[] prices = {85, 8, 80.99, 90, 80.49, 7.99};
        JCheckBox[] checkBoxes = new JCheckBox[items.length];
        JLabel[] quantityLabels = new JLabel[items.length];
        JButton[] incrementButtons = new JButton[items.length];
        JButton[] decrementButtons = new JButton[items.length];

        for (int i = 0; i < items.length; i++) {
            checkBoxes[i] = new JCheckBox();
            JLabel itemLabel = new JLabel(items[i]);
            quantityLabels[i] = new JLabel("1");
            incrementButtons[i] = new JButton("+");
            decrementButtons[i] = new JButton("-");

            incrementButtons[i].setPreferredSize(new Dimension(40, 30));
            decrementButtons[i].setPreferredSize(new Dimension(40, 30));

            int index = i;
            incrementButtons[i].addActionListener(e -> {
                int currentQuantity = Integer.parseInt(quantityLabels[index].getText());
                quantityLabels[index].setText(String.valueOf(currentQuantity + 1));
            });

            decrementButtons[i].addActionListener(e -> {
                int currentQuantity = Integer.parseInt(quantityLabels[index].getText());
                if (currentQuantity > 1) {
                    quantityLabels[index].setText(String.valueOf(currentQuantity - 1));
                }
            });

            menuPanel.add(checkBoxes[i]);
            menuPanel.add(itemLabel);
            menuPanel.add(decrementButtons[i]);
            menuPanel.add(quantityLabels[i]);
            menuPanel.add(incrementButtons[i]);
        }

        frame.add(menuPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CanteenManagementSystem::new);
    }
}
