package com.qc.dao;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "chairs")
public class ChairData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id(column = "id")
	private int id;
	@Column(column = "chairName")
	private String chairName;  //��������
	@Column(column = "StartTime")
	private String StartTime;  //��ʼʱ��
	@Column(column = "EndTime")
	private String EndTime;  //����ʱ��
	@Column(column = "Number")
	private int Number;  //���Ӽ���
	@Column(column = "isSend")
	private int isSend; //�Ƿ��ϴ� 0û���ϴ� 1���ϴ�
	
	public ChairData() {
		this.chairName = "";
		this.StartTime = "";
		this.EndTime = "";
		this.Number = 0;
		this.isSend = 0;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}

	public String getChairName() {
		return chairName;
	}

	public void setChairName(String chairName) {
		this.chairName = chairName;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public int getNumber() {
		return Number;
	}

	public void setNumber(int number) {
		Number = number;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}
	
}
