package java.com.qc.dao;

import android.database.SQLException;

import com.lidroid.xutils.DbUtils;

import java.util.List;

public interface OffLineTicketDao {

	public OffLineTicketData findOffLineById(DbUtils db, int id) throws SQLException;
	
	public OffLineTicketData findOffLineByDay(DbUtils db, String day) throws SQLException;
	//��������δ���͵�����Ʊ
	public List<OffLineTicketData> findOffLineNotSend(DbUtils db) throws SQLException;
	
	
	// ���
	public void addOffLineTicket(DbUtils db, OffLineTicketData offline) throws SQLException;
	// �޸�
	public void editOffLineTicket(DbUtils db, OffLineTicketData offline) throws SQLException;
	// ɾ��
	public void delOffLineTicket(DbUtils db, OffLineTicketData offline) throws SQLException;
	
}
