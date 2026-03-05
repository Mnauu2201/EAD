package com.example.studentinformationsystem.dao;

import com.example.studentinformationsystem.model.Subject;
import com.example.studentinformationsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subject_t";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt("subject_id"));
                sub.setSubjectCode(rs.getString("subject_code"));
                sub.setSubjectName(rs.getString("subject_name"));
                sub.setCredit(rs.getInt("credit"));
                list.add(sub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}