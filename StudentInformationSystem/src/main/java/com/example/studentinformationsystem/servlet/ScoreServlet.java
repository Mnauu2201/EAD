package com.example.studentinformationsystem.servlet;

import com.example.studentinformationsystem.dao.StudentDAO;
import com.example.studentinformationsystem.dao.StudentScoreDAO;
import com.example.studentinformationsystem.dao.SubjectDAO;
import com.example.studentinformationsystem.model.StudentScore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/scores")
public class ScoreServlet extends HttpServlet {

    private final StudentScoreDAO scoreDAO = new StudentScoreDAO();
    private final StudentDAO studentDAO    = new StudentDAO();
    private final SubjectDAO subjectDAO    = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("editId");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            StudentScore sc = scoreDAO.getScoreById(id);
            req.setAttribute("editScore",  sc);
            req.setAttribute("students",   studentDAO.getAllStudents());
            req.setAttribute("subjects",   subjectDAO.getAllSubjects());
            req.setAttribute("scores",     scoreDAO.getAllScores());
            req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/students");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            int scoreId  = Integer.parseInt(req.getParameter("scoreId"));
            double score1 = Double.parseDouble(req.getParameter("score1"));
            double score2 = Double.parseDouble(req.getParameter("score2"));
            scoreDAO.updateScore(scoreId, score1, score2);
        } else {
            scoreDAO.insertScore(
                    Integer.parseInt(req.getParameter("studentId")),
                    Integer.parseInt(req.getParameter("subjectId")),
                    Double.parseDouble(req.getParameter("score1")),
                    Double.parseDouble(req.getParameter("score2"))
            );
        }
        resp.sendRedirect(req.getContextPath() + "/students");
    }
}