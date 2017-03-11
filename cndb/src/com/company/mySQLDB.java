package com.company;

import java.sql.*;

/**
 * Created by Francis on 3/11/2017.
 */
public class mySQLDB {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    //java sql
    public Connection conn;
    public Statement stmt;
    public ResultSet res;

    public mySQLDB(){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //create statement
            stmt = conn.createStatement();

            //create query and Execute a query
            String query = "select * from account";
            res = stmt.executeQuery(query);

            //Extract data from result set
            System.out.println("Record from dB");
            //loop through database
            while(res.next()){
                //Retrieve by column name
                String username = res.getString("username");
                String password = res.getString("password");
                String email = res.getString("email");

                //Display values
                System.out.println("username: " + username);
                System.out.println("password: " + password);
                System.out.println("email: " + email);
            }
            //Clean-up environment
            res.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("errr..."+ e);
        }
    }

    public void getData(){
        try {


        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e);
        }
    }
}
