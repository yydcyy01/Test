package com.ischoolbar.programmer.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;

import com.ischoolbar.programmer.dao.AdminDao;
import com.ischoolbar.programmer.dao.StudentDao;
import com.ischoolbar.programmer.dao.TeacherDao;
import com.ischoolbar.programmer.model.Admin;
import com.ischoolbar.programmer.model.Student;
import com.ischoolbar.programmer.model.Teacher;
import com.ischoolbar.programmer.util.StringUtil;

/**
 * 
 * @author YYDCYY
 *登录验证servlet
 */
public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5870852067427524781L;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("logout".equals(method)){
			logout(request, response);
			return;
		}
		String vcode = request.getParameter("vcode");
		String name = request.getParameter("account");
		String password = request.getParameter("password");
		int type = Integer.parseInt(request.getParameter("type"));
		String loginCpacha = request.getSession().getAttribute("loginCapcha").toString();
		if(StringUtil.isEmpty(vcode)){
			response.getWriter().write("vcodeError");
			return;
		}
		if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
			response.getWriter().write("vcodeError");
			return;
		}
		//验证码验证通过，对比用户名密码是否正确
		String loginStatus = "loginFaild";
		switch (type) {
			case 1:{
				AdminDao adminDao = new AdminDao();
				Admin admin = adminDao.login(name, password);
				adminDao.closeCon();
				if(admin == null){
					response.getWriter().write("loginError");
					return;
				}
				MDC.put("userId",admin.getName());  //MDC为 log4j和standard,  记录name在log中
				HttpSession session = request.getSession();
				session.setAttribute("user", admin);
				session.setAttribute("userType", type);
				loginStatus = "loginSuccess";
				break;
			}
			case 2:{
				StudentDao studentDao = new StudentDao();
				Student student = studentDao.login(name, password);
				studentDao.closeCon();
				if(student == null){
					response.getWriter().write("loginError");
					return;
				}
				MDC.put("userId",student.getName());//MDC为 log4j和standard,  记录name在log中
				HttpSession session = request.getSession();
				session.setAttribute("user", student);
				session.setAttribute("userType", type);
				loginStatus = "loginSuccess";
				break;
			}
			case 3:{
				TeacherDao teahcerDao = new TeacherDao();
				Teacher teacher = teahcerDao.login(name, password);
				teahcerDao.closeCon();
				if(teacher == null){
					response.getWriter().write("loginError");
					return;
				}
				MDC.put("userId",teacher.getName());  //MDC为 log4j和standard,  记录name在log中
				HttpSession session = request.getSession();
				session.setAttribute("user", teacher);
				session.setAttribute("userType", type);
				loginStatus = "loginSuccess";
				break;
			}
			default:
				break;
			}
		response.getWriter().write(loginStatus);
		
	}
	
	private void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("userType");
		response.sendRedirect("index.jsp");
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
        String log4jLocation = config.getInitParameter("log4j-properties-location");  
        ServletContext sc = config.getServletContext();  
        if (log4jLocation == null) {  
            BasicConfigurator.configure();  
        } else {  
            String webAppPath = sc.getRealPath("/");  
            String log4jProp = webAppPath + log4jLocation;  
            File file = new File(log4jProp);  
            if (file.exists()) {  
                PropertyConfigurator.configure(log4jProp);  
            } else {  
                BasicConfigurator.configure();  
            }  
        }  
        super.init(config);  
	}

}
