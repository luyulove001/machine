package com.qc.dao;

import android.database.SQLException;

import com.lidroid.xutils.DbUtils;

import java.util.List;

public interface DayDao {
	
	//ͨ��ID������
	public DayData findDayById(DbUtils db, int id) throws SQLException;
	//ͨ������������
	public DayData findDayByDay(DbUtils db, String name) throws SQLException;
	//�������г������ⷢ��δ�ɹ�
	public List<DayData> findDayListSendFail(DbUtils db, String name) throws SQLException;

	//���
	public void addDay(DbUtils db, DayData day) throws SQLException;
	//�޸�
	public void editDay(DbUtils db, DayData day) throws SQLException;
	//ɾ��
	public void delDay(DbUtils db, DayData day) throws SQLException;
	
}
