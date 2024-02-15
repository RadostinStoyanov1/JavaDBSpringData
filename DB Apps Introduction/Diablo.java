package com.jdbcdemo;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        PreparedStatement query = connection.prepareStatement(
                "SELECT u.user_name, u.first_name, u.last_name, COUNT(ug.id) AS 'games_count' " +
                        "FROM users AS u " +
                        "JOIN users_games AS ug ON u.id = ug.user_id " +
                        "WHERE u.user_name = ? " +
                        "GROUP BY u.user_name, u.first_name, u.last_name"
                );
        query.setString(1, username);
        ResultSet result = query.executeQuery();

        if (result.next()) {
            String dbUsername = result.getString("user_name");
            String dbFirstName = result.getString("first_name");
            String dbLastName = result.getString("last_name");
            int dbGamesCount = result.getInt("games_count");
            System.out.printf("User: %s%n%s %s has played %d games",
                    dbUsername, dbFirstName, dbLastName, dbGamesCount);
        } else {
            System.out.println("No such user exists");
        }
    }
}
