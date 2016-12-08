package com.qc.utils;

public class Constans {
	
	public final static String SOCKET_SERVER = "114.55.41.68";// ��ʽ��������ַ
//	public final static String SOCKET_SERVER = "shop.qchouses.com";// ���Է�������ַ
//	public final static String SOCKET_SERVER = "139.224.65.120";// ���Է�������ַ
	public final static int SOCKET_PORT = 1234;// ������TCP�˿�
	public final static int SOCKET_TIMOUT = 60 * 1000;// Ĭ��timeout ʱ�� 60s
	public final static int SOCKET_READ_TIMOUT = 20 * 1000;// ��ȡ��ʱ����
	public final static int SOCKET_SLEEP_SECOND = 3;// ���Ӳ����õ�����£����̵߳ȴ�ʱ�䣨�룩
	public final static int SOCKET_HEART_SECOND = 10;// ���������ͼ��ʱ�䣨�룩
	
//	public static String URL="https://shop.qchouses.com/api/machine/";  //https://mx.qchouses.com/api/machine/
	public static String URL="https://mx.qchouses.com/api/machine/";  //https://mx.qchouses.com/api/machine/
//	public static String COMMONURL = "https://shop.qchouses.com/api/";  //https://mx.qchouses.com/api/
	public static String COMMONURL = "https://mx.qchouses.com/api/";  //https://mx.qchouses.com/api/
	
	public static String getWeixinScaneTicket(String shopid){//΢��ɨ�빺Ʊ  //����APPID  wxcbb066f5afb5e21c  ��ʽ  wxd8fc4feff1400e6e   shop==mx
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd8fc4feff1400e6e&redirect_uri=https%3A%2F%2Fmx.qchouses.com%2Fweixin%2Fhome%2Findex%2Fqchouse25%2Fmachine%2Findex.html&response_type=code&scope=snsapi_userinfo&state=" + shopid + "&connect_redirect=1#wechat_redirect"; //��ʽ
//		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcbb066f5afb5e21c&redirect_uri=http%3A%2F%2Fshop.qchouses.com%2Fweixin%2Fhome%2Findex%2Fqchouse25%2Fmachine%2Findex.html&response_type=code&scope=snsapi_userinfo&state=" + shopid + "&connect_redirect=1#wechat_redirect";//����
		return url;
	}

	public static String getWeixinScaneTouchTicket(String str){//΢��ɨ��ȡƱ
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd8fc4feff1400e6e&redirect_uri=https%3A%2F%2Fmx.qchouses.com%2Fweixin%2Fhome%2Findex%2Fqchouse25%2Ftaketicket%2Findex.html&response_type=code&scope=snsapi_userinfo&state=" + str + "&connect_redirect=1#wechat_redirect";//��ʽ
//		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcbb066f5afb5e21c&redirect_uri=http%3A%2F%2Fshop.qchouses.com%2Fweixin%2Fhome%2Findex%2Fqchouse25%2Ftaketicket%2Findex.html&response_type=code&scope=snsapi_userinfo&state=" + str + "&connect_redirect=1#wechat_redirect";//����
		return url;
	}
	
	public static final String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	public static final String accessKeyId = "h8L9pZWaH1Clpj8j";
	public static final String accessKeySecret = "syiH5Ns0zcF2ws7rAgETlwt1YJl3WQ";
	public static final String testBucket = "qc-video";
	
//	public static final String QCKEY = MD5.get32MD5("little 8 is handsome in qchouses.com company");//c75cc8f52146a1394536399a37febdb1
	public static final String QCKEY = "c75cc8f52146a1394536399a37febdb1";
	
	public static String getWebUrl(String shopid){
		String url = "https://mx.qchouses.com/weixin/home/index/advertising/index.html?shopid=" + shopid + "&qc_key=c75cc8f52146a1394536399a37febdb1";
//		String url = "http://shop.qchouses.com/weixin/home/index/advertising/index.html?shopid=" + shopid + "&qc_key=c75cc8f52146a1394536399a37febdb1";
		return url;
	}
}
