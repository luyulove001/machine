package com.qc.dao;

import android.database.SQLException;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class ChairImpl implements ChairDao {

	@Override
	public ChairData findChairById(DbUtils db, int id) throws SQLException {
		ChairData chair = new ChairData();
		try {
			chair = db.findFirst(Selector.from(ChairData.class).where("id","=",id));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return chair;
	}

	@Override
	public ChairData findChairByName(DbUtils db, String name)
			throws SQLException {
		ChairData chair = new ChairData();
		try {
			chair = db.findFirst(Selector.from(ChairData.class).where("chairName","=",name));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return chair;
	}
	
	@Override
	public void addChair(DbUtils db, ChairData chair) throws SQLException {
		try {
			db.save(chair);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editChair(DbUtils db, ChairData chair) throws SQLException {
		try {
			db.update(chair);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delChair(DbUtils db, ChairData chair) throws SQLException {
		try {
			db.delete(chair);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
}
