package java.com.qc.dao;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "OfflineTicketTable")
public class OffLineTicketBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(column = "id")
	private String id;
	@Column(column = "shopid")
	private String shopid;
	@Column(column = "isSend")
	private int isSend;

	public OffLineTicketBean() {
		this.id = "";
		this.shopid = "";
		this.isSend = 0; 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

	@Override
	public String toString() {
		return "OffLineTicketBean [id=" + id + ", shopid=" + shopid + ", isSend=" + isSend + "]";
	}
}
