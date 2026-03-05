package com.example.studentinformationsystem.servlet;

import com.example.studentinformationsystem.dao.StudentDAO;
import com.example.studentinformationsystem.dao.StudentScoreDAO;
import com.example.studentinformationsystem.dao.SubjectDAO;
import com.example.studentinformationsystem.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private final StudentDAO studentDAO       = new StudentDAO();
    private final StudentScoreDAO scoreDAO    = new StudentScoreDAO();
    private final SubjectDAO subjectDAO       = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("scores",   scoreDAO.getAllScores());
        req.setAttribute("students", studentDAO.getAllStudents());
        req.setAttribute("subjects", subjectDAO.getAllSubjects());
        req.getRequestDispatcher("/WEB-INF/views/index.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Student s = new Student(
                req.getParameter("studentCode"),
                req.getParameter("fullName"),
                req.getParameter("address")
        );
        studentDAO.insertStudent(s);
        resp.sendRedirect(req.getContextPath() + "/students");
    }
}