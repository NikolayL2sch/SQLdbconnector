package com.kolyan;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String req = in.nextLine();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        ArrayList<String> Quantity = new ArrayList<>();
        ArrayList<String> Price = new ArrayList<>();
        ArrayList<String> Name = new ArrayList<>();

        HashSet<String> item_id = new HashSet<>();

        String url = "jdbc:mysql://195.19.44.146:3306/user22?serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url,"user22", "user22");
            System.out.println("Connected...");

            statement =  connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM shop WHERE name LIKE '" + req + "'");
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                resultSet = statement.executeQuery("SELECT * FROM ALL_IN WHERE shop LIKE '" + id + "'");
                while (resultSet.next()){
                    String quantity = resultSet.getString("quantity");
                    String price = resultSet.getString("price");
                    String itemid = resultSet.getString("item");
                    Quantity.add(quantity);
                    Price.add(price);
                    item_id.add(itemid);
                }
                for (String e : item_id){
                    resultSet = statement.executeQuery("SELECT * FROM item WHERE id LIKE '" + e + "'");
                    if (resultSet.next()) {
                        Name.add(resultSet.getString("name"));
                    }
                }
                System.out.println(Name);
                System.out.println(Price);
                System.out.println(Quantity);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
