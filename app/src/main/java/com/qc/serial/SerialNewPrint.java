package com.qc.serial;

import com.qc.qchouse.MainActivity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialNewPrint {

	/**
	 * �µĴ�ӡ����Ʊ
	 * 
	 * @param quenenumber
	 *            �Ŷ�����
	 * @param ticketid
	 *            Ʊ��
	 */
	public static void newPrintTicket(String quenenumber, String ticketid) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String str = formatter.format(curDate);

		byte qchead[] = null;
		byte shopname[] = null;
		byte shopname1[] = null;
		byte ticketid1[] = null;
		byte ticketid2[] = null; // ֮���޸�����
		byte time1[] = null; // ��ǰʱ��
		byte time2[] = null; // ʱ��
		byte quene1[] = null; // �ŶӺ�
		byte quene2[] = null; // �ŶӺ�
		byte attention1[] = null;// ע��

		byte[] head = { 0x1b, 0x40, 0x1b, 0x33, 0x40, 0x1b, 0x61, 0x01, 0x1d, 0x21, 0x11 };
		byte[] head2 = { 0x1b, 0x33, 0x30, 0x1b, 0x61, 0x00, 0x1d, 0x21, 0x00 };
		byte[] ht = { 0x0A }; // ����
		byte[] first = { 0x1b, 0x40 }; // ��ʼ�� + �м��
		byte[] cute = { 0x1B, 0x69 }; // ��ֽ ȫ��
		byte[] space = { 0x1b, 0x4a, 0x05 }; // ��ֽ
		byte[] big = { 0x1d, 0x21, 0x11 }; // �����С 2���߿�
		byte[] spac = { 0x1b, 0x33, 0x30 }; // �м��

		byte[] spaceShop = { 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32 }; // �ո�
		byte[] spaceTicketId = { 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32 };
		// byte[] spaceTime = {32,32,32,32};
		byte[] spaceQuene = { 32, 32, 32, 32, 32, 32, 32, 32 };
		try {
			qchead = "�췢��Ʊ��".getBytes("GBK");
			shopname = "�ŵ����ƣ�".getBytes("GBK");
			byte[] byteShop = MainActivity.ShopName.getBytes("GBK");
			int lenShop = byteShop.length;
			int lenShopStart = spaceShop.length - lenShop;
			for (int i = lenShopStart, j = 0; i < spaceShop.length; i++, j++) {
				spaceShop[i] = byteShop[j];
			}
			shopname1 = spaceShop;// �ŵ�

			ticketid1 = "�ֽ�ƱƱ�ţ�".getBytes("GBK");
			byte[] byteTicketId = ticketid.getBytes("GBK");
			int lenTicketId = byteTicketId.length;
			int lenTicketIdStart = spaceTicketId.length - lenTicketId;
			for (int i = lenTicketIdStart, j = 0; i < spaceTicketId.length; i++, j++) {
				spaceTicketId[i] = byteTicketId[j];
			}
			ticketid2 = spaceTicketId;

			time1 = "��Ʊʱ�䣺".getBytes("GBK");
			String spaceTime = "                      ";
			int lenSpaceTime = spaceTime.length();
			int lenTime = str.length();
			StringBuffer bufferTime = new StringBuffer(spaceTime);
			bufferTime.replace(lenSpaceTime - lenTime, lenSpaceTime, str);
			time2 = bufferTime.toString().getBytes("GBK");

			quene1 = "�ŶӺţ�".getBytes("GBK");
			byte[] byteQueneNumber = quenenumber.getBytes("GBK");
			int lenQueneNumber = byteQueneNumber.length;
			int lenQueneNumberStart = spaceQuene.length - lenQueneNumber;
			for (int i = lenQueneNumberStart, j = 0; i < spaceQuene.length; i++, j++) {
				spaceQuene[i] = byteQueneNumber[j];
			}
			quene2 = spaceQuene;
			attention1 = "ע�⣺�����Ʊ��ܱ�СƱ����ƱΪΨһƾ֤��������ʧ�������¹���".getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		int lenfirst = first.length;
		int lenht = ht.length;
		int lenbig = big.length;
		int lenspac = spac.length;
		int lenhead2 = head2.length;

		int lenh = head.length;
		int lenqc = qchead.length;
		int lenshop = shopname.length;
		int lenshop1 = shopname1.length;
		int lenticketid1 = ticketid1.length;
		int lenticketid2 = ticketid2.length;
		int lentime1 = time1.length;
		int lentime2 = time2.length;
		int lenquene1 = quene1.length;
		int lenquene2 = quene2.length;

		int lenattention1 = attention1.length;
		int lenspace = space.length;
		int lencute = cute.length;

		byte[] data3 = new byte[lenh + lenqc + lenspac + lenht + lenhead2 + lenspac + lenshop + lenshop1 + lenht
				+ lenticketid1 + lenticketid2 + lenht + lenht + lentime1 + lentime2 + lenht + lenbig + lenquene1
				+ lenquene2 + lenht];
		byte[][] data5 = { head, qchead, ht, head2, spac, shopname, shopname1, ht, ticketid1, ticketid2, ht, time1,
				time2, ht, big, quene1, quene2, ht };
		int desLen = 0;
		for (int i = 0; i < data5.length; i++) {
			System.arraycopy(data5[i], 0, data3, desLen, data5[i].length);
			desLen += data5[i].length;
		}

		if (MainActivity.ComPrint != null && MainActivity.ComPrint.isOpen()) {
			MainActivity.ComPrint.send(data3);
			MainActivity.ComPrint.send(ht);
		}

		cn.jelly.qrcode.util.QRCodeInfo codeInfo = new cn.jelly.qrcode.util.QRCodeInfo();
		codeInfo.setlMargin(13);
		codeInfo.setmSide(2);
		String QRCodeInfo = codeInfo.GetQRCode("1," + MainActivity.ShopId + "," + ticketid);
		String str1 = "";
		int cnt = 0;
		int iIndex = 0;
		int iValue = 0;
		byte[] cmd = new byte[1024];
		if (QRCodeInfo.substring(QRCodeInfo.length() - 1) != " ")
			QRCodeInfo = QRCodeInfo + " ";

		while (QRCodeInfo.length() > 0) {
			iIndex = QRCodeInfo.indexOf(" ");
			if (iIndex > 0) {
				str1 = QRCodeInfo.substring(0, iIndex);
				iValue = Integer.valueOf(str1, 16);

				cmd[cnt++] = (byte) iValue;
				QRCodeInfo = QRCodeInfo.substring(iIndex + 1);
			}
		}
		if (MainActivity.ComPrint != null && MainActivity.ComPrint.isOpen()) {
			MainActivity.ComPrint.send(cmd);
			MainActivity.ComPrint.send(ht);
		}

		byte[] data4 = new byte[lenfirst + lenattention1 + lenht];
		System.arraycopy(first, 0, data4, 0, lenfirst);
		System.arraycopy(attention1, 0, data4, lenfirst, lenattention1);
		System.arraycopy(ht, 0, data4, lenfirst + lenattention1, lenht);
		MainActivity.ComPrint.send(data4);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);

		byte[] cmd3 = new byte[2];
		cmd3[0] = 0x1B;
		cmd3[1] = 0x69; // ȫ��
		// cmd3[1] = 0x6d; //����
		if (MainActivity.ComPrint != null && MainActivity.ComPrint.isOpen()) {
			MainActivity.ComPrint.send(cmd3);
		}
	}

	/**
	 * �´�ӡ�����߳�Ʊ
	 */
	public static void newPrintLeaveTicket() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String str = formatter.format(curDate);

		byte qchead[] = null;
		byte shopname[] = null;
		byte shopname1[] = null;
		byte ticketid1[] = null;
		byte ticketid2[] = null; // ֮���޸�����
		byte people1[] = null; // ��ǰ����
		byte people2[] = null; // ��ǰ���� ֮���޸�
		byte time1[] = null; // ��ǰʱ��
		byte time2[] = null; // ʱ��
		byte quene1[] = null; // �ŶӺ�
		byte quene2[] = null; // �ŶӺ�
		byte attention1[] = null;// ע��

		byte[] head = { 0x1b, 0x40, 0x1b, 0x33, 0x40, 0x1b, 0x61, 0x01, 0x1d, 0x21, 0x11 };
		byte[] head2 = { 0x1b, 0x33, 0x30, 0x1b, 0x61, 0x00, 0x1d, 0x21, 0x00 };
		byte[] ht = { 0x0A }; // ����
		byte[] first = { 0x1b, 0x40 }; // ��ʼ�� + �м��
		byte[] cute = { 0x1B, 0x69 }; // ��ֽ ȫ��
		byte[] space = { 0x1b, 0x4a, 0x05 }; // ��ֽ
		byte[] big = { 0x1d, 0x21, 0x11 }; // �����С 2���߿�
		byte[] spac = { 0x1b, 0x33, 0x30 }; // �м��

		byte[] spaceShop = { 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32 }; // �ո�
		byte[] spaceTicketId = { 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32 };
		// byte[] spaceTime = {32,32,32,32};
		byte[] spaceQuene = { 32, 32, 32, 32, 32, 32 };

		try {
			qchead = "�췢��Ʊ��\n����Ʊ".getBytes("GBK");
			shopname = "�ŵ����ƣ�".getBytes("GBK");
			byte[] byteShop = MainActivity.ShopName.getBytes("GBK");
			int lenShop = byteShop.length;
			int lenShopStart = spaceShop.length - lenShop;
			for (int i = lenShopStart, j = 0; i < spaceShop.length; i++, j++) {
				spaceShop[i] = byteShop[j];
			}
			shopname1 = spaceShop;// �ŵ�
			ticketid1 = "����Ʊ�ţ�".getBytes("GBK");
			ticketid2 = ("                    " + "XX").getBytes("GBK");
			people1 = "��ǰ������".getBytes("GBK");
			people2 = ("                    " + "XX").getBytes("GBK");
			time1 = "��Ʊʱ�䣺".getBytes("GBK");
			String spaceTime = "                      ";
			int lenSpaceTime = spaceTime.length();
			int lenTime = str.length();
			StringBuffer bufferTime = new StringBuffer(spaceTime);
			bufferTime.replace(lenSpaceTime - lenTime, lenSpaceTime, str);
			time2 = bufferTime.toString().getBytes("GBK");
			quene1 = "�ŶӺţ�".getBytes("GBK");
			quene2 = ("      " + "XX").getBytes("GBK");
			attention1 = "ע�⣺�����Ʊ��ܱ�СƱ����ƱΪΨһƾ֤��������ʧ�������¹���".getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		int lenfirst = first.length;
		int lenht = ht.length;
		int lenbig = big.length;
		int lenspac = spac.length;
		int lenhead2 = head2.length;

		int lenh = head.length;
		int lenqc = qchead.length;
		int lenshop = shopname.length;
		int lenshop1 = shopname1.length;
		int lenticketid1 = ticketid1.length;
		int lenticketid2 = ticketid2.length;
		int lenpeople1 = people1.length;
		int lenpeople2 = people2.length;
		int lentime1 = time1.length;
		int lentime2 = time2.length;
		int lenquene1 = quene1.length;
		int lenquene2 = quene2.length;

		int lenattention1 = attention1.length;
		int lenspace = space.length;
		int lencute = cute.length;

		byte[] data3 = new byte[lenh + lenqc + lenspac + lenht + lenhead2 + lenspac + lenshop + lenshop1 + lenht
				+ lenticketid1 + lenticketid2 + lenht + lenpeople1 + lenpeople2 + lenht + lentime1 + lentime2 + lenht
				+ lenbig + lenquene1 + lenquene2 + lenht];
		byte[][] data5 = { head, qchead, ht, head2, spac, shopname, shopname1, ht, ticketid1, ticketid2, ht, people1,
				people2, ht, time1, time2, ht, big, quene1, quene2, ht };
		int desLen = 0;
		for (int i = 0; i < data5.length; i++) {
			System.arraycopy(data5[i], 0, data3, desLen, data5[i].length);
			desLen += data5[i].length;
		}

		if (MainActivity.ComPrint != null && MainActivity.ComPrint.isOpen()) {
			MainActivity.ComPrint.send(data3);
			MainActivity.ComPrint.send(ht);
		}

		byte[] data4 = new byte[lenfirst + lenattention1 + lenht];
		System.arraycopy(first, 0, data4, 0, lenfirst);
		System.arraycopy(attention1, 0, data4, lenfirst, lenattention1);
		System.arraycopy(ht, 0, data4, lenfirst + lenattention1, lenht);
		MainActivity.ComPrint.send(data4);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);

		byte[] cmd3 = new byte[2];
		cmd3[0] = 0x1B;
		cmd3[1] = 0x69;
		// cmd3[1] = 0x6d;
		if (MainActivity.ComPrint != null && MainActivity.ComPrint.isOpen()) {
			MainActivity.ComPrint.send(cmd3);
		}
	}

	/**
	 * �´�ӡ��ɨ���Ʊ
	 * 
	 * @param str
	 *            ��Ʊʱ��
	 * @param quenenumber
	 *            �Ŷ�����
	 * @param ticketid
	 *            Ʊ��
	 */
	public static void newPrintScaneTicket(String str, String quenenumber, String ticketid) {

		byte qchead[] = null;
		byte shopname[] = null;
		byte shopname1[] = null;
		byte ticketid1[] = null;
		byte ticketid2[] = null; // ֮���޸�����
		byte time1[] = null; // ��ǰʱ��
		byte time2[] = null; // ʱ��
		byte quene1[] = null; // �ŶӺ�
		byte quene2[] = null; // �ŶӺ�
		byte attention1[] = null;// ע��

		byte[] head = { 0x1b, 0x40, 0x1b, 0x33, 0x40, 0x1b, 0x61, 0x01, 0x1d, 0x21, 0x11 };
		byte[] head2 = { 0x1b, 0x33, 0x30, 0x1b, 0x61, 0x00, 0x1d, 0x21, 0x00 };
		byte[] ht = { 0x0A }; // ����
		byte[] first = { 0x1b, 0x40 }; // ��ʼ�� + �м��
		byte[] cute = { 0x1B, 0x69 }; // ��ֽ ȫ��
		byte[] space = { 0x1b, 0x4a, 0x05 }; // ��ֽ
		byte[] big = { 0x1d, 0x21, 0x11 }; // �����С 2���߿�
		byte[] spac = { 0x1b, 0x33, 0x30 }; // �м��

		byte[] spaceShop = { 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32 }; // �ո�
		byte[] spaceTicketId = { 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32,
				32 };
		// byte[] spaceTime = {32,32,32,32};
		byte[] spaceQuene = { 32, 32, 32, 32, 32, 32, 32, 32 };

		try {
			qchead = "�췢��Ʊ��\nɨ��Ʊ".getBytes("GBK");
			shopname = "�ŵ����ƣ�".getBytes("GBK");
			byte[] byteShop = MainActivity.ShopName.getBytes("GBK");
			int lenShop = byteShop.length;
			int lenShopStart = spaceShop.length - lenShop;
			for (int i = lenShopStart, j = 0; i < spaceShop.length; i++, j++) {
				spaceShop[i] = byteShop[j];
			}
			shopname1 = spaceShop;// �ŵ�

			ticketid1 = "ɨ��Ʊ�ţ�".getBytes("GBK");
			byte[] byteTicketId = ticketid.getBytes("GBK");
			int lenTicketId = byteTicketId.length;
			int lenTicketIdStart = spaceTicketId.length - lenTicketId;
			for (int i = lenTicketIdStart, j = 0; i < spaceTicketId.length; i++, j++) {
				spaceTicketId[i] = byteTicketId[j];
			}
			ticketid2 = spaceTicketId;

			time1 = "��Ʊʱ�䣺".getBytes("GBK");
			String spaceTime = "                      ";
			int lenSpaceTime = spaceTime.length();
			int lenTime = str.length();
			StringBuffer bufferTime = new StringBuffer(spaceTime);
			bufferTime.replace(lenSpaceTime - lenTime, lenSpaceTime, str);
			time2 = bufferTime.toString().getBytes("GBK");

			quene1 = "�ŶӺţ�".getBytes("GBK");
			byte[] byteQueneNumber = quenenumber.getBytes("GBK");
			int lenQueneNumber = byteQueneNumber.length;
			int lenQueneNumberStart = spaceQuene.length - lenQueneNumber;
			for (int i = lenQueneNumberStart, j = 0; i < spaceQuene.length; i++, j++) {
				spaceQuene[i] = byteQueneNumber[j];
			}
			quene2 = spaceQuene;
			attention1 = "ע�⣺�����Ʊ��ܱ�СƱ����ƱΪΨһƾ֤��������ʧ�������¹���".getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		int lenfirst = first.length;
		int lenht = ht.length;
		int lenbig = big.length;
		int lenspac = spac.length;
		int lenhead2 = head2.length;

		int lenh = head.length;
		int lenqc = qchead.length;
		int lenshop = shopname.length;
		int lenshop1 = shopname1.length;
		int lenticketid1 = ticketid1.length;
		int lenticketid2 = ticketid2.length;
		int lentime1 = time1.length;
		int lentime2 = time2.length;
		int lenquene1 = quene1.length;
		int lenquene2 = quene2.length;

		int lenattention1 = attention1.length;
		int lenspace = space.length;
		int lencute = cute.length;

		byte[] data3 = new byte[lenh + lenqc + lenspac + lenht + lenhead2 + lenspac + lenshop + lenshop1 + lenht
				+ lenticketid1 + lenticketid2 + lenht + lenht + lentime1 + lentime2 + lenht + lenbig + lenquene1
				+ lenquene2 + lenht];
		byte[][] data5 = { head, qchead, ht, head2, spac, shopname, shopname1, ht, ticketid1, ticketid2, ht, time1,
				time2, ht, big, quene1, quene2, ht };
		int desLen = 0;
		for (int i = 0; i < data5.length; i++) {
			System.arraycopy(data5[i], 0, data3, desLen, data5[i].length);
			desLen += data5[i].length;
		}

		if (MainActivity.ComPrint != null && MainActivity.ComPrint.isOpen()) {
			MainActivity.ComPrint.send(data3);
			MainActivity.ComPrint.send(ht);
		}

		cn.jelly.qrcode.util.QRCodeInfo codeInfo = new cn.jelly.qrcode.util.QRCodeInfo();
		codeInfo.setlMargin(13);
		codeInfo.setmSide(2);
		String QRCodeInfo = codeInfo.GetQRCode("1," + MainActivity.ShopId + "," + ticketid);
		String str1 = "";
		int cnt = 0;
		int iIndex = 0;
		int iValue = 0;
		byte[] cmd = new byte[1024];
		if (QRCodeInfo.substring(QRCodeInfo.length() - 1) != " ")
			QRCodeInfo = QRCodeInfo + " ";

		while (QRCodeInfo.length() > 0) {
			iIndex = QRCodeInfo.indexOf(" ");
			if (iIndex > 0) {
				str1 = QRCodeInfo.substring(0, iIndex);
				iValue = Integer.valueOf(str1, 16);

				cmd[cnt++] = (byte) iValue;
				QRCodeInfo = QRCodeInfo.substring(iIndex + 1);
			}
		}
		if (MainActivity.ComPrint != null && MainActivity.ComPrint.isOpen()) {
			MainActivity.ComPrint.send(cmd);
			MainActivity.ComPrint.send(ht);
		}

		byte[] data4 = new byte[lenfirst + lenattention1 + lenht];
		System.arraycopy(first, 0, data4, 0, lenfirst);
		System.arraycopy(attention1, 0, data4, lenfirst, lenattention1);
		System.arraycopy(ht, 0, data4, lenfirst + lenattention1, lenht);
		MainActivity.ComPrint.send(data4);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);
		MainActivity.ComPrint.send(ht);

		byte[] cmd3 = new byte[2];
		cmd3[0] = 0x1B;
		cmd3[1] = 0x69;
		// cmd3[1] = 0x6d;
		if (MainActivity.ComPrint != null && MainActivity.ComPrint.isOpen()) {
			MainActivity.ComPrint.send(cmd3);
		}
	}

}
