package com.company;

import java.sql.*;

/**
 * Created by Francis on 3/11/2017.
 */

/*REFERENCES:
* https://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
* http://zetcode.com/db/mysqljava/
* http://www.vogella.com/tutorials/MySQLJava/article.html#jdbcdriver
*/

public class mySQLDB {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/test";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "";

    //java sql variables
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet res;

    //set up JDBC_driver
    public mySQLDB(){
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Method to view selected data
    public void getData(){
        try {
            //create connection to database
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //create query //!!will be change to get profile of user
            String query = "select * from account";
            //create preparedStatement
            pstmt = conn.prepareStatement(query);
            //Execute a query
            res = pstmt.executeQuery();

            System.out.println("Record from database");
            //Extract data from result set
            //loop through database (cursor)
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            closeRsc();
        }
    }

    //Method to add account and profile
    public void insertData(){ //need to add parameters for getting input from user
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            pstmt = conn.prepareStatement("INSERT INTO account VALUES(?,?,?)");
            pstmt.setString(1, "kkdso");
            pstmt.setString(2, "password");
            pstmt.setString(3, "kkdso@gmail.com");
            pstmt.executeUpdate();
            System.out.println("Insert Complete");
            //make profile here
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRsc();
        }
    }

    //Method to delete account and profile
    public void deleteData(){
        try {//!! if account is deleted  profile should be deleted too!
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            pstmt = conn.prepareStatement("DELETE FROM account WHERE username= ?");
            pstmt.setString(1, "changes");
            pstmt.executeUpdate();
            System.out.println("Delete Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRsc();
        }
    }

    public void updateData(){
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //!! change to profile update where ID = id
            pstmt = conn.prepareStatement("UPDATE account SET username = ?, password = ?, email = ? WHERE username = ?");
            pstmt.setString(1, "changes");
            pstmt.setString(2, "password");
            pstmt.setString(3, "kkdso@gmail.com");
            pstmt.setString(4, "kkdso");
            pstmt.executeUpdate();
            System.out.println("Update Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRsc();
        }
    }

    //Close database resources
    private void closeRsc() {
        try {
            if (res != null) {
                res.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
