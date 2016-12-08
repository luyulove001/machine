package java.com.qc.tcp;

import android.util.Log;

import com.qc.qchouse.MainActivity;
import com.qc.utils.Constans;
import com.qc.utils.MD5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * NIO TCP �ͻ���
 */
public class TCPClient
{
	private static final String FORMATSTR = "yyyy-MM-dd";
	String SHOPID = "";
	static final String tag = "TCPClient";
	// �ŵ�ѡ����
	private Selector selector;
	
	// �������ͨ�ŵ��ŵ�
	SocketChannel socketChannel;
	
	// Ҫ���ӵķ�����IP��ַ
	private String hostIp;
	
	// Ҫ���ӵ�Զ�̷������ڼ����Ķ˿�
	private int hostListenningPort;
	
	private static TCPClient s_Tcp = null;
	
	public boolean isInitialized = false;
	
	public static synchronized TCPClient instance()
	{
		if (s_Tcp == null)
		{
			s_Tcp = new TCPClient(Constans.SOCKET_SERVER, Constans.SOCKET_PORT);
		}
		return s_Tcp;
	}
	
	/**
	 * ���캯��
	 * 
	 * @param HostIp
	 * @param HostListenningPort
	 * @throws IOException
	 */
	public TCPClient(String HostIp, int HostListenningPort)
	{
		this.hostIp = HostIp;
		this.hostListenningPort = HostListenningPort;
		
		try
		{
			initialize();
			this.isInitialized = true;
			MainActivity.isDone = true;
		} catch (IOException e)
		{
			this.isInitialized = false;
			MainActivity.isDone = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			this.isInitialized = false;
			MainActivity.isDone = false;
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʼ��
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException
	{
		boolean done = false;
		try
		{
			// �򿪼����ŵ�������Ϊ������ģʽ
			socketChannel = SocketChannel.open();
			if (socketChannel != null)
			{
				socketChannel.connect(new InetSocketAddress(hostIp, hostListenningPort));
				Log.e(tag, "Connected");
				socketChannel.socket().setTcpNoDelay(true);
				socketChannel.socket().setKeepAlive(true);
				// ���� ��socket��timeoutʱ��
				socketChannel.socket().setSoTimeout(Constans.SOCKET_READ_TIMOUT);
				socketChannel.configureBlocking(false);
				
				// �򿪲�ע��ѡ�������ŵ�
				selector = Selector.open();
				if (selector != null)
				{
					socketChannel.register(selector, SelectionKey.OP_READ);
					done = true;
//					MainActivity.isDone = true;
				}
			}
		}
		finally
		{
//			if (!MainActivity.isDone && selector != null)
//			{
//				selector.close();
//			}
//			if (!MainActivity.isDone  && socketChannel != null)
//			{
//				socketChannel.close();
//			} else {
//				heartBeat();
//			}
			if (!done && selector != null)
			{
				selector.close();
			}
			if (!done  && socketChannel != null)
			{
				socketChannel.close();
			} else {
				heartBeat();
			}
		}
	}
	
	static void blockUntil(SelectionKey key, long timeout) throws IOException
	{
		
		int nkeys = 0;
		if (timeout > 0)
		{
			nkeys = key.selector().select(timeout);
			
		} else if (timeout == 0)
		{
			nkeys = key.selector().selectNow();
		}
		
		if (nkeys == 0)
		{
			throw new SocketTimeoutException();
		}
	}
	
	/**
	 * �����ַ�����������
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMsg(String message) throws IOException
	{
		ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes("utf-8"));
		
		if (socketChannel == null)
		{
			throw new IOException();
		}
		socketChannel.write(writeBuffer);
	}
	
	/**
	 * ��������
	 * 
	 * @param bytes
	 * @throws IOException
	 */
	public void sendMsg(byte[] bytes) throws IOException
	{
		ByteBuffer writeBuffer = ByteBuffer.wrap(bytes);
		
		if (socketChannel == null)
		{
			throw new IOException();
		}
		socketChannel.write(writeBuffer);
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized Selector getSelector()
	{
		return this.selector;
	}
	
	/**
	 * Socket�����Ƿ���������
	 * 
	 * @return
	 */
	public boolean isConnect()
	{
		boolean isConnect = false;
		if (this.isInitialized)
		{
			isConnect =  this.socketChannel.isConnected();
		}
		return isConnect;
	}
	
	/**
	 * �ر�socket ��������
	 * 
	 * @return
	 */
	public boolean reConnect()
	{
		closeTCPSocket();
		Log.e(tag, "reConnect");
		try
		{
			initialize();
			isInitialized = true;
			MainActivity.isDone = true;
		} catch (IOException e)
		{
			isInitialized = false;
			MainActivity.isDone = false;
			e.printStackTrace();
		}
		catch (Exception e)
		{
			isInitialized = false;
			MainActivity.isDone = false;
			e.printStackTrace();
		}
		return isInitialized;
	}
	
	/**
	 * �������Ƿ�رգ�ͨ������һ��socket��Ϣ
	 * 
	 * @return
	 */
	public boolean canConnectToServer()
	{
		try
		{
			if (socketChannel != null)
			{
				socketChannel.socket().sendUrgentData(0xff);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * �ر�socket
	 */
	public void closeTCPSocket() {
		try {
			if (socketChannel != null) {
				socketChannel.close();
			}

		} catch (IOException e) {

		}
		try {
			if (selector != null) {
				selector.close();
			}
		} catch (IOException e) {
		}
		MainActivity.isDone = false;
		Log.e(tag, "closed");
	}
	
	/**
	 * ÿ�ζ������ݺ���Ҫ����ע��selector����ȡ����
	 */
	public synchronized void repareRead()
	{
		if (socketChannel != null)
		{
			try
			{
				selector = Selector.open();
				socketChannel.register(selector, SelectionKey.OP_READ);
			} catch (ClosedChannelException e)
			{
				e.printStackTrace();
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void heartBeat()
	{
		// ����һ��������
		boolean canConnectToServer = canConnectToServer();
		if(canConnectToServer == false){
			MainActivity.isDone = false;
			reConnect();
		} else {
			MainActivity.isDone = true;
			try {
				String content = SHOPID + "@" + MD5.get32MD5(SHOPID + getDate() + "qchouses.com");
				sendMsg(content);
				Log.e(tag, "sendMsg" + content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static String getDate()
	{
		DateFormat sdf = new SimpleDateFormat(FORMATSTR, Locale.getDefault());
		return sdf.format(new Date());
	}
	
	public void setShopID(String shopid)
	{
		this.SHOPID = shopid;
	}
}
