package com.example.studentinformationsystem.dao;

import com.example.studentinformationsystem.model.Student;
import com.example.studentinformationsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student_t";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                s.setStudentCode(rs.getString("student_code"));
                s.setFullName(rs.getString("full_name"));
                s.setAddress(rs.getString("address"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertStudent(Student student) {
        String sql = "INSERT INTO student_t (student_code, full_name, address) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getStudentCode());
            ps.setString(2, student.getFullName());
            ps.setString(3, student.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}