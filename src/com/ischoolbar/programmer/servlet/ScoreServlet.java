package com.ischoolbar.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ischoolbar.programmer.dao.ScoreDao;
import com.ischoolbar.programmer.model.Score;
import com.ischoolbar.programmer.model.Student;
import com.ischoolbar.programmer.model.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author YYDCYY
 *
 */
//@WebServlet("/ScoreServlet")
public class ScoreServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String method = request.getParameter("method");
		if ("toScoreListView".equals(method)) {
			scoreList(request, response);
		}else if("getScoreList".equals(method)){
			getScoreList(request, response);
			
		}
		else if ("AddScore".equals(method)) {
			addScore(request, response);
		}else if ("limitScoreList".equals(method)) {
			limitScoreList(request, response);
		}
		// else if ("DeleteScore".equals(method)) {
		// deleteScore(request, response);
		// }
	}

	// private void editCourse(HttpServletRequest request, HttpServletResponse
	// response) {
	//
	// Integer id = Integer.parseInt(request.getParameter("id"));
	// System.out.println("id=" + id);
	// String name = request.getParameter("name");
	// String credithour = request.getParameter("credithour");
	// Integer classhour = Integer.parseInt(request.getParameter("classhour"));
	// Integer practicehour =
	// Integer.parseInt(request.getParameter("practicehour"));
	// String remark = request.getParameter("remark");
	//
	// Score course = new Score();
	// course.setId(id);
	// course.setName(name);
	// course.setCredithour(credithour);
	// course.setClasshour(classhour);
	// course.setPracticehour(practicehour);
	// course.setRemark(remark);
	//
	// ScoreDao clazzDao = new ScoreDao();
	// if (clazzDao.editCourse(course)) {
	// try {
	// response.getWriter().write("success");
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// clazzDao.closeCon();
	// }
	// }
	// }

	// private void deleteCourse(HttpServletRequest request, HttpServletResponse
	// response) {
	// Integer id;
	// try {
	// id = Integer.parseInt(request.getParameter("courseid"));
	// System.out.println("id=" + id);
	// } catch (NumberFormatException e1) {
	// // TODO Auto-generated catch block
	// id = (Integer) 0;
	// System.out.println("�n�̱༭������,id��stringתintʧ��");
	// }
	// ScoreDao courseDao = new ScoreDao();
	// if (courseDao.deleteCourse(id)) {
	// try {
	// response.getWriter().write("success");
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// courseDao.closeCon();
	// }
	// }
	// }
	// id int(10)���

	// studentId int(5)ѧ��ѧ��
	// courseId int(5)�γ̱��

	// semester varchar(15)����ѧ�� �������18�̶�ֵ,����Ҫ��ȡ

	private void limitScoreList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("view/limitScoreList.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void scoreList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("view/scoreList.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addScore(HttpServletRequest request, HttpServletResponse response) {
		// id���û�ȡ,�Զ����
		Integer courseId = Integer.parseInt(request.getParameter("id")); // ��ȡ�û��˿�ѡ���id

		// ��ȡ��ǰ��¼�û�����
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
//		Student student = new Student();
//		student.setName(name);
//		student.setClazzId(clazz);
		if (userType != 2) {  
//			ֻ��ѧ������ѡ��,�������ѧ��,ֱ����ֹѡ��
			return ;
		}
		// �����ѧ����ֻ�ܲ鿴�Լ�����Ϣ
		Student currentUser = (Student) request.getSession().getAttribute("user");
		int studentId =currentUser.getId();
		
		Score score = new Score();
		score.setCourseId(courseId);
		score.setStudentId(studentId);

		ScoreDao scoreDao = new ScoreDao();
		if (scoreDao.addScore(score)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				scoreDao.closeCon();
			}
		}

	}

	

	private void getScoreList(HttpServletRequest request, HttpServletResponse response) {
		Integer studentId = Integer.parseInt(request.getParameter("studentId"));
		Integer courseId = Integer.parseInt(request.getParameter("courseId"));

		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));

		Score score = new Score();
		score.setStudentId(studentId);
		score.setCourseId(courseId);
		ScoreDao scoreDao = new ScoreDao();
		List<Score> scoreList = scoreDao.getScoreList(score, new Page(currentPage, pageSize));
		int total = scoreDao.getScoreListTotal(score);
		scoreDao.closeCon();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", scoreList);
		try {
			String from = request.getParameter("from");
			if ("combox".equals(from)) {
				response.getWriter().write(JSONArray.fromObject(scoreList).toString());
			} else {
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// id int(10)���
	// studentId int(5)ѧ��ѧ��
	// courseId int(5)�γ̱��
	// semester varchar(15)����ѧ��
	// score1 floatһ���ɼ�
	// score2 float�����ɼ�
	// score3 float�����ɼ�
}
