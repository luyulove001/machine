package com.qc.dao;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "OffLineTicket")
public class OffLineTicketData implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id(column = "id")
	private int id;
	@Column(column = "day")
	private String day;
	@Column(column = "offlineNumber")
	private int offlineNumber; //���������� ����
	@Column(column = "allNumber")
	private int allNumber;
	@Column(column = "isSend")
	private int isSend; 
	
	public OffLineTicketData() {
		this.day = "";
		this.offlineNumber = 0;
		this.allNumber = 0;
		this.isSend = 0;  //0û�з���, 1����
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getOfflineNumber() {
		return offlineNumber;
	}

	public void setOfflineNumber(int offlineNumber) {
		this.offlineNumber = offlineNumber;
	}

	public int getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(int allNumber) {
		this.allNumber = allNumber;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

	@Override
	public String toString() {
		return "OffLineTicketData [id=" + id + ", day=" + day + ", offlineNumber=" + offlineNumber + ", allNumber="
				+ allNumber + ", isSend=" + isSend + "]";
	}
}
