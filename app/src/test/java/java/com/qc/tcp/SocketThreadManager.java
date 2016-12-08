package java.com.qc.tcp;

import android.os.Handler;

/**
 * �̹߳�����
 */
public class SocketThreadManager {
	private static SocketThreadManager s_SocketManager = null;
	private SocketInputThread mInputThread = null;
	private SocketHeartThread mHeartThread = null;

	// ��ȡ����
	public static SocketThreadManager sharedInstance(Handler handler) {
		if (s_SocketManager == null) {
			s_SocketManager = new SocketThreadManager(handler);
			s_SocketManager.startThreads();
		}
		return s_SocketManager;
	}

	// ���������������ⲿ��������
	private SocketThreadManager(Handler handler) {
		mHeartThread = new SocketHeartThread();
		mInputThread = new SocketInputThread(handler);
	}

	/**
	 * �����߳�
	 */

	private void startThreads() {
		mHeartThread.start();
		mInputThread.start();
		mInputThread.setStart(true);
	}

	/**
	 * stop�߳�
	 */
	public void stopThreads() {
		mHeartThread.stopThread();
		mInputThread.setStart(false);
	}

	public static void releaseInstance() {
		if (s_SocketManager != null) {
			s_SocketManager.stopThreads();
			s_SocketManager = null;
		}
	}

	/**
	 * �����ŵ�ID
	 * 
	 * @param shopid
	 */
	public void setShopID(String shopid) {
		mHeartThread.setShopID(shopid);
	}
}
