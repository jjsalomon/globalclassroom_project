package com.company;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        mySQLDB connect = new mySQLDB();
        connect.createTable(); //creates table account and profile

        JFrame frame = new JFrame("loginGUI");
        frame.setContentPane(new loginGUI().login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        String username;
        String password;
        int option;
        boolean loop = true;
        boolean valid;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Press 1: Login\nPress 2: Register Account\nPress 3: Delete Account\nPress 4: View Profile ");
            option = scanner.nextInt();

            switch (option) {
                case 1:

                    System.out.print("Enter username: ");
                    username = scanner.next();
                    System.out.print("Enter password: ");
                    password = scanner.next();
                    //needs a return so that if login is not possible prints invalid
                    valid = connect.Login(username,password);
                    if (valid) {
                        loop = false;
                        System.out.println("Welcome " + username);
                        //!!add the main chess game

                    } else {
                        System.out.println("Invalid login details");
                    }
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.next();
                    System.out.print("Enter password: ");
                    password = scanner.next();

                    //returns valid true of false
                    valid = connect.insertData(username, password);
                    if (valid) {
                        System.out.println("Register success");
                    } else {
                        System.out.println("Register fail");
                    }
                    break;
                case 3:
                    System.out.print("Enter username: ");
                    username = scanner.next();
                    valid = connect.deleteData(username);
                    if (valid) {
                        System.out.println("Deletion success");
                    } else {
                        System.out.println("Deletion fail or no data found");
                    }
                    break;
                case 4:
                    System.out.print("Enter username: ");
                    username = scanner.next();
                    valid = connect.viewProfile(username);
                    if (!valid){
                        System.out.println("There is no such profile");
                    }
                    break;
                default:
                    System.out.println("Invalid input");
            }

        }while(loop);
//        connect.insertData();
//        connect.updateData();
//        connect.deleteData();
//        connect.getData();
    }
}
