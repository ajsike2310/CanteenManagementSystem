<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CafeOrd - Canteen Management System</title>
</head>
<body>

<h1>☕ CafeOrd - Canteen Management System 🍽️</h1>
<p>A Java desktop application for managing canteen orders. CafeOrd provides a modern, user-friendly interface for placing orders, logging in securely, and viewing detailed order summaries. The system uses Java Swing for the interface and MySQL for database storage.</p>

<h2>📋 Features</h2>
<ul>
  <li><strong>Login Interface</strong>: Secure login system for users, with stored credentials in MySQL.</li>
  <li><strong>Interactive Menu</strong>: Select items, adjust quantities, and see price calculations in real-time.</li>
  <li><strong>Order Summary</strong>: View selected items with total cost, shown before finalizing the order.</li>
  <li><strong>Database Integration</strong>: Users, orders, and items are stored in a MySQL database for persistent data handling.</li>
</ul>

<h2>⚙️ Prerequisites</h2>
<p>Before running the application, make sure you have:</p>
<ul>
  <li><strong>Java Development Kit (JDK)</strong> version 8 or higher.</li>
  <li><strong>MySQL Database</strong> installed and configured with the canteen schema.</li>
  <li><strong>MySQL Connector</strong> for Java to establish database connections.</li>
</ul>

<h2>📦 Setup Instructions</h2>

<h3>Step 1: Clone the Repository</h3>
<pre><code>git clone https://github.com/your-username/CafeOrd.git
cd CafeOrd
</code></pre>

<h3>Step 2: Set Up the Database</h3>
<ol>
  <li>Launch MySQL and create a new database for the project.</li>
  <li>Run the following commands to create the required tables:</li>
</ol>
<pre><code>CREATE DATABASE canteen_db;
USE canteen_db;

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50)
);

CREATE TABLE items (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    price DECIMAL(5, 2)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    order_details TEXT,
    total_amount DECIMAL(10, 2)
);
</code></pre>

<h3>Step 3: Configure Database Connection in Code</h3>
<ol>
  <li>Open <code>CanteenManagementSystem.java</code>.</li>
  <li>Update database credentials in the <code>connectDatabase()</code> method:</li>
</ol>
<pre><code>String url = "jdbc:mysql://localhost:3306/canteen_db";
String user = "root";
String password = "your_password";
</code></pre>

<h3>Step 4: Compile and Run the Application</h3>
<pre><code>javac CanteenManagementSystem.java
java CanteenManagementSystem
</code></pre>

<h2>🎮 Usage</h2>
<ol>
  <li><strong>Login</strong>: Enter username and password to access the system.</li>
  <li><strong>Browse Menu</strong>: Select items, adjust quantities, and see calculated costs.</li>
  <li><strong>Place Order</strong>: Review your order and submit for processing.</li>
</ol>

<h2>📁 Project Structure</h2>
<pre><code>CafeOrd/
├── src/
│   └── CanteenManagementSystem.java
└── README.md
</code></pre>

<h2>📚 Dependencies</h2>
<ul>
  <li><strong>Java Swing</strong> for user interface design.</li>
  <li><strong>MySQL Connector</strong> to connect Java to the MySQL database.</li>
</ul>

<h2>🚀 Future Enhancements</h2>
<ul>
  <li><strong>Role-based Access</strong>: Separate views for customers and staff.</li>
  <li><strong>Order History</strong>: Save past orders and allow users to view them.</li>
  <li><strong>Enhanced UI</strong>: Add more customization and theme options.</li>
</ul>

</body>
</html>
