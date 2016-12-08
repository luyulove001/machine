package java.com.qc.tcp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.qc.qchouse.MainActivity;
import com.qc.utils.Constans;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * �ͻ��˶���Ϣ�߳�
 */
public class SocketInputThread extends Thread
{
	private boolean isStart = true;
	
	private static String tag = "SocketInputThread";
	
	private Handler handler;
	
	public SocketInputThread(Handler handler){
		this.handler = handler;
	}
	
	public void setStart(boolean isStart){
		this.isStart = isStart;
		MainActivity.isDone = isStart;
	}
	
	@Override
	public void run(){
		while (isStart){
			//���ﻹ�������Ӽ������״̬�Ĵ���
			if (!TCPClient.instance().isConnect()){
				Log.e(tag, "TCPClient connet server is fail read thread sleep second " + Constans.SOCKET_SLEEP_SECOND);
				try{
					sleep(Constans.SOCKET_SLEEP_SECOND * 1000);
				}
				catch (InterruptedException e){
					Log.v(tag, "InterruptedException " + e.getMessage());
					e.printStackTrace();
				}
			}
			readSocket();
		}
	}

	public void readSocket(){
		Selector selector = TCPClient.instance().getSelector();
		if (selector == null){
			return;
		}
		try{
			// ���û�����ݹ�����һֱ����
			while (selector.select() > 0){
				for (SelectionKey sk : selector.selectedKeys()){
					// �����SelectionKey��Ӧ��Channel���пɶ�������
					if (sk.isReadable()){
						// ʹ��NIO��ȡChannel�е�����
						SocketChannel sc = (SocketChannel) sk.channel();
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						try{
							sc.read(buffer);
						} catch (IOException e){
							e.printStackTrace();
							// continue;
						}
						buffer.flip();
						String receivedString = "";
						// ��ӡ�յ�������
						try{
							receivedString = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
							if(!receivedString.equals("") && receivedString != null){
								Log.e(tag, "receiveString = " + receivedString);
								Message msg = new Message();
								msg.what = 110;
								msg.obj = receivedString;
								handler.sendMessage(msg);
							}
						} catch (CharacterCodingException e){
							Log.e(tag, "CharacterCodingException " + e.getMessage());
							e.printStackTrace();
						}
						buffer.clear();
						buffer = null;
						
						try{
							// Ϊ��һ�ζ�ȡ��׼��
							sk.interestOps(SelectionKey.OP_READ);
							// ɾ�����ڴ����SelectionKey
							selector.selectedKeys().remove(sk);
							
						} catch (CancelledKeyException e){
							Log.e(tag, "CancelledKeyException " + e.getMessage());
							e.printStackTrace();
						}
					}
				}
			}
			// selector.close();
			// TCPClient.instance().repareRead();
			
		} catch (IOException e1){
			MainActivity.isDone = false;
			Log.e("error", "IOException " + e1.getMessage());
			e1.printStackTrace();
		} catch (ClosedSelectorException e2){
			MainActivity.isDone = false;
			Log.e(tag, "ClosedSelectorException " + e2.getMessage());
		}
	}
	
}
