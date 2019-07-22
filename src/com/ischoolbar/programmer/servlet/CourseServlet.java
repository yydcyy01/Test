package com.ischoolbar.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ischoolbar.programmer.dao.CourseDao;
import com.ischoolbar.programmer.model.Course;
import com.ischoolbar.programmer.model.Course;
import com.ischoolbar.programmer.model.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author YYDCYY
 *	班级信息管理servlet
 */
public class CourseServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toCourseListView".equals(method)){
			courseList(request,response);
		}else if("getCourseList".equals(method)){
			getCourseList(request, response);
		}else if("AddCourse".equals(method)){
			addCourse(request, response);
		}else if("DeleteCourse".equals(method)){
			deleteCourse(request, response);
		}else if("EditCourse".equals(method)){
			editCourse(request, response);
//			System.out.println("servlet进来了");
		}else if("LimitCourseList".equals(method)){
			getLimitCourseList(request, response);
		}
	}
	
	private void editCourse(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		System.out.println("id="+id);
		String name = request.getParameter("name"); 
		String credithour = request.getParameter("credithour");
		Integer classhour = Integer.parseInt(request.getParameter("classhour"));
		Integer practicehour = Integer.parseInt(request.getParameter("practicehour"));
		String remark = request.getParameter("remark");
		
		Course course = new Course();
		course.setId(id);
		course.setName(name);
		course.setCredithour(credithour);
		course.setClasshour(classhour);
		course.setPracticehour(practicehour);
		course.setRemark(remark);

		CourseDao clazzDao = new CourseDao();
		if(clazzDao.editCourse(course)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				clazzDao.closeCon();
			}
		}
	}
	
	private void deleteCourse(HttpServletRequest request,
			HttpServletResponse response) {
		Integer id;
		try {
			id = Integer.parseInt(request.getParameter("courseid"));
			System.out.println("id="+id);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			id=(Integer)0;
			System.out.println("課程编辑过程中,id的string转int失败");
			}
		CourseDao courseDao = new CourseDao();
		if(courseDao.deleteCourse(id)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				courseDao.closeCon();
			}
		}
	}
	private void addCourse(HttpServletRequest request,
			HttpServletResponse response) {
		String name = request.getParameter("name"); 
		String credithour = request.getParameter("credithour");
		Integer classhour = Integer.parseInt(request.getParameter("classhour"));
		Integer practicehour = Integer.parseInt(request.getParameter("practicehour"));
		String remark = request.getParameter("remark");
//		Integer id = Integer.parseInt(request.getParameter("id"));

		Course course = new Course();
//		course.setId(id);
		course.setName(name);
		course.setCredithour(credithour);
		course.setClasshour(classhour);
		course.setPracticehour(practicehour);
		course.setRemark(remark);	
		//吃完饭回来继续做.
		CourseDao courseDao = new CourseDao();
		if(courseDao.addCourse(course)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				courseDao.closeCon();
			}
		}
		
	}
	private void courseList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getRequestDispatcher("view/courseList.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void getCourseList(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("courseName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Course course = new Course();
		course.setName(name);
		CourseDao courseDao = new CourseDao();
		List<Course> courseList = courseDao.getCourseList(course, new Page(currentPage, pageSize));
		int total = courseDao.getCourseListTotal(course);
		courseDao.closeCon();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", courseList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(courseList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//这个方法是课程管理中(返回去除已选课程的课程列表)
private void getLimitCourseList(HttpServletRequest request, HttpServletResponse response) {
	String name = request.getParameter("courseName");
	Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
	Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
	Course course = new Course();
	course.setName(name);
	CourseDao courseDao = new CourseDao();
	List<Course> courseList = courseDao.getCourseList(course, new Page(currentPage, pageSize));
	int total = courseDao.getCourseListTotal(course);
	courseDao.closeCon();
	response.setCharacterEncoding("UTF-8");
	Map<String, Object> ret = new HashMap<String, Object>();
	ret.put("total", total);
	ret.put("rows", courseList);
	try {
		String from = request.getParameter("from");
		if("combox".equals(from)){
			response.getWriter().write(JSONArray.fromObject(courseList).toString());
		}else{
			response.getWriter().write(JSONObject.fromObject(ret).toString());
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
