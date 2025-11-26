package com.pluralsight;

import java.sql.*;

public class Main {

    // This is finalizing information needed to get into the specific SQL database
    public static String username = "root";
    public static String password = "yearup";
    public static String dataBase = "northwind";
    public static String URL = "jdbc:mysql://localhost:3306/" + dataBase;

    public static void main(String[] args)  {

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
            try{

                System.out.print(mainMenu);
                int command = ConsoleHelper.promptForInt("Enter here"); //prompt for menu

                switch (command) {
                    case 1 -> displayAllProducts();
                    case 2 -> displayAllCustomers();
                    case 3 -> {
                        displayAllCategories();
                        boolean yesOrNO = ConsoleHelper.promptForYesNo("\nWould you like to see all products from a category");
                        if (yesOrNO){
                            String categoryID = ConsoleHelper.promptForString("\nEnter categoryID");
                            SearchByCategoryID(categoryID);
                        }
                    }
                    case 0 -> {
                        System.out.println("\nGoodbye!");
                        System.exit(0); // Force closes program
                    }
                    default -> System.out.println("Please insert a valid number!"); //Error message
                }
            } catch (Exception e) {
                System.out.println("There was an error: " + e.getMessage());
            }

        }
    }

    private static void displayAllProducts() throws SQLException, ClassNotFoundException {
        String query1 = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        Class.forName("com.mysql.cj.jdbc.Driver");


        //try to make connection, make a statement and get a result set
        try (
            Connection connection = DriverManager.getConnection(URL, username, password);
            Statement statement = connection.createStatement();
            ResultSet results1 = statement.executeQuery(query1);
                )
        {
            //getting the information and turning it into an object
            while (results1.next()) {
                int productID = results1.getInt("ProductID");
                String productName = results1.getString("ProductName");
                double unitPrice = results1.getDouble("UnitPrice");
                int unitsInStock = results1.getInt("UnitsInStock");

                System.out.printf("\n%-10d %-35s %-13.2f %-10d\n", productID, productName, unitPrice, unitsInStock);
            }
        }
    }

    private static void displayAllCustomers() throws SQLException, ClassNotFoundException {
        String query2 = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers ORDER BY Country";

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (
                Connection connection = DriverManager.getConnection(URL, username, password);
                // Create statement
                // The statement is tied to the open connection
                Statement statement = connection.createStatement();
                ResultSet results2 = statement.executeQuery(query2);
                )
        {
            while (results2.next()) {
                String contactName = results2.getString("ContactName");
                String companyName = results2.getString("CompanyName");
                String city = results2.getString("City");
                String country = results2.getString("Country");
                String phone = results2.getString("Phone");

                System.out.printf("\n%-34s %-40s %-18s %-18s %-10s\n", contactName, companyName, city, country, phone);
            }
        }
    }

    private static void displayAllCategories() throws SQLException, ClassNotFoundException {
        String query3 = "SELECT CategoryID, CategoryName  FROM categories ORDER BY CategoryID";

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (
                Connection connection = DriverManager.getConnection(URL, username, password);
                // Create statement
                // The statement is tied to the open connection
                Statement statement = connection.createStatement();
                ResultSet results3 = statement.executeQuery(query3);
                )
        {
            while (results3.next()) {
                int categoryID = results3.getInt("CategoryID");
                String categoryName = results3.getString("CategoryName");

                System.out.printf("\n%-7d %-10s\n", categoryID, categoryName);
            }
        }
    }

    private static void SearchByCategoryID(String categoryID) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        try(
                Connection connection = DriverManager.getConnection(URL, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT productID, productName, UnitPrice, unitsInStock" +
                             " FROM products WHERE categoryID = ? "))
        {
            preparedStatement.setString(1, categoryID);

            try(ResultSet results = preparedStatement.executeQuery()){

                while (results.next()) {
                    int productID = results.getInt("ProductID");
                    String productName = results.getString("ProductName");
                    double unitPrice = results.getDouble("UnitPrice");
                    int unitsInStock = results.getInt("UnitsInStock");

                    System.out.printf("\n%-10d %-35s %-13.2f %-10d\n", productID, productName, unitPrice, unitsInStock);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }
}