package com.qc.dao;

import android.database.SQLException;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class OffLineTicketImpl implements OffLineTicketDao {

	@Override
	public OffLineTicketData findOffLineById(DbUtils db, int id)
			throws SQLException {
		OffLineTicketData offline = new OffLineTicketData();
		try {
			offline = db.findFirst(Selector.from(OffLineTicketData.class).where("id","=",id));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return offline;
	}

	@Override
	public OffLineTicketData findOffLineByDay(DbUtils db, String day)
			throws SQLException {
		OffLineTicketData offline = new OffLineTicketData();
		try {
			offline = db.findFirst(Selector.from(OffLineTicketData.class).where("day","=",day));
			if (offline == null)
				offline = new OffLineTicketData();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return offline;
	}
	
	@Override
	public List<OffLineTicketData> findOffLineNotSend(DbUtils db)
			throws SQLException {
		List<OffLineTicketData> offlineList = new ArrayList<OffLineTicketData>();
		try {
			offlineList = db.findAll(Selector.from(OffLineTicketData.class).where("isSend","=",0));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return offlineList;
	}

	
	@Override
	public void addOffLineTicket(DbUtils db, OffLineTicketData offline)
			throws SQLException {
		try {
			db.save(offline);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editOffLineTicket(DbUtils db, OffLineTicketData offline)
			throws SQLException {
		try {
			db.update(offline);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delOffLineTicket(DbUtils db, OffLineTicketData offline)
			throws SQLException {
		try {
			db.delete(offline);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

}
