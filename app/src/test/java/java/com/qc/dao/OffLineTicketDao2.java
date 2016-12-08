package java.com.qc.dao;

import android.database.SQLException;

import com.lidroid.xutils.DbUtils;

import java.util.List;

public interface OffLineTicketDao2 {

	public OffLineTicketBean findOffLineById(DbUtils db, String id) throws SQLException;

	public List<OffLineTicketBean> findOffLineByDay(DbUtils db, String id) throws SQLException;
	//��������δ���͵�����Ʊ
	public List<OffLineTicketBean> findOffLineNotSend(DbUtils db) throws SQLException;


	// ���
	public void addOffLineTicket(DbUtils db, OffLineTicketBean offline) throws SQLException;
	// �޸�
	public void editOffLineTicket(DbUtils db, OffLineTicketBean offline) throws SQLException;
	// ɾ��
	public void delOffLineTicket(DbUtils db, OffLineTicketBean offline) throws SQLException;
	
}
