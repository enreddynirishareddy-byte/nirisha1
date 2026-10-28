package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // CREATE
    public static void insertStudent(
            String name, int age, String gender, String aadhar)
            throws Exception {

        String sql = "INSERT INTO student(name, age, gender, aadhar) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, aadhar);

            ps.executeUpdate();
            System.out.println("Student inserted successfully!");
        }
    }

    // READ
    public static void displayStudents() throws Exception {
        String sql = "SELECT * FROM student";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id")
                                + ", Name: " + rs.getString("name")
                                + ", Age: " + rs.getInt("age")
                                + ", Gender: " + rs.getString("gender")
                                + ", Aadhaar: " + rs.getString("aadhar")
                );
            }
        }
    }

    // UPDATE
    public static void updateStudent(int id, int age)
            throws Exception {

        String sql = "UPDATE student SET age = ? WHERE id = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, age);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            System.out.println(rows + " student record(s) updated.");
        }
    }

    // DELETE
    public static void deleteStudent(int id) throws Exception {
        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            System.out.println(rows + " student record(s) deleted.");
        }
    }

    public static void main(String[] args) {
        try {
            insertStudent("Nirisha", 21, "Female", "123456789019");

            System.out.println("\nStudent Details:");
            displayStudents();

            updateStudent(1, 22);

            // Uncomment this line only when you want to delete ID 1.
            // deleteStudent(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}