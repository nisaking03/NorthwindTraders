package com.pluralsight;

import java.sql.*;

public class Main {

    public static String username = "root";
    public static String password = "yearup";
    public static String dataBase = "northwind";
    public static String URL = "jdbc:mysql://localhost:3306/" + dataBase;

    public static void main(String[] args) {

        System.out.println("""
                ╭ ────────────── • ──────────── ╮
                  Welcome To NorthWind Traders!
                ╰ ────────────── • ──────────── ╯
                """);


        String mainMenu = """
                 1) Display all products
                 2) Display all customers
                 3) Display all categories
                 0) Exit
                """;

        while (true) {
            System.out.print(mainMenu);
            int command = ConsoleHelper.promptForInt("Enter here"); //prompt for menu

            switch (command) {
                case 1 -> displayAllProducts();
                case 2 -> displayAllCustomers();
                case 3 -> displayAllCategories();
                case 0 -> {
                    System.out.println("Goodbye!");
                    System.exit(0); // Force closes program
                }
                default -> System.out.println("Please insert a valid number!"); //Error message
            }
        }
    }

    private void displayAllProducts(){
        String query1 = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        Connection connection;
        connection = DriverManager.getConnection(
                URL,
                username,
                password);

        // Create statement
        // The statement is tied to the open connection
        Statement statement = connection.createStatement();

        ResultSet results1 = statement.executeQuery(query1);

        while (results1.next()) {
            int productID = results1.getInt("ProductID");
            String productName = results1.getString("ProductName");
            double unitPrice = results1.getDouble("UnitPrice");
            int unitsInStock = results1.getInt("UnitsInStock");

            System.out.printf("%-10d %-30s %-10.2f %-10d\n", productID, productName, unitPrice, unitsInStock);
        }
        results1.close();
        statement.close();
        connection.close();
    }

    private void displayAllCustomers(){
        String query2 = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers ORDER BY Country";

        Connection connection;
        connection = DriverManager.getConnection(
                URL,
                username,
                password);

        // Create statement
        // The statement is tied to the open connection
        Statement statement = connection.createStatement();

        ResultSet results2 = statement.executeQuery(query2);

        while (results2.next()) {
            String contactName = results2.getString("ContactName");
            String companyName = results2.getString("CompanyName");
            String city = results2.getString("City");
            String country = results2.getString("Country");
            String phone = results2.getString("Phone");

            System.out.printf("%-10s %-30s %-10.2s %-10s %-10s\n", contactName, companyName, city, country, phone);
        }
        results2.close();
        statement.close();
        connection.close();
    }

    private void displayAllCategories(){
        String query3 = "SELECT CategoryID, CategoryName  FROM categories ORDER BY CategoryID";

        Connection connection;
        connection = DriverManager.getConnection(
                URL,
                username,
                password);

        // Create statement
        // The statement is tied to the open connection
        Statement statement = connection.createStatement();

        ResultSet results3 = statement.executeQuery(query3);

        while (results3.next()) {
            int categoryID = results3.getInt("CategoryID");
            String categoryName = results3.getString("CategoryName");

            System.out.printf("%-10d %-30s\n", categoryID, categoryName);
        }
        results3.close();
        statement.close();
        connection.close();
    }
}
// In your pom.xml file, add a dependency to the MySQL Driver.
// <dependencies>
//        <dependency>
//            <groupId>mysql</groupId>
//            <artifactId>mysql-connector-java</artifactId>
//            <version>8.0.33</version>
//        </dependency>
//    </dependencies>


// In the main method connect to the Northwind database.
// (jdbc:mysql://localhost:3306/northwind)
// Execute a query to select all products.
// Display the name of each product sold by Northwind to the screen.