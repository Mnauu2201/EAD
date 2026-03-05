<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Student Information System</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f0f0f0; }

        .header {
            background: #5a7a3a; color: white;
            padding: 16px 30px; font-size: 20px; font-weight: bold;
        }
        .container {
            max-width: 1100px; margin: 20px auto; background: white;
            border-radius: 8px; overflow: hidden;
            box-shadow: 0 2px 8px rgba(0,0,0,0.15);
        }
        h2 { text-align: center; padding: 16px; font-size: 18px; color: #333; }

        .toolbar { padding: 10px 20px; display: flex; gap: 10px; }

        .btn {
            background: #5a7a3a; color: white; padding: 8px 18px;
            border: none; border-radius: 4px; cursor: pointer; font-size: 14px;
        }
        .btn:hover { background: #486030; }
        .btn-cancel { background: #888; }
        .btn-cancel:hover { background: #666; }

        .btn-edit {
            background: none; border: none; cursor: pointer;
            color: #5a7a3a; font-size: 16px; padding: 4px 8px;
        }
        .btn-edit:hover { color: #2a4a1a; transform: scale(1.2); }

        table { width: 100%; border-collapse: collapse; }
        thead tr { background: #5a7a3a; color: white; }
        th, td { padding: 10px 14px; text-align: center; border-bottom: 1px solid #e0e0e0; }
        tbody tr:hover { background: #f5f5f5; }

        .overlay {
            display: none; position: fixed; inset: 0;
            background: rgba(0,0,0,0.5); z-index: 100;
            justify-content: center; align-items: center;
        }
        .overlay.active { display: flex; }
        .modal {
            background: white; padding: 28px; border-radius: 8px;
            width: 420px; box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }
        .modal h3 { margin-bottom: 16px; color: #5a7a3a; }
        .modal label { display: block; margin-bottom: 4px; font-size: 13px; color: #555; }
        .modal input, .modal select {
            width: 100%; padding: 8px 10px; margin-bottom: 14px;
            border: 1px solid #ccc; border-radius: 4px; font-size: 14px;
        }
        .modal input[readonly] { background: #f5f5f5; color: #888; }
        .modal-actions { display: flex; gap: 10px; justify-content: flex-end; margin-top: 8px; }
    </style>
</head>
<body>

<div class="header">Student Information System</div>

<div class="container">
    <h2>Student Information</h2>

    <div class="toolbar">
        <button class="btn" onclick="showModal('studentModal')">+ Student</button>
        <button class="btn" onclick="showModal('scoreModal')">+ Score</button>
    </div>

    <table>
        <thead>
            <tr>
                <th>Id</th>
                <th>Student Id</th>
                <th>Student Name</th>
                <th>Subject Name</th>
                <th>Score 1</th>
                <th>Score 2</th>
                <th>Credit</th>
                <th>Grade</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sc" items="${scores}">
                <tr>
                    <td>${sc.id}</td>
                    <td>${sc.studentCode}</td>
                    <td>${sc.studentName}</td>
                    <td>${sc.subjectName}</td>
                    <td>${sc.score1}</td>
                    <td>${sc.score2}</td>
                    <td>${sc.credit}</td>
                    <td>${sc.grade}</td>
                    <td>
                        <!-- Nút ✏️ cây bút -->
                        <button class="btn-edit"
                            onclick="openEditModal(
                                ${sc.id},
                                '${sc.studentCode}',
                                '${sc.studentName}',
                                '${sc.subjectName}',
                                ${sc.score1},
                                ${sc.score2}
                            )"
                            title="Edit">✏️</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div id="studentModal" class="overlay">
    <div class="modal">
        <h3>Add New Student</h3>
        <form action="${pageContext.request.contextPath}/students" method="post">
            <label>Student Code *</label>
            <input type="text" name="studentCode" placeholder="e.g. 2007A10" required>
            <label>Full Name *</label>
            <input type="text" name="fullName" placeholder="e.g. Nguyễn Văn A" required>
            <label>Address</label>
            <input type="text" name="address" placeholder="e.g. Hà Nội">
            <div class="modal-actions">
                <button type="button" class="btn btn-cancel"
                        onclick="hideModal('studentModal')">Cancel</button>
                <button type="submit" class="btn">Save</button>
            </div>
        </form>
    </div>
</div>
<div id="scoreModal" class="overlay">
    <div class="modal">
        <h3>Add Score</h3>
        <form action="${pageContext.request.contextPath}/scores" method="post">
            <input type="hidden" name="action" value="insert">
            <label>Student *</label>
            <select name="studentId" required>
                <option value="">-- Select Student --</option>
                <c:forEach var="st" items="${students}">
                    <option value="${st.studentId}">
                        ${st.studentCode} - ${st.fullName}
                    </option>
                </c:forEach>
            </select>
            <label>Subject *</label>
            <select name="subjectId" required>
                <option value="">-- Select Subject --</option>
                <c:forEach var="sub" items="${subjects}">
                    <option value="${sub.subjectId}">
                        ${sub.subjectCode} - ${sub.subjectName}
                    </option>
                </c:forEach>
            </select>
            <label>Score 1</label>
            <input type="number" name="score1"
                   min="0" max="10" step="0.1" placeholder="0.0 - 10.0" required>
            <label>Score 2</label>
            <input type="number" name="score2"
                   min="0" max="10" step="0.1" placeholder="0.0 - 10.0" required>
            <div class="modal-actions">
                <button type="button" class="btn btn-cancel"
                        onclick="hideModal('scoreModal')">Cancel</button>
                <button type="submit" class="btn">Save</button>
            </div>
        </form>
    </div>
</div>
<div id="editModal" class="overlay">
    <div class="modal">
        <h3>✏️ Edit Score</h3>
        <form action="${pageContext.request.contextPath}/scores" method="post">
            <input type="hidden" name="action"  value="update">
            <input type="hidden" name="scoreId" id="editScoreId">

            <label>Student Id</label>
            <input type="text" id="editStudentCode" readonly>

            <label>Student Name</label>
            <input type="text" id="editStudentName" readonly>

            <label>Subject Name</label>
            <input type="text" id="editSubjectName" readonly>

            <label>Score 1 *</label>
            <input type="number" name="score1" id="editScore1"
                   min="0" max="10" step="0.1" required>

            <label>Score 2 *</label>
            <input type="number" name="score2" id="editScore2"
                   min="0" max="10" step="0.1" required>

            <div class="modal-actions">
                <button type="button" class="btn btn-cancel"
                        onclick="hideModal('editModal')">Cancel</button>
                <button type="submit" class="btn">Update</button>
            </div>
        </form>
    </div>
</div>

<script>
    function showModal(id) {
        document.getElementById(id).classList.add('active');
    }
    function hideModal(id) {
        document.getElementById(id).classList.remove('active');
    }
    function openEditModal(scoreId, studentCode, studentName, subjectName, score1, score2) {
        document.getElementById('editScoreId').value    = scoreId;
        document.getElementById('editStudentCode').value = studentCode;
        document.getElementById('editStudentName').value = studentName;
        document.getElementById('editSubjectName').value = subjectName;
        document.getElementById('editScore1').value     = score1;
        document.getElementById('editScore2').value     = score2;
        showModal('editModal');
    }
    document.querySelectorAll('.overlay').forEach(el => {
        el.addEventListener('click', e => {
            if (e.target === el) el.classList.remove('active');
        });
    });
</script>
</body>
</html>