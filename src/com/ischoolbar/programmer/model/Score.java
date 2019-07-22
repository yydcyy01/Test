package com.ischoolbar.programmer.model;

public class Score {
private int id;
private int studentId;
private int courseId;
private String semester;
private String score1;
private String score2;
private String score3;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getStudentId() {
	return studentId;
}
public void setStudentId(int studentId) {
	this.studentId = studentId;
}
public int getCourseId() {
	return courseId;
}
public void setCourseId(int courseId) {
	this.courseId = courseId;
}
public String getSemester() {
	return semester;
}
public void setSemester(String semester) {
	this.semester = semester;
}
public String getScore1() {
	return score1;
}
public void setScore1(String score1) {
	this.score1 = score1;
}
public String getScore2() {
	return score2;
}
public void setScore2(String score2) {
	this.score2 = score2;
}
public String getScore3() {
	return score3;
}
public void setScore3(String score3) {
	this.score3 = score3;
}
}

//
//id int(10)序号
//studentId int(5)学生学号	
//courseId int(5)课程编号
//semester varchar(15)开课学期
//score1 float一考成绩
//score2 float二考成绩
//score3 float三考成绩