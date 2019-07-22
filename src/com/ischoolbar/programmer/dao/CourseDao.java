package com.ischoolbar.programmer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ischoolbar.programmer.model.Course;
import com.ischoolbar.programmer.model.Page;
import com.ischoolbar.programmer.model.Course;
import com.ischoolbar.programmer.util.StringUtil;

public class CourseDao extends BaseDao {
	public boolean addCourse(Course course) {
		String sql = "insert into course values(null,'" + course.getName() + "','" + course.getCredithour() + "'";
		sql += ",'" + course.getClasshour() + "'," + course.getPracticehour();
		sql += ",'" + course.getRemark() + "')";
		 System.out.println(sql);
		return update(sql);
	}

	public boolean editCourse(Course course) {
		 String sql = "update course set name = '"+course.getName()+"'";
		 sql += ",credithour = '" + course.getCredithour() + "'";
		 sql += ",classhour = '" + course.getClasshour() + "'";
		 sql += ",practicehour = '" + course.getPracticehour() + "'";
		 sql += ",Remark = '" + course.getRemark() +"'";
		 sql += " where id='"+course.getId()+"'";
		System.out.println(sql);
		 return update(sql);
	}
	
	public boolean deleteCourse(int ids) {
		String sql = "delete from course where id in("+ids+")";
		return update(sql);
	}

	public boolean deleteCourse(String ids) {
		String sql = "delete from s_student where id in(" + ids + ")";
		return update(sql);
	}

	public Course getCourse(int id) {
		String sql = "select * from course where id = " + id;
		Course course = null;
		ResultSet resultSet = query(sql);
		try {
			if (resultSet.next()) {
				Course c = new Course();
				c.setId(resultSet.getInt("id"));
				c.setName(resultSet.getString("name"));
				c.setCredithour(resultSet.getString("credithour"));
				
				c.setClasshour(resultSet.getInt("classhour"));
				c.setPracticehour(resultSet.getInt("practicehour"));
				c.setRemark(resultSet.getString("remark"));
				return course;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	public List<Course> getCourseList(Course course, Page page) {
		List<Course> ret = new ArrayList<Course>();
		String sql = "select * from course ";
		if (!StringUtil.isEmpty(course.getName())) {
			sql += "and name like '%" + course.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = query(sql.replaceFirst("and", "where"));
		try {
			while (resultSet.next()) {
				Course c = new Course();
				c.setId(resultSet.getInt("id"));
				c.setName(resultSet.getString("name"));
				c.setCredithour(resultSet.getString("credithour"));
				c.setClasshour(resultSet.getInt("classhour"));
				c.setPracticehour(resultSet.getInt("practicehour"));
				c.setRemark(resultSet.getString("remark"));
				ret.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public int getCourseListTotal(Course course) {
		int total = 0;
		String sql = "select count(*)as total from course ";
		if (!StringUtil.isEmpty(course.getName())) {
			sql += "and name like '%" + course.getName() + "%'";
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
