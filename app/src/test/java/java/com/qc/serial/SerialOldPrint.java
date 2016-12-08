package java.com.qc.serial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.lvrenyang.pos.Cmd;
import com.lvrenyang.utils.DataUtils;


import java.com.lvernyang.myprinter.Global;
import java.com.lvernyang.myprinter.WorkService;
import java.com.qc.qchouse.MainActivity;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialOldPrint {

	/**
	 * ������Ʊ
	 * @param quenenumber �Ŷ�����
	 * @param ticketid Ʊ��
	 */
	public static void pushTicket(Context context,String quenenumber, String ticketid){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String str = formatter.format(curDate);

		byte head1[] = null;
		byte shopname1[] = null;
		byte order1[] = null;
		byte people1[] = null;
		byte time1[] = null;
		byte attention1[] = null;
		byte ticketid1[] = null;

		byte shopname[] = null;
		//�������
		byte order[] = null;
		//��ǰʱ��
		byte time[] = null;
		//��ά��ƱID
		byte ticketid3[] = null;
		//ƱID
		byte ticketid2[] = null;

		byte[] spaceShop = {32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32}; //�ո�
		byte[] spaceTicketId = {32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32};
		byte[] spaceTime = {32,32,32};
		byte[] spaceQuene = {32,32,32,32,32,32,32,32};

		try {
			head1 = "�췢��Ʊ��".getBytes("GBK");
			shopname1 = "�ŵ����ƣ�".getBytes("GBK");
			byte[] byteShop = MainActivity.ShopName.getBytes("GBK");
			int lenShop = byteShop.length;
			int lenShopStart = spaceShop.length - lenShop;
			for(int i = lenShopStart, j = 0; i < spaceShop.length; i++,j++){
				spaceShop[i] = byteShop[j];
			}
			shopname = spaceShop;//�ŵ�

			order1 = "�ŶӺţ�".getBytes("GBK");
			time1 = "��Ʊʱ�䣺".getBytes("GBK");
			ticketid1 = "�ֽ�ƱƱ�ţ�".getBytes("GBK");
			attention1 = "ע�⣺�����Ʊ��ܱ�СƱ����ƱΪΨһƾ֤��������ʧ�������¹���".getBytes("GBK");

			byte[] byteQueneNumber = quenenumber.getBytes("GBK");
			int lenQueneNumber = byteQueneNumber.length;
			int lenQueneNumberStart = spaceQuene.length - lenQueneNumber;
			for(int i = lenQueneNumberStart, j = 0; i < spaceQuene.length; i++,j++){
				spaceQuene[i] = byteQueneNumber[j];
			}
			order = spaceQuene; //�ŶӺ�
			time = str.getBytes("GBK");

			byte[] byteTicketId = ticketid.getBytes("GBK");
			int lenTicketId = byteTicketId.length;
			int lenTicketIdStart = spaceTicketId.length - lenTicketId;
			for(int i = lenTicketIdStart, j = 0; i < spaceTicketId.length; i++,j++){
				spaceTicketId[i] = byteTicketId[j];
			}
			ticketid2 = spaceTicketId; //ƱID
			ticketid3 = ("1," + MainActivity.ShopId + "," + ticketid).getBytes("GBK"); //��ά��

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//head
		byte[] head = {0x1b,0x61,0x01};
		//��ͷ2
		byte[] head2 = {0x1b,0x44,0x14,0x00};
		//��ʼ����ӡ��+�Ʊ�
		byte[] setHT = {0x1b,0x44,0x18,0x00};
		//����һ����λ
		byte[] HT = {0x09};
		//��Ч
		byte[] GT = {0x08};
		byte[] FT = {0x04};
		//����
		byte[] LF = {0x0d,0x0a};
		//ȫ��
		byte[] cute = {0x1b, 0x69};
		//����
		byte[] halfcute = {0x1b,0x6d};

		byte[] cute1 = {0x1d, 0x56, 0x79, 0x00};

		byte[] twocode = new byte[8 + ticketid3.length];//��ά��
		twocode[0] = (byte)(0x1d);
		twocode[1] = (byte)(0x28);
		twocode[2] = (byte)(0x6b);
		twocode[3] = (byte)(3 + ticketid3.length);
		twocode[4] = (byte)(0x00);
		twocode[5] = (byte)(0x31);
		twocode[6] = (byte)(0x50);
		twocode[7] = (byte)(0x30);
		for(int i = 1; i<= ticketid3.length;i ++){
			twocode[7+i] = ticketid3[i-1];
		}

		byte[][] allbuf = new byte[][]{
			{0x1b,0x40},{0x1d,0x21,0x11},head,head1,LF, //�췢��Ʊ��
			{0x1b,0x40},{0x1b,0x44,0x08,0x00},shopname1,HT,shopname,LF, // �ŵ�����
			{0x1b,0x44,0x18,0x00},ticketid1,ticketid2,LF,      // Ʊ��
			{0x1b,0x44,0x18,0x00},time1,spaceTime,time,LF,      // ʱ��
			{0x1b,0x44,0x18,0x00},{0x1d,0x21,0x11},order1,order,LF,   //�ŶӺ�

			//��ά��
			{0x1b,0x40},//��ʼ��
			{0x1d,0x28,0x6b,0x03,0x00,0x31,0x43,0x09},//0x09Ϊ��ά���С����Χ0<=n<=16,16����0123456789ABCDEF10
			{0x1d,0x28,0x6b,0x03,0x00,0x31,0x45,0x30},
//			{0x1d,0x28,0x6b,0x07,0x00,0x31,0x50,0x30,0x35,0x32,0x35,0x32},
			twocode,
			{0x1b,0x61,0x01}, //����
			{0x1d,0x28,0x6b,0x03,0x00,0x31,0x52,0x30},
			{0x1d,0x28,0x6b,0x03,0x00,0x31,0x51,0x30},

			{0x1b,0x40},{0x1b,0x44,0x18,0x00},attention1,LF,   //ע������
			LF,LF,LF,LF,
			"\r\n".getBytes(),cute
		};

		byte[] buf = DataUtils.byteArraysToBytes(allbuf);
		if (null == WorkService.workThread) {
			Intent intent = new Intent(context, WorkService.class);
			context.startService(intent);
		}
		if (WorkService.workThread.isConnected()) {
			Bundle data = new Bundle();
			data.putByteArray(Global.BYTESPARA1, buf);
			data.putInt(Global.INTPARA1, 0);
			data.putInt(Global.INTPARA2, buf.length);
			data.putInt(Global.INTPARA3, 1);
			data.putInt(Global.INTPARA4, 1);
			data.putInt(Global.INTPARA5, Cmd.Constant.FONTSTYLE_BOLD);
			WorkService.workThread.handleCmd(Global.CMD_POS_WRITE, data);
		}
	}

	/**
	 * ɨ��ȡƱ
	 * @param str ʱ��
	 * @param quenenumber �Ŷ�����
	 * @param ticketid Ʊ��
	 */
	public static void pushJPushTicket(Context context,String str,String quenenumber,String ticketid){

		byte head1[] = null;
		byte weixin[] = null;
		byte shopname1[] = null;
		byte order1[] = null;
		byte time1[] = null;
		byte attention1[] = null;
		byte ticketid1[] = null;

		byte shopname[] = null;
		//�������
		byte order[] = null;
		//��ǰʱ��
		byte time[] = null;
		//��ά��Ʊ
		byte ticketid3[] = null;
		//ƱID
		byte ticketid2[] = null;

		byte[] spaceShop = {32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32}; //�ո�
		byte[] spaceTicketId = {32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32};
		byte[] spaceTime = {32,32,32};
		byte[] spaceQuene = {32,32,32,32,32,32,32,32};

		try {
			head1 = "�췢��Ʊ��".getBytes("GBK");
			weixin = "ɨ��Ʊ".getBytes("GBK");
			shopname1 = "�ŵ����ƣ�".getBytes("GBK");
			byte[] byteShop = MainActivity.ShopName.getBytes("GBK");
			int lenShop = byteShop.length;
			int lenShopStart = spaceShop.length - lenShop;
			for(int i = lenShopStart, j = 0; i < spaceShop.length; i++,j++){
				spaceShop[i] = byteShop[j];
			}
			shopname = spaceShop;//�ŵ�

			order1 = "�ŶӺţ�".getBytes("GBK");
			time1 = "��Ʊʱ�䣺".getBytes("GBK");
			ticketid1 = "ɨ��Ʊ�ţ�".getBytes("GBK");
			attention1 = "ע�⣺�����Ʊ��ܱ�СƱ����ƱΪΨһƾ֤��������ʧ�������¹���".getBytes("GBK");

			byte[] byteQueneNumber = quenenumber.getBytes("GBK");
			int lenQueneNumber = byteQueneNumber.length;
			int lenQueneNumberStart = spaceQuene.length - lenQueneNumber;
			for(int i = lenQueneNumberStart, j = 0; i < spaceQuene.length; i++,j++){
				spaceQuene[i] = byteQueneNumber[j];
			}
			order = spaceQuene; //�ŶӺ�
			time = str.getBytes("GBK");
			byte[] byteTicketId = ticketid.getBytes("GBK");
			int lenTicketId = byteTicketId.length;
			int lenTicketIdStart = spaceTicketId.length - lenTicketId;
			for(int i = lenTicketIdStart, j = 0; i < spaceTicketId.length; i++,j++){
				spaceTicketId[i] = byteTicketId[j];
			}
			ticketid2 = spaceTicketId; //ƱID
			ticketid3 = ("1," + MainActivity.ShopId + "," + ticketid).getBytes("GBK");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//head
		byte[] head = {0x1b,0x61,0x01};
		//��ͷ2
		byte[] head2 = {0x1b,0x44,0x14,0x00};
		//��ʼ����ӡ��+�Ʊ�
		byte[] setHT = {0x1b,0x44,0x18,0x00};
		//����һ����λ
		byte[] HT = {0x09};
		//��Ч
		byte[] GT = {0x08};
		byte[] FT = {0x04};
		//����
		byte[] LF = {0x0d,0x0a};
		//ȫ��
		byte[] cute = {0x1b, 0x69};
		//����
		byte[] halfcute = {0x1b,0x6d};

		byte[] cute1 = {0x1d, 0x56, 0x79, 0x00};

		byte[] twocode = new byte[8 + ticketid3.length];//��ά��
		twocode[0] = (byte)(0x1d);
		twocode[1] = (byte)(0x28);
		twocode[2] = (byte)(0x6b);
		twocode[3] = (byte)(3 + ticketid3.length);
		twocode[4] = (byte)(0x00);
		twocode[5] = (byte)(0x31);
		twocode[6] = (byte)(0x50);
		twocode[7] = (byte)(0x30);
		for(int i = 1; i<= ticketid3.length;i ++){
			twocode[7+i] = ticketid3[i-1];
		}

		byte[][] allbuf = new byte[][]{
			{0x1b,0x40},{0x1d,0x21,0x11},head,head1,LF, //�췢��Ʊ��
			head,weixin,LF, //΢��ɨ��
			{0x1b,0x40},{0x1b,0x44,0x08,0x00},shopname1,HT,shopname,LF, // �ŵ�����
			{0x1b,0x44,0x18,0x00},ticketid1,ticketid2,LF,      // Ʊ��
			{0x1b,0x44,0x18,0x00},time1,spaceTime,time,LF,      // ��Ʊʱ��
			{0x1b,0x44,0x18,0x00},{0x1d,0x21,0x11},order1,order,LF,   //�ŶӺ�

			//��ά��
			{0x1b,0x40},//��ʼ��
			{0x1d,0x28,0x6b,0x03,0x00,0x31,0x43,0x09},//0x09Ϊ��ά���С����Χ0<=n<=16,16����0123456789ABCDEF10
			{0x1d,0x28,0x6b,0x03,0x00,0x31,0x45,0x30},
//			{0x1d,0x28,0x6b,0x07,0x00,0x31,0x50,0x30,0x35,0x32,0x35,0x32},
			twocode,
			{0x1b,0x61,0x01}, //����
			{0x1d,0x28,0x6b,0x03,0x00,0x31,0x52,0x30},
			{0x1d,0x28,0x6b,0x03,0x00,0x31,0x51,0x30},

			{0x1b,0x40},{0x1b,0x44,0x18,0x00},attention1,LF,   //ע������
			LF,LF,LF,
			"\r\n".getBytes(),cute
		};

		byte[] buf = DataUtils.byteArraysToBytes(allbuf);
		if (null == WorkService.workThread) {
			Intent intent = new Intent(context, WorkService.class);
			context.startService(intent);
		}
		if (WorkService.workThread.isConnected()) {
			Bundle data = new Bundle();
			data.putByteArray(Global.BYTESPARA1, buf);
			data.putInt(Global.INTPARA1, 0);
			data.putInt(Global.INTPARA2, buf.length);
			data.putInt(Global.INTPARA3, 1);
			data.putInt(Global.INTPARA4, 1);
			data.putInt(Global.INTPARA5, Cmd.Constant.FONTSTYLE_BOLD);
			WorkService.workThread.handleCmd(Global.CMD_POS_WRITE, data);
		}
	}

	public static void PushLeaveTicket(Context context){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String str = formatter.format(curDate);

		byte head1[] = null;
		byte shopname1[] = null;
		byte order1[] = null;
		byte time1[] = null;
		byte attention1[] = null;
		byte ticketid1[] = null;

		byte shopname[] = null;
		//�������
		byte order[] = null;
		//��ǰ����
		byte people[] = null;
		//��ǰʱ��
		byte time[] = null;
		//ƱID
		byte ticketid3[] = null;

		byte blank[] = null;

		byte[] spaceShop = {32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32}; //�ո�
		byte[] spaceTicketId = {32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32};
		byte[] spaceTime = {32,32,32};
		byte[] spaceQuene = {32,32,32,32,32,32};

		try {
			head1 = "�췢��Ʊ��".getBytes("GBK");
			shopname1 = "�ŵ����ƣ�".getBytes("GBK");
			byte[] byteShop = MainActivity.ShopName.getBytes("GBK");
			int lenShop = byteShop.length;
			int lenShopStart = spaceShop.length - lenShop;
			for(int i = lenShopStart, j = 0; i < spaceShop.length; i++,j++){
				spaceShop[i] = byteShop[j];
			}
			shopname = spaceShop;//�ŵ�

			order1 = "�ŶӺţ�".getBytes("GBK");
			time1 = "��Ʊʱ�䣺".getBytes("GBK");
			ticketid1 = "����Ʊ�ţ�".getBytes("GBK");
			attention1 = "ע�⣺�����Ʊ��ܱ�СƱ����ƱΪΨһƾ֤��������ʧ�������¹���".getBytes("GBK");

			order = "����Ʊ".getBytes("GBK");
			people = "XX".getBytes("GBK");
			time = str.getBytes("GBK");
			ticketid3 = "XX".getBytes("GBK");
			blank = "  ".getBytes("GBK");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//head
		byte[] head = {0x1b,0x61,0x01};
		//��ͷ2
		byte[] head2 = {0x1b,0x44,0x14,0x00};
		//��ʼ����ӡ��+�Ʊ�
		byte[] setHT = {0x1b,0x44,0x18,0x00};
		//����һ����λ
		byte[] HT = {0x09};
		//��Ч
		byte[] GT = {0x08};
		byte[] FT = {0x04};
		//����
		byte[] LF = {0x0d,0x0a};
		//ȫ��
		byte[] cute = {0x1b, 0x69};
		//����
		byte[] halfcute = {0x1b,0x6d};

		byte[] cute1 = {0x1d, 0x56, 0x79, 0x00};

		byte[][] allbuf = new byte[][]{

			{0x1b,0x40},{0x1d,0x21,0x11},head,head1,LF, //�췢��Ʊ��
			order,LF,  //����Ʊ
			{0x1b,0x40},{0x1b,0x44,0x08,0x00},shopname1,shopname,LF, // �ŵ�����
			{0x1b,0x44,0x18,0x00},ticketid1,spaceTicketId,ticketid3,LF,      // ƱID
			{0x1b,0x44,0x18,0x00},time1,spaceTime,time,LF,      // ʱ��
			{0x1b,0x44,0x18,0x00},{0x1d,0x21,0x11},order1,spaceQuene,ticketid3,LF,   //�ŶӺ�
			{0x1b,0x40},{0x1b,0x44,0x18,0x00},attention1,LF,   //ע������
			LF,LF,LF,LF,
			"\r\n".getBytes(),cute
		};
			
		byte[] buf = DataUtils.byteArraysToBytes(allbuf);
		if (null == WorkService.workThread) {
			Intent intent = new Intent(context, WorkService.class);
			context.startService(intent);
		}
		if (WorkService.workThread.isConnected()) {
			Bundle data = new Bundle();
			data.putByteArray(Global.BYTESPARA1, buf);
			data.putInt(Global.INTPARA1, 0);
			data.putInt(Global.INTPARA2, buf.length);
			data.putInt(Global.INTPARA3, 1);
			data.putInt(Global.INTPARA4, 1);
			data.putInt(Global.INTPARA5, Cmd.Constant.FONTSTYLE_BOLD);
			WorkService.workThread.handleCmd(Global.CMD_POS_WRITE, data);
		} 
	}
	
}
