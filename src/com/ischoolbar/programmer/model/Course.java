package com.ischoolbar.programmer.model;

public class Course {
private int id;
private String name;
private String credithour;
private int classhour;
private int practicehour;
private String remark;
public int getClasshour() {
	return classhour;
}
public void setClasshour(int classhour) {
	this.classhour = classhour;
}
public int getPracticehour() {
	return practicehour;
}
public void setPracticehour(int practicehour) {
	this.practicehour = practicehour;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCredithour() {
	return credithour;
}
public void setCredithour(String credithour) {
	this.credithour = credithour;
}


public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
}



//FieldTypeComment
//id int(5)�γ�id
//name varchar(30)�γ���
//credithour floatѧ��
//classhour int(11)����ѧʱ
//practicehour int(11)ʵ��ѧʱ
//remark text��ע