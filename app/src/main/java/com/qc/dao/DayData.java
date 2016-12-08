package com.qc.dao;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "Day")
public class DayData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id(column = "id")
	private int id;
	@Column(column = "day")
	private String day;
	@Column(column = "number")
	private int number;
	@Column(column = "addNumber")
	private int addNumber;
	@Column(column = "isSend")
	private int isSend;  // 0Ϊδ����  1Ϊ���ͳ�
	
	public DayData() {
		this.day = "";
		this.number = 0;
		this.addNumber = 0;
		this.isSend = 0;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getAddNumber() {
		return addNumber;
	}

	public void setAddNumber(int addNumber) {
		this.addNumber = addNumber;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}
	
}
