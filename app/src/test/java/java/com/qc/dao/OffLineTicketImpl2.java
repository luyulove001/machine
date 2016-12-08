package java.com.qc.dao;

import android.database.SQLException;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class OffLineTicketImpl2 implements OffLineTicketDao2 {

	@Override
	public OffLineTicketBean findOffLineById(DbUtils db, String id)
			throws SQLException {
		OffLineTicketBean offline = new OffLineTicketBean();
		try {
			offline = db.findFirst(Selector.from(OffLineTicketBean.class).where("id","=",id));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return offline;
	}

	@Override
	public List<OffLineTicketBean> findOffLineByDay(DbUtils db, String id)
			throws SQLException {
		List<OffLineTicketBean> offline = new ArrayList<OffLineTicketBean>();
		try {
			offline = db.findFirst(Selector.from(OffLineTicketBean.class).where("id","like",id));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return offline;
	}

	@Override
	public List<OffLineTicketBean> findOffLineNotSend(DbUtils db)
			throws SQLException {
		List<OffLineTicketBean> offlineList = new ArrayList<OffLineTicketBean>();
		try {
			offlineList = db.findAll(Selector.from(OffLineTicketBean.class).where("isSend","=",0));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return offlineList;
	}


	@Override
	public void addOffLineTicket(DbUtils db, OffLineTicketBean offline)
			throws SQLException {
		try {
			db.save(offline);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editOffLineTicket(DbUtils db, OffLineTicketBean offline)
			throws SQLException {
		try {
			db.update(offline);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delOffLineTicket(DbUtils db, OffLineTicketBean offline)
			throws SQLException {
		try {
			db.delete(offline);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

}
