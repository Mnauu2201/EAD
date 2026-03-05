package com.example.studentinformationsystem.dao;

import com.example.studentinformationsystem.model.StudentScore;
import com.example.studentinformationsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentScoreDAO {

    public List<StudentScore> getAllScores() {
        List<StudentScore> list = new ArrayList<>();
        String sql = """
                SELECT ss.student_score_id,
                       st.student_code,
                       st.full_name,
                       sub.subject_name,
                       ss.score1,
                       ss.score2,
                       sub.credit
                FROM student_score_t ss
                JOIN student_t st  ON ss.student_id  = st.student_id
                JOIN subject_t sub ON ss.subject_id  = sub.subject_id
                ORDER BY ss.student_score_id
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StudentScore sc = new StudentScore();
                sc.setId(rs.getInt("student_score_id"));
                sc.setStudentCode(rs.getString("student_code"));
                sc.setStudentName(rs.getString("full_name"));
                sc.setSubjectName(rs.getString("subject_name"));
                sc.setScore1(rs.getDouble("score1"));
                sc.setScore2(rs.getDouble("score2"));
                sc.setCredit(rs.getInt("credit"));

                double finalScore = 0.3 * sc.getScore1() + 0.7 * sc.getScore2();
                sc.setFinalScore(Math.round(finalScore * 10.0) / 10.0);

                if      (finalScore >= 8.0) sc.setGrade("A");
                else if (finalScore >= 6.0) sc.setGrade("B");
                else if (finalScore >= 4.0) sc.setGrade("D");
                else                        sc.setGrade("F");

                list.add(sc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy 1 score theo ID để điền vào form edit
    public StudentScore getScoreById(int id) {
        String sql = """
                SELECT ss.student_score_id,
                       st.student_id,
                       st.student_code,
                       st.full_name,
                       sub.subject_id,
                       sub.subject_name,
                       ss.score1,
                       ss.score2,
                       sub.credit
                FROM student_score_t ss
                JOIN student_t st  ON ss.student_id  = st.student_id
                JOIN subject_t sub ON ss.subject_id  = sub.subject_id
                WHERE ss.student_score_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                StudentScore sc = new StudentScore();
                sc.setId(rs.getInt("student_score_id"));
                sc.setStudentCode(rs.getString("student_code"));
                sc.setStudentName(rs.getString("full_name"));
                sc.setSubjectName(rs.getString("subject_name"));
                sc.setScore1(rs.getDouble("score1"));
                sc.setScore2(rs.getDouble("score2"));
                sc.setCredit(rs.getInt("credit"));
                return sc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertScore(int studentId, int subjectId,
                               double score1, double score2) {
        String sql = """
                INSERT INTO student_score_t (student_id, subject_id, score1, score2)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setDouble(3, score1);
            ps.setDouble(4, score2);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật score1 và score2 theo ID
    public boolean updateScore(int scoreId, double score1, double score2) {
        String sql = """
                UPDATE student_score_t
                SET score1 = ?, score2 = ?
                WHERE student_score_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, score1);
            ps.setDouble(2, score2);
            ps.setInt(3, scoreId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}