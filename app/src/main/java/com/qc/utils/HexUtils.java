package com.qc.utils;

import java.io.UnsupportedEncodingException;

public class HexUtils {

	private static String hexString = "0123456789ABCDEF";

	public static String encodeCN(String data) {
		byte[] bytes;
		try {
			bytes = data.getBytes("gbk");
			StringBuilder sb = new StringBuilder(bytes.length * 2);
			for (int i = 0; i < bytes.length; i++) {
				sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
				sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
				sb.append(" ");
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encodeStr(String data) {
		String result = "";
		byte[] b;
		try {
			b = data.getBytes("gbk");
			for (int i = 0; i < b.length; i++) {
				result += Integer.toString((b[i] & 0xff) + 0x100, 16)
						.substring(1);
				result += " ";
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// ʮ������ת������
	public String HToB(String a) {
		String b = Integer.toBinaryString(Integer.valueOf(toD(a, 16)));
		return b;
	}
	
	// ������תʮ������
	public String BToH(String a) {
		// ��������תΪʮ�����ٴ�ʮ����תΪʮ������
		String b = Integer.toHexString(Integer.valueOf(toD(a, 2)));
		return b;
	}

	// ���������תΪʮ������
	public String toD(String a, int b) {
		int r = 0;
		for (int i = 0; i < a.length(); i++) {
			r = (int) (r + formatting(a.substring(i, i + 1))
					* Math.pow(b, a.length() - i - 1));
		}
		return String.valueOf(r);
	}
	
	// ��ʮ�������е���ĸתΪ��Ӧ������
	public int formatting(String a) {
		int i = 0;
		for (int u = 0; u < 10; u++) {
			if (a.equals(String.valueOf(u))) {
				i = u;
			}
		}
		if (a.equals("a")) {
			i = 10;
		}
		if (a.equals("b")) {
			i = 11;
		}
		if (a.equals("c")) {
			i = 12;
		}
		if (a.equals("d")) {
			i = 13;
		}
		if (a.equals("e")) {
			i = 14;
		}
		if (a.equals("f")) {
			i = 15;
		}
		return i;
	}

	public static boolean isCN(String data) {
		boolean flag = false;
		String regex = "^[\u4e00-\u9fa5]*$";
		if (data.matches(regex)) {
			flag = true;
		}
		return flag;
	}

	public static String getHexResult(String targetStr) {
		StringBuilder hexStr = new StringBuilder();
		int len = targetStr.length();
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				char tempStr = targetStr.charAt(i);
				String data = String.valueOf(tempStr);
				if (isCN(data)) {
					hexStr.append(encodeCN(data));
				} else {
					hexStr.append(encodeStr(data));
				}
			}
		}
		return hexStr.toString();
	}
	
	// ���ô�ӡģʽ
	public static byte[] SetPrintModel(int iWidth, int iHeight) {
		int N = iHeight + iWidth * 0x10;
		String tempStr = "1D 21 " + Integer.toHexString(N);
		byte[] sBuffer = hexStr2Bytesnoenter(tempStr);
		return sBuffer;
	}

	public static byte[] hexStr2Bytesnoenter(String src) {
		String[] src_sp = src.split(" ");
		int leng = src_sp.length;
		byte[] ret = new byte[leng + 3];
		for (int i = 0; i < leng; i++) {
			ret[i] = Integer.decode("0x" + src_sp[i]).byteValue();
		}
		return ret;
	}
	
	/**   
	 * �ַ���ת����ʮ�������ַ���  
	 * @param String str ��ת����ASCII�ַ���  
	 * @return String ÿ��Byte֮��ո�ָ�����: [61 6C 6B]  
	 */      
	public static String str2HexStr(String str)    
	{      
	  
	    char[] chars = "0123456789ABCDEF".toCharArray();      
	    StringBuilder sb = new StringBuilder("");    
	    byte[] bs = str.getBytes();      
	    int bit;      
	        
	    for (int i = 0; i < bs.length; i++)    
	    {      
	        bit = (bs[i] & 0x0f0) >> 4;      
	        sb.append(chars[bit]);      
	        bit = bs[i] & 0x0f;      
	        sb.append(chars[bit]);    
	        sb.append(' ');    
	    }      
	    return sb.toString().trim();      
	}    
	    
	/**   
	 * ʮ������ת���ַ���  
	 * @param String str Byte�ַ���(Byte֮���޷ָ��� ��:[616C6B])  
	 * @return String ��Ӧ���ַ���  
	 */      
	public static String hexStr2Str(String hexStr)    
	{      
	    String str = "0123456789ABCDEF";      
	    char[] hexs = hexStr.toCharArray();      
	    byte[] bytes = new byte[hexStr.length() / 2];      
	    int n;      
	  
	    for (int i = 0; i < bytes.length; i++)    
	    {      
	        n = str.indexOf(hexs[2 * i]) * 16;      
	        n += str.indexOf(hexs[2 * i + 1]);      
	        bytes[i] = (byte) (n & 0xff);      
	    }      
	    return new String(bytes);      
	}    
	    
	/**  
	 * bytesת����ʮ�������ַ���  
	 * @param byte[] b byte����  
	 * @return String ÿ��Byteֵ֮��ո�ָ�  
	 */    
	public static String byte2HexStr(byte[] b)    
	{    
	    String stmp="";    
	    StringBuilder sb = new StringBuilder("");    
	    for (int n=0;n<b.length;n++)    
	    {    
	        stmp = Integer.toHexString(b[n] & 0xFF);    
	        sb.append((stmp.length()==1)? "0"+stmp : stmp);    
	        sb.append(" ");    
	    }    
	    return sb.toString().toUpperCase().trim();    
	}    
	    
	/**  
	 * bytes�ַ���ת��ΪByteֵ  
	 * @param String src Byte�ַ�����ÿ��Byte֮��û�зָ���  
	 * @return byte[]  
	 */    
	public static byte[] hexStr2Bytes(String src)    
	{    
	    int m=0,n=0;    
	    int l=src.length()/2;    
	    System.out.println(l);    
	    byte[] ret = new byte[l];    
	    for (int i = 0; i < l; i++)    
	    {    
	        m=i*2+1;    
	        n=m+1;    
	        ret[i] = Byte.decode("0x" + src.substring(i*2, m) + src.substring(m,n));    
	    }    
	    return ret;    
	}    
	  
	/**  
	 * String���ַ���ת����unicode��String  
	 * @param String strText ȫ���ַ���  
	 * @return String ÿ��unicode֮���޷ָ���  
	 * @throws Exception  
	 */    
	public static String strToUnicode(String strText)    
	    throws Exception    
	{    
	    char c;    
	    StringBuilder str = new StringBuilder();    
	    int intAsc;    
	    String strHex;    
	    for (int i = 0; i < strText.length(); i++)    
	    {    
	        c = strText.charAt(i);    
	        intAsc = (int) c;    
	        strHex = Integer.toHexString(intAsc);    
	        if (intAsc > 128)    
	            str.append("\\u" + strHex);    
	        else // ��λ��ǰ�油00    
	            str.append("\\u00" + strHex);    
	    }    
	    return str.toString();    
	}    
	    
	/**  
	 * unicode��Stringת����String���ַ���  
	 * @param String hex 16����ֵ�ַ��� ��һ��unicodeΪ2byte��  
	 * @return String ȫ���ַ���  
	 */    
	public static String unicodeToString(String hex)    
	{    
	    int t = hex.length() / 6;    
	    StringBuilder str = new StringBuilder();    
	    for (int i = 0; i < t; i++)    
	    {    
	        String s = hex.substring(i * 6, (i + 1) * 6);    
	        // ��λ��Ҫ����00��ת    
	        String s1 = s.substring(2, 4) + "00";    
	        // ��λֱ��ת    
	        String s2 = s.substring(4);    
	        // ��16���Ƶ�stringתΪint    
	        int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);    
	        // ��intת��Ϊ�ַ�    
	        char[] chars = Character.toChars(n);    
	        str.append(new String(chars));    
	    }    
	    return str.toString();    
	}   
	
	/**
	 * �ӻ�ȡ��������ת��
	 * @param string
	 * @return
	 */
	public static int changeASCIItoNum(String string){
		if("30".equals(string)){
			return 0;
		}else if("31".equals(string)){
			return 1;
		}else if("32".equals(string)){
			return 2;
		}else if("33".equals(string)){
			return 3;
		}else if("34".equals(string)){
			return 4;
		}else if("35".equals(string)){
			return 5;
		}else if("36".equals(string)){
			return 6;
		}else if("37".equals(string)){
			return 7;
		}else if("38".equals(string)){
			return 8;
		}else if("39".equals(string)){
			return 9;
		}else if("41".equals(string)){
			return 10;
		}else if("42".equals(string)){
			return 11;
		}else if("43".equals(string)){
			return 12;
		}else if("44".equals(string)){
			return 13;
		}else if("45".equals(string)){
			return 14;
		}
			
		while(!string.equals("46"));
		return 15;
	}
	
}
