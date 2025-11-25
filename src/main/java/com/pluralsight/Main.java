package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String username = "root";
        String password = "yearup";
        String database = "northwind";

        String databaseurl = "jdbc:mysql://localhost:3306/" + database;

        // Load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 1. open a connection to the database
        // use the database URL to point to the correct database
        Connection connection;
        connection = DriverManager.getConnection(
                databaseurl,
                username,
                password);

        // Create statement
        // The statement is tied to the open connection
        Statement statement = connection.createStatement();

        //-------------------------------------------------------------------------------------------------------------

        String query1 = """
        SELECT
        productID, productName, UnitPrice, UnitsInStock
        
        FROM products""";

        ResultSet results1 = statement.executeQuery(query1);

        while (results1.next()) {
            int productID = results1.getInt("ProductID");
            String productName = results1.getString("ProductName");
            double unitPrice = results1.getDouble("UnitPrice");
            int unitsInStock = results1.getInt("UnitsInStock");

            System.out.printf("%-10d %-30s %-10.2f %-10d\n",productID, productName, unitPrice, unitsInStock);

        }

        results1.close();

        //-------------------------------------------------------------------------------------------------------------
        // Define your query
        String query2 = """
                SELECT
                
                CompanyName, ContactName, orders.orderid
                
                FROM northwind.customers
                left join orders on customers.CustomerID = orders.customerid
                order by ContactName;
                """;

        // 2. Execute your query
        ResultSet results2 = statement.executeQuery(query2);

        // process the results
        while (results2.next()) {
            String companyName = results2.getString("CompanyName");
            String contactName = results2.getString("ContactName");
            int orderId = results2.getInt("orderid");

            System.out.printf("%-30s %-30s %20d\n", companyName, contactName, orderId);

        }

        results2.close();

        //-------------------------------------------------------------------------------------------------------------

        // 3. Close the connection
        results.close();
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