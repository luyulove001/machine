package java.com.qc.tcp;

import com.qc.qchouse.MainActivity;
import com.qc.utils.Constans;

/**
 * �����߳�
 */
class SocketHeartThread extends Thread {

	String SHOPID = "";

	boolean isStop = false;
	boolean mIsConnectSocketSuccess = false;
	static SocketHeartThread s_instance;

	static final String tag = "SocketHeartThread";

	public static synchronized SocketHeartThread instance() {
		if (s_instance == null) {
			s_instance = new SocketHeartThread();
		}
		return s_instance;
	}

	public SocketHeartThread() {
		TCPClient.instance();
	}

	public void setShopID(String shopid) {
		TCPClient.instance().setShopID(shopid);
	}

	public void stopThread() {
		this.isStop = true;
		MainActivity.isDone = false;
	}

	public void run() {
		isStop = false;
		while (!isStop) {
			TCPClient.instance().heartBeat();
			try {
				Thread.sleep(Constans.SOCKET_HEART_SECOND * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
