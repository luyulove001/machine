package com.qc.dao;

import android.database.SQLException;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class DayImpl implements DayDao{

	@Override
	public DayData findDayById(DbUtils db, int id) throws SQLException {
		// TODO Auto-generated method stub
		DayData day = new DayData();
		try {
			day = db.findFirst(Selector.from(DayData.class).where("id","=",id));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day;
	}

	@Override
	public DayData findDayByDay(DbUtils db, String name) throws SQLException {
		// TODO Auto-generated method stub
		DayData day = new DayData();
		try {
			day = db.findFirst(Selector.from(DayData.class).where("day","=",name));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day;
	}

	@Override
	public List<DayData> findDayListSendFail(DbUtils db, String name) throws SQLException {
		// TODO Auto-generated method stub
		List<DayData> dayList = new ArrayList<DayData>();
		try {
			dayList = db.findAll(Selector.from(DayData.class).where("isSend","=",0).and("day", "!=", name));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return dayList;
	}

	@Override
	public void addDay(DbUtils db, DayData day) throws SQLException {
		// TODO Auto-generated method stub
		try {
			db.save(day);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void editDay(DbUtils db, DayData day) throws SQLException {
		// TODO Auto-generated method stub
		try {
			db.update(day);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delDay(DbUtils db, DayData day) throws SQLException {
		// TODO Auto-generated method stub
		try {
			db.delete(day);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
