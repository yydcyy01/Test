package com.ischoolbar.programmer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ischoolbar.programmer.model.Page;
import com.ischoolbar.programmer.model.Score;
import com.ischoolbar.programmer.util.StringUtil;

public class ScoreDao extends BaseDao{
	//id int(10)序号
	//studentId int(5)学生学号	
	//courseId int(5)课程编号
	//semester varchar(15)开课学期
	//score1 float一考成绩
	//score2 float二考成绩
	//score3 float三考成绩 
	
	public boolean addScore(Score score) {
		String sql = "insert into score values(null,'" + score.getStudentId() + "','" + score.getCourseId() + "'";
		sql += ",'2018',";
		sql += "null,null,null)";
		 System.out.println(sql);
		return update(sql);
	}
//
//	public boolean editCourse(Course course) {
//		 String sql = "update course set name = '"+course.getName()+"'";
//		 sql += ",credithour = '" + course.getCredithour() + "'";
//		 sql += ",classhour = '" + course.getClasshour() + "'";
//		 sql += ",practicehour = '" + course.getPracticehour() + "'";
//		 sql += ",Remark = '" + course.getRemark() +"'";
//		 sql += " where id='"+course.getId()+"'";
//		System.out.println(sql);
//		 return update(sql);
//	}
//	
//	public boolean deleteCourse(int ids) {
//		String sql = "delete from course where id in("+ids+")";
//		return update(sql);
//	}
//
//	public boolean deleteCourse(String ids) {
//		String sql = "delete from s_student where id in(" + ids + ")";
//		return update(sql);
//	}
//
//	public Course getCourse(int id) {
//		String sql = "select * from course where id = " + id;
//		Course course = null;
//		ResultSet resultSet = query(sql);
//		try {
//			if (resultSet.next()) {
//				Course c = new Course();
//				c.setId(resultSet.getInt("id"));
//				c.setName(resultSet.getString("name"));
//				c.setCredithour(resultSet.getString("credithour"));
//				
//				c.setClasshour(resultSet.getInt("classhour"));
//				c.setPracticehour(resultSet.getInt("practicehour"));
//				c.setRemark(resultSet.getString("remark"));
//				return course;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return course;
//	}
//
	public List<Score> getScoreList(Score score, Page page) {
		List<Score> ret = new ArrayList<Score>();
		String sql = "select * from score ";
		String CourseId = String.valueOf(score.getCourseId());
		String studentId = String.valueOf(score.getStudentId());

		if (!StringUtil.isEmpty(CourseId)) {
			sql += "and courseid like '%" + score.getCourseId() + "%'";
		}
		if (!StringUtil.isEmpty(studentId)) {
			sql += "and courseid like '%" + score.getStudentId() + "%'";
		}
		
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = query(sql.replaceFirst("and", "where"));
		System.out.println(sql);
		//
		//id int(10)序号
		//studentId int(5)学生学号	
		//courseId int(5)课程编号
		//semester varchar(15)开课学期
		//score1 float一考成绩
		//score2 float二考成绩
		//score3 float三考成绩
		try {
			while (resultSet.next()) {
				Score s = new Score();
				s.setId(resultSet.getInt("id"));
				s.setStudentId(resultSet.getInt("studentId"));
				s.setCourseId(resultSet.getInt("courseId"));
				s.setSemester(resultSet.getString("semester"));
				s.setScore1(resultSet.getString("score1"));
				s.setScore2(resultSet.getString("score2"));
				s.setScore3(resultSet.getString("score3"));
				ret.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public int getScoreListTotal(Score score) {
		int total = 0;
		String sql = "select count(*)as total from score ";
		
		String CourseId = String.valueOf(score.getCourseId());
		String studentId = String.valueOf(score.getStudentId());

		if (!StringUtil.isEmpty(CourseId)) {
			sql += "and courseid like '%" + score.getCourseId() + "%'";
		}
		if (!StringUtil.isEmpty(studentId)) {
			sql += "and courseid like '%" + score.getStudentId() + "%'";
		}

		ResultSet resultSet = query(sql.replaceFirst("and", "where"));
		try {
			while (resultSet.next()) {
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
}
