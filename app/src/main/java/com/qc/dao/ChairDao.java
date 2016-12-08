package com.qc.dao;

import android.database.SQLException;

import com.lidroid.xutils.DbUtils;

public interface ChairDao {

	//ͨ��ID��������
	public ChairData findChairById(DbUtils db, int id) throws SQLException;
	//ͨ�����Ʋ�������
	public ChairData findChairByName(DbUtils db, String name) throws SQLException;
	
	
	//�������
	public void addChair(DbUtils db, ChairData chair) throws SQLException;
	//�޸�����
	public void editChair(DbUtils db, ChairData chair) throws SQLException;
	//ɾ������
	public void delChair(DbUtils db, ChairData chair) throws SQLException;
	
}
