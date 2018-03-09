package com.yura.lampak;

import oracle.jdbc.driver.OracleDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OracleDAOStudent implements DAOStudent {

    private  static final OracleDAOStudent instance = new OracleDAOStudent();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public static OracleDAOStudent getInstance(){
        return instance;
    }

    private OracleDAOStudent(){
        super();
    }


    @Override
    public void connection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "DBExample", "kfvgfr_1996");
            if(!connection.isClosed()){
                System.out.println("Connection successful");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnection() {
        try {
            if(preparedStatement != null)
                preparedStatement.close();
            if (resultSet != null)
                resultSet.close();
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////// CREATE STUDENT  //////////////////////////////
    @Override
    public void createStudent(){
        connection();
        String sql = "INSERT INTO STUDENTS (STUDENT_NAME, STUDENT_GROUP) VALUES (?, ?)";
        try {
            System.out.println("Create new student:\nName:");
            String name = new Scanner(System.in).nextLine();
            System.out.println("Group");
            String group = new Scanner(System.in).nextLine();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, group);
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnection();
    }

    //////////////////////////////////////  SELECT STUDENT  ////////////////////////////////////////
    @Override
    public List<Student> getStudents() {
        connection();
        List<Student> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM STUDENTS");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(parseStudent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnection();
        return list;
    }

    private Student parseStudent(ResultSet resultSet){
        Student student = null;
        try {
            int id = resultSet.getInt("STUDENT_ID");
            String name = resultSet.getString("STUDENT_NAME");
            String group = resultSet.getString("STUDENT_GROUP");
            student = new Student(id, name, group);

        } catch (SQLException e) {
            e.printStackTrace();
        } return student;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////  UPDATE STUDENT  /////////////////////////////////////////
    @Override
    public void updateStudentById(){
        connection();
        String updateNameQuery = "UPDATE STUDENTS SET STUDENT_NAME=? WHERE STUDENT_ID=?";
        String updateGroupQuery = "UPDATE STUDENTS SET STUDENT_GROUP=? WHERE STUDENT_ID=?";
        try {
            String student_id = getStudentId();
            String query = getQueryToUpdate(updateNameQuery, updateGroupQuery);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, getNewData(query));
            preparedStatement.setString(2, student_id);
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("User was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnection();
    }

    private String getStudentId() {
        Scanner scanner = new Scanner(System.in);
        String stud_id = "";
        while (stud_id.equals("")){
            System.out.println("Enter id student");
            stud_id = scanner.nextLine();
        } return stud_id;
    }

    private String getQueryToUpdate (String query1, String query2) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Change name;\n2. Change group;");
        String userChoice = scanner.nextLine();
        while (userChoice.equals("")){
            System.out.println("Choose item");
            userChoice = scanner.nextLine();
        }
        switch (userChoice){
            case "1":
                return query1;
            case "2":
                return query2;
            default:
                System.out.println("Choose correct item");
        } return "-1";
    }

    private String getNewData (String query) {
        Scanner scanner = new Scanner(System.in);
        String temp = "";
        if (query.contains("NAME")){
            while (temp.isEmpty()){
                System.out.println("Enter new name");
                temp = scanner.nextLine();
            }
        } else if (query.contains("GROUP")){
            while (temp.isEmpty()){
                System.out.println("Enter new group");
                temp = scanner.nextLine();
            }
        } return temp;
    }
    //////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////   DELETE STUDENT  //////////////////////////
    @Override
    public void removeStudentById() {
        connection();
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter id to remove: ");
            int temp = scanner.nextInt();
            String query = "DELETE FROM STUDENTS WHERE STUDENT_ID = " + temp;
            preparedStatement = connection.prepareStatement(query);
            if (preparedStatement.executeUpdate() > 0)
                System.out.println("remove successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnection();
        getStudents();
    }
    ///////////////////////////////////////////////////////////////////////////////////




}
