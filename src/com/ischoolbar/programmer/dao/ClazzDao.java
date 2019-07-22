package com.ischoolbar.programmer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ischoolbar.programmer.model.Clazz;
import com.ischoolbar.programmer.model.Page;
import com.ischoolbar.programmer.util.StringUtil;

/**
 * 
 * @author YYDCYY
 *�༶��Ϣ���ݿ����
 */
public class ClazzDao extends BaseDao {
	 private Logger logger=Logger.getLogger(this.getClass());
	 
	public List<Clazz> getClazzList(Clazz clazz,Page page){
		logger.warn("��ѯ�γ��б�(����Ϊҳ���������������,������ʾҳ��)");
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from s_clazz ";
		if(!StringUtil.isEmpty(clazz.getName())){
			sql += "where name like '%" + clazz.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = query(sql);
		try {
			while(resultSet.next()){
				Clazz cl = new Clazz();
				cl.setId(resultSet.getInt("id"));
				cl.setName(resultSet.getString("name"));
				cl.setInfo(resultSet.getString("info"));
				ret.add(cl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("getClazzList���������������ݿ�ʧ��");
		}
		return ret;
	}
	public int getClazzListTotal(Clazz clazz){
		int total = 0;
		String sql = "select count(*)as total from s_clazz ";
		if(!StringUtil.isEmpty(clazz.getName())){
			sql += "where name like '%" + clazz.getName() + "%'";
		}
		ResultSet resultSet = query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}
	public boolean addClazz(Clazz clazz){
		String sql = "insert into s_clazz values(null,'"+clazz.getName()+"','"+clazz.getInfo()+"') ";
		return update(sql);
	}
	public boolean deleteClazz(int id){
		String sql = "delete from s_clazz where id = "+id;
		return update(sql);
	}
	public boolean editClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		String sql = "update s_clazz set name = '"+clazz.getName()+"',info = '"+clazz.getInfo()+"' where id = " + clazz.getId();
		return update(sql);
	}
	
}
