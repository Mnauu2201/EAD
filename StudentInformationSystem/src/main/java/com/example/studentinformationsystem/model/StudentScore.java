package com.example.studentinformationsystem.model;

public class StudentScore {
    private int id;
    private String studentCode;
    private String studentName;
    private String subjectName;
    private double score1;
    private double score2;
    private int credit;
    private double finalScore;
    private String grade;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public double getScore1() { return score1; }
    public void setScore1(double score1) { this.score1 = score1; }

    public double getScore2() { return score2; }
    public void setScore2(double score2) { this.score2 = score2; }

    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

    public double getFinalScore() { return finalScore; }
    public void setFinalScore(double finalScore) { this.finalScore = finalScore; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}