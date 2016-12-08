package java.com.qc.qchouse;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.HeadObjectRequest;
import com.alibaba.sdk.android.oss.model.HeadObjectResult;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lvrenyang.rwusb.PL2303Driver.TTYTermios;
import com.lvrenyang.rwusb.PL2303Driver.TTYTermios.FlowControl;
import com.lvrenyang.rwusb.PL2303Driver.TTYTermios.Parity;
import com.lvrenyang.rwusb.PL2303Driver.TTYTermios.StopBits;
import com.lvrenyang.rwusb.USBDriver.USBPort;
import com.pgyersdk.crash.PgyCrashManager;
import com.qc.qchouse.R;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.android_serialport_api.SerialPortFinder;
import java.com.bjw.ComAssistant.MyFunc;
import java.com.bjw.ComAssistant.SerialHelper;
import java.com.bjw.bean.ComBean;
import java.com.lvernyang.myprinter.Global;
import java.com.lvernyang.myprinter.WorkService;
import java.com.qc.dao.ChairDao;
import java.com.qc.dao.ChairData;
import java.com.qc.dao.ChairImpl;
import java.com.qc.dao.DayDao;
import java.com.qc.dao.DayData;
import java.com.qc.dao.DayImpl;
import java.com.qc.dao.OffLineTicketBean;
import java.com.qc.dao.OffLineTicketDao;
import java.com.qc.dao.OffLineTicketDao2;
import java.com.qc.dao.OffLineTicketData;
import java.com.qc.dao.OffLineTicketImpl;
import java.com.qc.dao.OffLineTicketImpl2;
import java.com.qc.help.DbConfig;
import java.com.qc.serial.SerialNewPrint;
import java.com.qc.serial.SerialOldPrint;
import java.com.qc.tcp.SocketThreadManager;
import java.com.qc.utils.BitmapQRUtils;
import java.com.qc.utils.Constans;
import java.com.qc.utils.HexUtils;
import java.com.qc.utils.MD5;
import java.com.qc.utils.StringHexUtils;
import java.com.qc.view.MyVideoView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

	private static final String Tag = "PrintTest";
	byte[] state1 = { 0x10, 0x04, 0x01 }; // 传送打印机状态
	byte[] state2 = { 0x10, 0x04, 0x02 }; // 传送脱机状态
	byte[] state3 = { 0x10, 0x04, 0x03 }; // 传送错误状态
	byte[] state0 = { 0x10, 0x04, 0x04 }; // 传送卷纸传感器状态

	private TextView tv_ver;// 版本号
	private TextView tv_time; // 当前时间
	private TextView tv_week; // 当前星期
	private TextView tv_day; // 当前日期
	private TextView tv_shopname; // 门店名称
	private TextView tv_ticketid; // 当前票号
	private TextView tv_line_peo; // 当天已购票人数
	private TextView tv_line_num; // 排队编号
	private TextView tv_sendmoney2; // 塞钱口
	private TextView tv_company; // 公司名称 用于测试

	private ImageView iv_back;// 返回按钮
	private ImageView iv_shop;// 门店按钮
	private ImageView img_view; // 无网络图片
	private ImageView img_meiti; // 媒体加载中图片
	private ImageView img_weboutline; // Web无网络
	private ImageView iv_peo; // 微信
	private ImageView iv_peo_nonet; // 微信
	private ImageView iv_shopid; // 门店ID二维码
	private ImageView iv_shopid_nonet; // 门店ID二维码

	private MyVideoView video_view; // 视频
	private WebView web_view; // 网页

	public static String ShopId; // 门店ID
	public static String ShopName = ""; // 门店名称
	private String Price; // 门店票价
	private String Secret; // 用户密钥
	private String quenenumber = "";// 当前票数
	private String totalquene;// 排队人数
	private String ticketid = "";// 票ID
	private String quenenumber1;// 排队号
	// private String totalquene1;//当前人数
	// private String queue_size;//当前排队人数
	private String ticketid1; // 票号qc_key
	private String getDataTime; // 判断接收到的两行数据时间
	private String WeiXinBuyTicket = "抱歉，暂时无法扫码"; // 微信购票扫码短地址
	private String WeiXinGetTicket = "抱歉，暂时无法扫码"; // 微信取票扫码短地址
	private String ResultData = ""; // 验证密钥后的消息

	private boolean isVideo = false; // 判断是否有视频

	public static SerialControl ComChair;// 串口 椅子串口
	public static SerialControl ComPrint;// 串口 打印机串口
	public static SerialControl ComMoney;// 串口 收银机串口
	DispQueueThread DispQueue;// 刷新显示线程
	SerialPortFinder mSerialPortFinder;// 串口设备搜索

	public static OSS oss; // 阿里云服务

	// 打印机线程
	private static Handler mHandler = null;
	public static Handler workHandler = null;

	// 讯飞语音
	private SpeechSynthesizer mTts;
	private String voicer = "xiaoyan"; // 声音种类

	private SharedPreferences shared; // 数据库
	private Editor ed;
	public static String Today; // 今天日期

	private int Money = 0; // 投钱
	private int Videonum = 0; // 判断是正在播放的是哪个video
	private int VideoLen; // 下载下来的video长度
	private int Cyclesnum = 1; // 当前循环次数
	private String[] listVideo; // 视频列表
	private String[] listCysles; // 视频循环次数

	private int offlineNum = 0;
	private MediaController mMediaController;

	private DayDao dayDao = new DayImpl(); // 记录发送日期数据
	private DayData daydata = new DayData();
	private ChairDao chairDao = new ChairImpl(); // 记录椅子数据
	private ChairData chairdata = new ChairData();
	private OffLineTicketDao offlineTicketDao = new OffLineTicketImpl(); // 记录离线票数据
	private OffLineTicketDao2 offlineTicketDao2 = new OffLineTicketImpl2(); // 新版记录离线票
	private OffLineTicketData offlineTicketdata = new OffLineTicketData();// 今天的离线票数据
	private List<OffLineTicketData> mOffLineTicketDataList = new ArrayList<OffLineTicketData>();

	// File systemUIapkFile = new File("/system/app/SystemUI.apk"); //SystemUI
	HexUtils hex = new HexUtils();

	private boolean isNewCash = false; // 判断当前是否是新的收银机
	private boolean isRejectCash = true; // 如果要退钱先暂停线程
	private boolean isPing = true; // 判断是否联网
	private boolean testPin = true;

	private boolean lastPing = true;// 上次ping结果

	private boolean first = true;
	private boolean second = true;

	public static volatile boolean isDone = true; // socket 连接
													// true为start,false为stop
	public static volatile boolean isClosed = true; // socket是否已经断开

	private long length;
	private long ApkLength;
	private String offlineTag = "offline"; // 离线数据tag
	private TextView printingPaperTip;

	private boolean isTimeSetFirst = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		hideSystemUI(getWindow().getDecorView()); // 隐藏UI 2.3无法使用
		setContentView(R.layout.activity_main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		PgyCrashManager.register(this);// 蒲公英log
		getVersionInfo();

		initView();

		mHandler = new MHandler(this);
		WorkService.addHandler(mHandler);

		SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "=561f5a6e"); // 讯飞语音
		mTts = SpeechSynthesizer.createSynthesizer(MainActivity.this, mTtsInitListener);// 初始化合成对象
		setParam();

		initData(); // 启动线程监听USB端口
		openTTYS1Port();// 打开串口，监听椅子
		openTTYS2Port();// 打开串口，监听打印机
		openTTYS3Port();// 打开串口，监听收钞机

		new Thread(new Runnable() {
			@Override
			public void run() {
				cashDataTest(); // 收银机数据发送
				cdt.start();// 传感器数据发送
			}
		}).start();

		probe(); // 2.3无法使用 USB打印机
		if (null == WorkService.workThread) {
			Intent intent = new Intent(MainActivity.this, WorkService.class);
			startService(intent);
		}
		Log.d(offlineTag, "onCreate");
		testPing();
		isTimeSetFirst = true;
	}

	private void cashDataTest() {
		sendPortData(ComMoney, "7F8001116582"); // 检查是否连接
		setWaitLongSleep();
		if (isNewCash) {
			sendPortData(ComMoney, "7F0001051E08"); // 读取配置情况
			setSleep();
			sendPortData(ComMoney, "7F800302FFFF25A4"); // 开放、关闭相应的通道
			setSleep();
			sendPortData(ComMoney, "7F00010A3C08"); // 开放
			setSleep();
			if (isNewCash) { // 判断是否是新收银头
				// for(int i = 0; i < 1000;i++){
				// send80CahsData();
				// setWaitSleep();
				// send00CashData();
				// setWaitSleep();
				// if(i == 999){
				// i = 0;
				// }
				// }
				c.start();
			} else {
				sendPortData(ComMoney, "30");
			}
		} else {
			sendPortData(ComMoney, "30");
		}
	}

	private int index = 0;
	private CountDownTimer c = new CountDownTimer(60 * 60 * 1000, 300) {

		@Override
		public void onTick(long millisUntilFinished) {
			if (index % 2 == 0) {
				sendPortData(ComMoney, "7F8001071202");
				// Log.i("111", "index:" + index + " 指令：7F8001071202");
			} else {
				sendPortData(ComMoney, "7F0001071188");
				// Log.d("111", "index:" + index + " 指令：7F0001071188");
			}
			index += 1;
		}

		@Override
		public void onFinish() {
			if (index % 2 == 0) {
				index = 1;
			} else {
				index = 0;
			}
			c.start();
		}
	};

	private void send80CahsData() {
		if (isRejectCash) {
			sendPortData(ComMoney, "7F8001071202");
		} else {
			sendPortData(ComMoney, "7F0001083388");
			setSleep();
			sendPortData(ComMoney, "7F8001080230");
			setSleep();
		}
	}

	private void send00CashData() {
		if (isRejectCash) {
			sendPortData(ComMoney, "7F0001071188");
		} else {
			sendPortData(ComMoney, "7F0001083388");
			setSleep();
			sendPortData(ComMoney, "7F8001080230");
			setSleep();
		}
	}

	private void initView() {
		// 数字字体
		Typeface numface = Typeface.createFromAsset(getAssets(), "fonts/BEBAS.ttf");
		// 中文字体
		Typeface timeface = Typeface.createFromAsset(getAssets(), "fonts/iYaHeiBold.ttf");
		// 当前时间
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_time.setTypeface(numface);
		// 当前星期
		tv_week = (TextView) findViewById(R.id.tv_week);
		// 当前日期
		tv_day = (TextView) findViewById(R.id.tv_day);
		tv_day.setTypeface(numface);
		printingPaperTip = (TextView) findViewById(R.id.printing_paper_tip);
		printingPaperTip.setTypeface(timeface);
		printingPaperTip.setVisibility(View.GONE);
		img_view = (ImageView) findViewById(R.id.img_view); // 购票机已离线图
		img_meiti = (ImageView) findViewById(R.id.img_meiti); // 媒体加载图
		img_meiti.setVisibility(View.VISIBLE);
		img_weboutline = (ImageView) findViewById(R.id.img_weboutline); // Web无网络

		video_view = (MyVideoView) findViewById(R.id.video_view); // Video

		tv_ticketid = (TextView) findViewById(R.id.tv_ticketid);// 当前票号

		tv_line_peo = (TextView) findViewById(R.id.tv_line_peo);
		tv_line_peo.setTypeface(numface);
		tv_line_num = (TextView) findViewById(R.id.tv_line_num);
		tv_line_num.setTypeface(numface);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ExitDialog();
			}
		});

		iv_shop = (ImageView) findViewById(R.id.iv_shop);
		iv_shop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShopDialog();
			}
		});

		iv_peo = (ImageView) findViewById(R.id.iv_peo);
		iv_peo_nonet = (ImageView) findViewById(R.id.iv_peo_nonet);

		iv_shopid = (ImageView) findViewById(R.id.iv_shopid);
		iv_shopid_nonet = (ImageView) findViewById(R.id.iv_shopid_nonet);

		tv_sendmoney2 = (TextView) findViewById(R.id.tv_sendmoney2);
		// tv_sendmoney2.setText("请投入" + Price + "元纸币");
		tv_shopname = (TextView) findViewById(R.id.tv_shopname);

		tv_ver = (TextView) findViewById(R.id.tv_ver);// 版本号
		tv_ver.setText("版本号：v" + getVersion());

		web_view = (WebView) findViewById(R.id.web_view);
		web_view.setWebViewClient(new AdvertWebViewClient());
		web_view.setWebChromeClient(new WebChromeClient());
		WebSettings webSettings = web_view.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
		String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
		webSettings.setAppCachePath(appCacheDir);
		webSettings.setAllowFileAccess(true); // 设置文件权限
		webSettings.setAppCacheEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSupportZoom(false); // 设置无法缩放
		webSettings.setGeolocationEnabled(true);
		// webSettings.setBuiltInZoomControls(false);
		// webSettings.setDomStorageEnabled(true);
		// webSettings.setDatabaseEnabled(true);

		// web_view.loadUrl(Constans.WEBURL);

		mMediaController = new MediaController(MainActivity.this);
		mMediaController.hide();
		mMediaController.setVisibility(View.GONE);
		mMediaController.setClickable(false);
		video_view.setMediaController(mMediaController);
		video_view.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
				// 处理无法播放此视频
				return true;
			}
		});

		video_view.setFocusableInTouchMode(false);
		video_view.setFocusable(false);
		video_view.setEnabled(false);
		video_view.setClickable(false);

		// TODO 还原
		// final Uri uri = Uri.parse("android.resource://" + getPackageName() +
		// "/" + R.raw.test);
		// video_view.setVideoURI(uri);
		video_view.start();
		video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// 播放结束后的操作
				hideSystemUI(getWindow().getDecorView());
				if (isVideo) { // 有视频时操作
					if (Videonum + 1 < VideoLen) {
						if (Cyclesnum < Integer.parseInt(listCysles[Videonum])) {
							video_view.setVideoPath(listVideo[Videonum]);
							// video_view.setVideoURI(uri);
							video_view.start();
							Cyclesnum++;
						} else {
							Cyclesnum = 1;
							Videonum++;
							video_view.setVideoPath(listVideo[Videonum]);
							// video_view.setVideoURI(uri);
							video_view.start();
						}
					} else {
						if (Cyclesnum < Integer.parseInt(listCysles[Videonum])) {
							video_view.setVideoPath(listVideo[Videonum]);
							// video_view.setVideoURI(uri);
							video_view.start();
							Cyclesnum++;
						} else {
							Cyclesnum = 1;
							Videonum = 0;
							video_view.setVideoPath(listVideo[Videonum]);
							// video_view.setVideoURI(uri);
							video_view.start();
						}
					}
				} else {
					// TODO 还原两行
					// video_view.setVideoURI(uri);
					// video_view.start();
				}
				hideSystemUI(getWindow().getDecorView());
			}
		});

		video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// mp.start();
				// mp.setLooping(true);
			}
		});

		tv_company = (TextView) findViewById(R.id.tv_company);

		// ViewTreeObserver vto = web_view.getViewTreeObserver();
		// vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		// @Override
		// public void onGlobalLayout() {
		// web_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		// Log.v("ViewTest", "ViewTest Height: " + web_view.getHeight() + "
		// Width: "
		// + web_view.getWidth());
		// }
		// });
	}

	// 网页
	private class AdvertWebViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return false;
		}

		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			Toast.makeText(MainActivity.this, "暂时无法查找到网站", Toast.LENGTH_SHORT).show();
			view.loadUrl("about:blank");
		}

		/*
		 * //扩充缓存的容量 public void onReachedMaxAppCacheSize(long spaceNeeded, long
		 * totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
		 * quotaUpdater.updateQuota(spaceNeeded * 2); }
		 */
	}

	/**
	 * 监测是否断网
	 */
	private void isWifi() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				isPing = ping();
				if (isPing) {
					if (lastPing) {
						// do nothing
					} else {
						Log.d(offlineTag, "testPing");
						handler.sendEmptyMessage(106);
					}
				} else {
					if (lastPing) {
						handler.sendEmptyMessage(107);
					} else {
						// do nothing
					}
				}
				lastPing = isPing;
			}
		}, 1000, 60000);
	}

	private void testPing() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				first = isDone;
				if (first) { // socket通
					if (second) {
						// 一直通
						// Log.v(offlineTag, "testPing:一直通");
					} else {
						// 之前断，现在通了
						Log.v(offlineTag, "testPing:之前断，现在通了");
						handler.sendEmptyMessage(106);
					}
				} else { // socket断
					if (second) {// 之前通，现在断了
						Log.v(offlineTag, "testPing:之前通，现在断了");
						handler.sendEmptyMessage(107);
					} else {
						// 一直断网
						// Log.v(offlineTag, "testPing:一直断网");
					}
				}
				second = first;
			}
		}, 1000, 5000);
	}

	/*
	 * Ping
	 */
	public static final boolean ping() {
		try {
			String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
			Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
			// 读取ping的内容，可以不加
			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer stringBuffer = new StringBuffer();
			String content = "";
			while ((content = in.readLine()) != null) {
				stringBuffer.append(content);
			}
			int status = p.waitFor();
			if (status == 0) {
				return true;
			}
		} catch (IOException e) {
		} catch (InterruptedException e) {
		} finally {
		}
		return false;
	}

	/**
	 * 启动串口通讯
	 */
	private void initData() {
		ComChair = new SerialControl();
		ComPrint = new SerialControl();
		ComMoney = new SerialControl();
		DispQueue = new DispQueueThread();// 启动线程监听
		DispQueue.start();
		mSerialPortFinder = new SerialPortFinder();
	}

	private void cashDataSend() {
		sendPortData(ComMoney, "7F8001116582"); // 检查是否连接
		setWaitLongSleep();
		if (isNewCash) {
			sendPortData(ComMoney, "7F0001051E08"); // 读取配置情况
			setSleep();
			sendPortData(ComMoney, "7F800302FFFF25A4"); // 开放、关闭相应的通道
			setSleep();
			sendPortData(ComMoney, "7F00010A3C08"); // 开放
			setSleep();
			if (isNewCash) { // 判断是否是新收银头
				Timer timer1 = new Timer();
				timer1.schedule(new TimerTask() {
					@Override
					public void run() {
						if (isRejectCash) {
							sendPortData(ComMoney, "7F8001071202");
							setWaitSleep();
							sendPortData(ComMoney, "7F0001071188");
							setWaitSleep();
						}
					}
				}, 1000, 1000);
			} else {
				sendPortData(ComMoney, "30");
			}
		} else {
			sendPortData(ComMoney, "30");
		}
	}

	private void setSleep() {
		try {
			Thread.sleep(200);// 数据之间的时间间隔
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setWaitSleep() {
		try {
			Thread.sleep(500);// 数据之间的时间间隔
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setWaitLongSleep() {
		try {
			Thread.sleep(2000);// 数据之间的时间间隔
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏UI
	 * 
	 * @param view
	 */
	public void hideSystemUI(View view) {
		view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.INVISIBLE | 8);
	}

	/**
	 * 显示UI
	 * 
	 * @param view
	 */
	public void showSystemUI(View view) {
		view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}

	/*
	 * 获取视频名称
	 */
	private void getVideoName() {
		String url = Constans.URL + "get_shop_video";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("shopid", ShopId);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					Log.v("MainActivity", "object " + object);
					if (code.equals("200")) {
						isVideo = true;
						JSONArray array = object.optJSONArray("result");
						if (array != null) {
							VideoLen = array.length();
							String[] videoName = new String[VideoLen];
							listVideo = new String[VideoLen];// 视频地址
							listCysles = new String[VideoLen];// 每个视频各自的循环次数
							if (VideoLen == 1) {
								JSONObject oneCircle = array.optJSONObject(0);
								String downloadObject = oneCircle.optString("video_name");
								String cycles = oneCircle.optString("cycles");
								videoName[0] = "/sdcard/QChouse/" + downloadObject;
								downloadVideo(2, downloadObject);
								listVideo[0] = "/sdcard/QChouse/" + downloadObject;
								listCysles[0] = cycles;
							} else if (VideoLen == 0) {
								// 没有视频

							} else {
								for (int i = 0; i < VideoLen; i++) {
									JSONObject oneCircle = array.optJSONObject(i);
									String downloadObject = oneCircle.optString("video_name");
									String cycles = oneCircle.optString("cycles");
									videoName[i] = "/sdcard/QChouse/" + downloadObject;
									downloadVideo(2, downloadObject);
									listVideo[i] = "/sdcard/QChouse/" + downloadObject;
									listCysles[i] = cycles;
								}
							}
							// 删除多余视频
							String dirName = "/sdcard/QChouse";
							File f = new File(dirName);
							File[] fl = f.listFiles();
							int fileLen = 0;
							if (null != fl) {
								fileLen = fl.length;
							} else {
								return;
							}
							if (fileLen > VideoLen) {
								for (int i = 0; i < fileLen; i++) {
									for (int y = 0; y < VideoLen; y++) {
										if (fl[i].getName().equals(videoName[y])) {
											fl[i] = null;
										}
									}
								}
								for (int i = 0; i < fileLen; i++) {
									if (fl[i] != null) {
										fl[i].delete();
									}
								}
							}
						} else {
							VideoLen = 0;
							// TODO 没有视频之后放图片处理
						}
					} else {
						// 如果是407失败 无法播放视频处理
						isVideo = false;
						// TODO 还原5行
						// final Uri uri = Uri.parse("android.resource://" +
						// getPackageName() + "/" +R.raw.pengpai);
						// video_view.setVideoURI(uri);
						// video_view.start();
						hideSystemUI(getWindow().getDecorView());
						// img_meiti.setVisibility(View.GONE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 阿里云下载
	 * 
	 * @param type
	 *            数据类型，1为Apk，2为Video
	 * @param downloadObject
	 *            文件名称
	 */
	private void downloadVideo(final int type, final String downloadObject) {
		OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(Constans.accessKeyId,
				Constans.accessKeySecret);

		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
		OSSLog.enableLog();
		oss = new OSSClient(getApplicationContext(), Constans.endpoint, credentialProvider, conf);

		HeadObjectRequest head = new HeadObjectRequest(Constans.testBucket, downloadObject);
		OSSAsyncTask task = oss.asyncHeadObject(head, new OSSCompletedCallback<HeadObjectRequest, HeadObjectResult>() {

			@Override
			public void onSuccess(HeadObjectRequest arg0, HeadObjectResult arg1) {
				length = arg1.getMetadata().getContentLength();
				new Thread(new Runnable() {
					@Override
					public void run() {
						getObjectSample(type, Constans.testBucket, downloadObject);
					}
				}).start();
			}

			@Override
			public void onFailure(HeadObjectRequest arg0, ClientException arg1, ServiceException arg2) {
				if (arg1 != null) {
					// 本地异常如网络异常等
					arg1.printStackTrace();
				}
				if (arg2 != null) {
					// 服务异常
				}

			}
		});
	}

	private void downloadApk(final String downloadObject) {
		OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(Constans.accessKeyId,
				Constans.accessKeySecret);

		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
		OSSLog.enableLog();
		oss = new OSSClient(getApplicationContext(), Constans.endpoint, credentialProvider, conf);

		HeadObjectRequest head = new HeadObjectRequest(Constans.testBucket, downloadObject);
		OSSAsyncTask task = oss.asyncHeadObject(head, new OSSCompletedCallback<HeadObjectRequest, HeadObjectResult>() {

			@Override
			public void onSuccess(HeadObjectRequest arg0, HeadObjectResult arg1) {
				ApkLength = arg1.getMetadata().getContentLength();
				new Thread(new Runnable() {
					@Override
					public void run() {
						getObjectApk(Constans.testBucket, downloadObject);
					}
				}).start();
			}

			@Override
			public void onFailure(HeadObjectRequest arg0, ClientException arg1, ServiceException arg2) {
				if (arg1 != null) {
					// 本地异常如网络异常等
					arg1.printStackTrace();
				}
				if (arg2 != null) {
					// 服务异常
				}
			}
		});
	}

	private void getObjectSample(int type, String testBucket, String testObject) {
		// 构造下载文件请求
		GetObjectRequest get = new GetObjectRequest(testBucket, testObject);
		String dirName = "/sdcard/QChouse";
		File f = new File(dirName);
		if (!f.exists()) {
			f.mkdir();
		}

		String newFilename = dirName + "/" + testObject;
		File file = new File(newFilename);

		if (file.exists()) {
			long fileLength = file.length();
			if (fileLength < length) {
				file.delete();
				try {
					// 同步执行下载请求，返回结果
					GetObjectResult getResult = oss.getObject(get);
					// 获取文件输入流
					InputStream inputStream = getResult.getObjectContent();
					byte[] buffer = new byte[2048];
					int len;
					OutputStream os = new FileOutputStream(newFilename);
					while ((len = inputStream.read(buffer)) != -1) {
						// 处理下载的数据，比如图片展示或者写入文件等
						os.write(buffer, 0, len);
					}
					os.close();
					inputStream.close();
					// 下载后可以查看文件元信息
					ObjectMetadata metadata = getResult.getMetadata();
					handler.sendEmptyMessage(104);
				} catch (ClientException e) {
					// 本地异常如网络异常等
					e.printStackTrace();
				} catch (ServiceException e) {
					// 服务异常
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				handler.sendEmptyMessage(104);
			}
			return;
		} else { // 视频不存在
			try {
				// 同步执行下载请求，返回结果
				GetObjectResult getResult = oss.getObject(get);
				// 获取文件输入流
				InputStream inputStream = getResult.getObjectContent();
				byte[] buffer = new byte[2048];
				int len;
				OutputStream os = new FileOutputStream(newFilename);
				while ((len = inputStream.read(buffer)) != -1) {
					// 处理下载的数据，比如图片展示或者写入文件等
					os.write(buffer, 0, len);
				}
				os.close();
				inputStream.close();
				// 下载后可以查看文件元信息
				ObjectMetadata metadata = getResult.getMetadata();
				handler.sendEmptyMessage(104);
			} catch (ClientException e) {
				// 本地异常如网络异常等
				e.printStackTrace();
			} catch (ServiceException e) {
				// 服务异常
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void getObjectApk(String testBucket, String testObject) {
		// 构造下载文件请求
		GetObjectRequest get = new GetObjectRequest(testBucket, testObject);
		String dirName = "/sdcard/QChouseApk";
		File f = new File(dirName);
		if (!f.exists()) {
			f.mkdir();
		}

		String newFilename = dirName + "/" + testObject;
		File file = new File(newFilename);

		if (file.exists()) {
			long apkFileLength = file.length();
			if (apkFileLength < ApkLength) {
				file.delete();
				try {
					GetObjectResult getResult = oss.getObject(get);
					InputStream inputStream = getResult.getObjectContent();
					// byte[] buffer = new byte[2048];
					byte[] buffer = new byte[64];
					int len;
					OutputStream os = new FileOutputStream(newFilename);
					while ((len = inputStream.read(buffer)) != -1) {
						os.write(buffer, 0, len);
					}
					os.close();
					inputStream.close();
					ObjectMetadata metadata = getResult.getMetadata();
				} catch (ClientException e) {
					e.printStackTrace();
				} catch (ServiceException e) {
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				GetObjectResult getResult = oss.getObject(get);
				InputStream inputStream = getResult.getObjectContent();
				byte[] buffer = new byte[64];
				int len;
				OutputStream os = new FileOutputStream(newFilename);
				while ((len = inputStream.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				os.close();
				inputStream.close();
				ObjectMetadata metadata = getResult.getMetadata();
			} catch (ClientException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + newFilename), "application/vnd.android.package-archive");
		MainActivity.this.startActivity(intent);
	}

	/**
	 * 从数据库中获取信息
	 */
	private void getSharedPerferences() {
		SharedPreferences sp = getSharedPreferences("ShopId", Context.MODE_PRIVATE);// 获取门店ID刷新排队人数
		ShopId = sp.getString("id", null);
		ShopName = sp.getString("shopname", null);
		Price = sp.getString("price", null);
		Secret = sp.getString("secret", null);
	}

	private void setData() {
		getSharedPerferences();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		Today = formatter.format(curDate);

		if (ShopId != null) {
			if (ShopName != null) {
				getSearchShop();
				tv_shopname.setText("门店名称：" + ShopName);
			}

			// Socket连接
			SocketThreadManager m = SocketThreadManager.sharedInstance(handler);
			m.setShopID(ShopId);

			if (Secret == null) {
				ShopDialog();
			}
			new Thread(new Runnable() { // 微信扫码购票二维码
				@Override
				public void run() {
					longToShort(1, Constans.getWeixinScaneTicket(ShopId));
					Log.v("MainActivity", "getWeixinScaneTicket " + Constans.getWeixinScaneTicket(ShopId));
				}
			}).start();

			String str1 = ShopId + "&" + Today + "&" + "Zhejiang kuaifa Technology Co. Ltd";
			final String str2 = MD5.get32MD5(str1);
			// Bitmap qrcode = generateQRCode(ShopId + "," + str2);

			new Thread(new Runnable() { // 微信扫码取票二维码
				@Override
				public void run() {
					longToShort(2, Constans.getWeixinScaneTouchTicket(ShopId + "," + str2));
					Log.v("MainActivity",
							"getWeixinScaneTouchTicket " + Constans.getWeixinScaneTouchTicket(ShopId + "," + str2));
				}
			}).start();

			findIfLegal(); // 用于查找当前密钥是否过期
			getQueueInfo();
		} else {
			Intent intent = new Intent(MainActivity.this, FirstRegistActivity.class);
			intent.putExtra("intent", "1000");
			startActivity(intent);
		}
	}

	/**
	 * 判断密钥是否合法
	 * 
	 * @param secret
	 *            密钥
	 */
	private void findIfLegal() {
		String url = Constans.URL + "set_shop";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("secret_key", Secret);
		params.addBodyParameter("machine_version", "v" + getVersion());
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					if (code.equals("200")) {
						JSONObject jsondata = object.getJSONObject("result");
						String ShopId = jsondata.getString("shopid");
						String shopname = jsondata.getString("shopname");
						String price = jsondata.getString("cash_price");
						String timestamp = jsondata.getString("timestamp");
						compareTime(timestamp);
						// setSystemTime(MainActivity.this, timestamp);
						if (!shopname.equals(ShopName)) {
							SharedPreferences sp = getSharedPreferences("ShopId", Context.MODE_PRIVATE);
							Editor ed = sp.edit();
							ed.putString("shopname", shopname);
							ed.commit();
							ShopName = shopname;
						} else if (!price.equals(Price)) {
							SharedPreferences sp = getSharedPreferences("ShopId", Context.MODE_PRIVATE);
							Editor ed = sp.edit();
							ed.putString("price", price);
							ed.commit();
							Price = price;
						}
					} else if (code.equals("0")) {
						Intent intent = new Intent(MainActivity.this, FirstRegistActivity.class);
						startActivity(intent);
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			/**
			 * 本地和服务器时间对比
			 * 
			 * @param time
			 */
			private void compareTime(String time) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date d = sdf.parse(time);
					Calendar c = Calendar.getInstance();
					c.setTime(d);
					long t = Math.abs(c.getTimeInMillis() - System.currentTimeMillis());
					if (t / 1000 / 60 > 30 && isTimeSetFirst) { // 相差超过30分钟并是第一次进入
						showTimeSetDialog();
						isTimeSetFirst = false;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 是否跳到系统时间设置页面dialog
	 */
	private void showTimeSetDialog() {
		hideSystemUI(getWindow().getDecorView());
		Builder builder = new Builder(MainActivity.this);
		builder.setCancelable(false);
		builder.setMessage("当前系统时间有误，可能会影响离线票的统计，请进入系统设置更改日期和时间");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent("/");
				ComponentName cm = new ComponentName("com.android.settings",
						"com.android.settings.DateTimeSettingsSetupWizard");
				intent.setComponent(cm);
				intent.setAction("android.intent.action.VIEW");
				hideSystemUI(getWindow().getDecorView());
				arg0.dismiss();
				startActivityForResult(intent, 0);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				hideSystemUI(getWindow().getDecorView());
				arg0.dismiss();
			}
		});
		hideSystemUI(getWindow().getDecorView());
		builder.create().show();
	}

	/**
	 * 门店机器设置
	 * 
	 * @param secret
	 *            密钥
	 */
	private void setShop(String secret) {
		String url = Constans.URL + "set_shop";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("secret_key", secret);
		params.addBodyParameter("machine_version", "v" + getVersion());
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					if (code.equals("200")) {
						ResultData = "success";
						JSONObject jsondata = object.getJSONObject("result");
						ShopId = jsondata.getString("shopid");
						ShopName = jsondata.getString("shopname");
						Price = jsondata.getString("cash_price");

						SharedPreferences sp = getSharedPreferences("ShopId", Context.MODE_PRIVATE);
						SharedPreferences chai = getSharedPreferences("Chairs", Context.MODE_PRIVATE);
						Editor ed = sp.edit();
						Editor edchair = chai.edit();
						ed.putString("id", ShopId);
						ed.putString("shopname", ShopName);
						ed.putString("price", Price);
						ed.commit();
						// 删除椅子计数
						edchair.clear();
						edchair.commit();
						setData();
					} else if (code.equals("0")) { // 密钥不正确
						ResultData = object.getString("result");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private String setShop1(String secret) {
		String resultData = "";
		String url = Constans.URL + "set_shop";
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("qc_key", Constans.QCKEY));
		params.add(new BasicNameValuePair("secret_key", secret));
		params.add(new BasicNameValuePair("machine_version", "v" + getVersion()));
		HttpResponse httpResponse = null;
		HttpParams httpParameters1 = new BasicHttpParams();
		// 超时设置
		HttpConnectionParams.setConnectionTimeout(httpParameters1, 5 * 1000);
		HttpConnectionParams.setSoTimeout(httpParameters1, 5 * 1000);

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpResponse = new DefaultHttpClient().execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				System.out.println("result:---------" + result);
				JSONObject object = new JSONObject(result);
				String code = object.getString("code");
				if (code.equals("200")) {
					resultData = "success";
					JSONObject jsondata = object.getJSONObject("result");
					ShopId = jsondata.getString("shopid");
					ShopName = jsondata.getString("shopname");
					Price = jsondata.getString("cash_price");
					SharedPreferences sp = getSharedPreferences("ShopId", Context.MODE_PRIVATE);
					SharedPreferences chai = getSharedPreferences("Chairs", Context.MODE_PRIVATE);
					Editor ed = sp.edit();
					Editor edchair = chai.edit();
					ed.putString("id", ShopId);
					ed.putString("shopname", ShopName);
					ed.putString("price", Price);
					ed.putString("secret", secret);
					ed.commit();
					// 删除椅子计数
					edchair.clear();
					edchair.commit();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							setData();
						}
					});
				} else if (code.equals("0")) {
					resultData = object.getString("result");
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultData;
	}

	/*
	 * 获取门店信息
	 */
	private void getSearchShop() {
		String url = Constans.URL + "get_shop_info";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("shopid", ShopId);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					if (code.equals("200")) {
						JSONObject jsondata = object.getJSONObject("result");
						Price = jsondata.optString("cash_price");
						handler.sendEmptyMessage(109);
						SharedPreferences sp = getSharedPreferences("ShopId", Context.MODE_PRIVATE);
						Editor ed = sp.edit();
						ed.putString("price", Price + "");
						ed.commit();
						tv_sendmoney2.setText("请投入" + Price + "元纸币");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * 获取门店排队信息
	 */
	private void getQueueInfo() {
		String url = Constans.URL + "get_queue_info";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("shopid", ShopId);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					if (code.equals("200")) {
						JSONObject jsondata = object.getJSONObject("result");
						// totalquene = jsondata.getString("num"); //当前门店消费人数
						totalquene = jsondata.getString("queue_size"); // 排队人数
						ticketid1 = jsondata.getString("ticket_no"); // 票号
						quenenumber1 = jsondata.getString("queue_number"); // 排队号
						// queue_size = jsondata.getString("queue_size"); //排队人数
						if (ticketid1 == null || ticketid1.equals("null")) {
							ticketid1 = "0";
						}
						if (quenenumber1 == null || quenenumber1.equals("null")) {
							quenenumber1 = "无";
						}
						handler.sendEmptyMessage(103);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * 获取版本信息，用于静默更新
	 */
	private void getVersionInfo() {
		String url = Constans.URL + "get_version";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					if (code.equals("200")) {
						JSONObject jsondata = object.getJSONObject("result");
						String version = jsondata.getString("version"); // 版本号
						String fileName = jsondata.getString("file_name"); // 文件名
						int versionCode = getVersionCode();
						if (Integer.parseInt(version) > versionCode) { // 需要更新
							downloadApk(fileName);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 百度长链接转短链接
	 * 
	 * @param url
	 *            长链接地址
	 * @return String 短链接地址
	 */
	public String generateShortUrl(String url) {
		try {
			HttpPost httpost = new HttpPost("http://dwz.cn/create.php");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("url", url));
			httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = new DefaultHttpClient().execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			JSONObject object = new JSONObject(jsonStr);
			Log.v("generateShortUrl", "generateShortUrl " + jsonStr);
			return object.getString("tinyurl");
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}

	/**
	 * 服务器长链接转短链接
	 * 
	 * @param longUrl
	 */
	private void longToShort(final int num, String longUrl) {
		String url = Constans.COMMONURL + "common/dwz_encode";
		RequestParams params = new RequestParams();
		params.addBodyParameter("url", longUrl);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					Log.v("longToShort", "longToShort " + arg0.result);
					String code = object.getString("code");
					if (code.equals("200")) {
						JSONObject jsondata = object.getJSONObject("result");
						String tinyurl = jsondata.getString("tinyurl"); // 短地址
						if (num == 1) {
							WeiXinBuyTicket = tinyurl;
							handler.sendEmptyMessage(111);
						} else if (num == 2) {
							WeiXinGetTicket = tinyurl;
							handler.sendEmptyMessage(112);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * 实时发送椅子增量数据
	 */
	private void sendChairNum() {
		String url = Constans.URL + "add_chair_num";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("shopid", ShopId);
		params.addBodyParameter("send_date", Today);
		params.addBodyParameter("num", "1");
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					if (code.equals("200")) {
						// TODO
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 发送所有未发送的离线票
	 */
	private void sendOfflineNotSend() {
		// TODO
		StringBuffer uids = new StringBuffer();
		List<OffLineTicketBean> ls_offline = offlineTicketDao2.findOffLineNotSend(DbConfig.getInstance().getDb());
		if (ls_offline == null || ls_offline.isEmpty()) {
			Log.i(offlineTag, "无未发送离线票");
			return;
		}
		int index = (int) Math.ceil(ls_offline.size() / 10.0);
		for (int i = 0; i < index; i++) {
			uids = new StringBuffer();
			for (int j = 0; j < (i == index - 1 ? ls_offline.size() % 10 : 10); j++) { // 10个一行
				uids = uids.append(ls_offline.get(i * 10 + j).getId() + ",");
			}
			if (!"".equals(uids)) {
				String uid = (String) uids.subSequence(0, uids.length() - 1);
				Log.i(offlineTag, "sendOfflineNotSend:" + uid);
				sendOffLineTichet(uid);
			}
		}
	}

	/**
	 * 数据库增加一张离线票 （新）
	 */
	private void addOfflineTicket() {
		OffLineTicketBean offline = new OffLineTicketBean();
		String id = System.currentTimeMillis() / 1000 + "";
		offline.setId(id);
		offline.setIsSend(0);
		offline.setShopid(ShopId);
		offlineTicketDao2.addOffLineTicket(DbConfig.getInstance().getDb(), offline);
		Log.i(offlineTag, "addOfflineTicket()  offline:" + offline.toString());
		// sendOffLineTichet(offline);
		List<OffLineTicketBean> ls_offline = offlineTicketDao2.findOffLineNotSend(DbConfig.getInstance().getDb());
		for (int i = 0; i < ls_offline.size(); i++) {
			Log.i(offlineTag, "addOfflineTicket()  offline:" + i + "  " + ls_offline.get(i).toString());
		}
		sendOffLineTichet(offline.getId());
	}

	private String setOfflineId() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}

	/**
	 * 发送离线票（新）
	 * 
	 * @param uids 离线票
	 */
	private void sendOffLineTichet(final String uids) {
		// TODO Auto-generated method stub

		String url = Constans.URL + "report_offine_ticket";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("shopid", ShopId);
		params.addBodyParameter("timestamp", uids);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.d(offlineTag, "code:" + arg0.getExceptionCode() + "   Msg:" + arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				OffLineTicketBean offline = new OffLineTicketBean();
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					Log.d(offlineTag, "code:" + code);
					if (code.equals("200")) {
						String[] str = uids.split(",");
						for (int i = 0; i < str.length; i++) {
							offline = offlineTicketDao2.findOffLineById(DbConfig.getInstance().getDb(), str[i]);
							offline.setIsSend(1);
							offlineTicketDao2.editOffLineTicket(DbConfig.getInstance().getDb(), offline);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 数据库增加一张离线票
	 */
	private void addOfflineTicketNumber() {
		OffLineTicketData offline = offlineTicketDao.findOffLineByDay(DbConfig.getInstance().getDb(), Today);
		if (offline == null) {
			Log.d(offlineTag, "offline：isNull");
			offlineTicketdata.setIsSend(0);
			offlineTicketdata.setOfflineNumber(1);
			offlineTicketdata.setDay(Today);
			offlineTicketDao.addOffLineTicket(DbConfig.getInstance().getDb(), offlineTicketdata);
			// sendOfflineTicket("1");
		} else {
			offlineNum = offline.getOfflineNumber() + 1;
			offline.setIsSend(0);
			offline.setDay(Today);
			offline.setOfflineNumber(offlineNum);
			offline.setAllNumber(offline.getAllNumber() + 1);
			offlineTicketDao.editOffLineTicket(DbConfig.getInstance().getDb(), offline);
			// sendOfflineTicket(offlineNum + "");
			Log.d(offlineTag, "offline.toString():" + offline.toString());
		}
	}

	/**
	 * 发送离线票数据
	 * 
	 * @param num
	 *            增量
	 */
	private void sendOfflineTicket(final String num) {
		String url = Constans.URL + "add_off_num";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("shopid", ShopId);
		params.addBodyParameter("send_date", Today);
		params.addBodyParameter("num", num);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.d(offlineTag, "code:" + arg0.getExceptionCode() + "Msg:" + arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					Log.d(offlineTag, "code:" + code);
					if (code.equals("200")) {
						OffLineTicketData offline = offlineTicketDao.findOffLineByDay(DbConfig.getInstance().getDb(),
								Today);
						offlineNum = 0;
						offline.setIsSend(1);
						offline.setDay(Today);
						offline.setOfflineNumber(offlineNum);
						offlineTicketDao.editOffLineTicket(DbConfig.getInstance().getDb(), offline);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 发送其他日子离线票
	 * 
	 * @param num
	 * @param day
	 */
	private void sendDayOfflineTicket(final int num, final String day) {
		String url = Constans.URL + "add_off_num";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("shopid", ShopId);
		params.addBodyParameter("send_date", day);
		params.addBodyParameter("num", num + "");
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					OffLineTicketData offline = offlineTicketDao.findOffLineByDay(DbConfig.getInstance().getDb(), day);
					Log.d(offlineTag, "sendDayOfflineTicket.code:" + code + "  offline:" + offline.toString());
					if (code.equals("200")) {
						offline.setIsSend(1);
						offline.setOfflineNumber(0);
					} else {
						offline.setIsSend(0);
					}
					offline.setDay(day);
					// offline.setOfflineNumber(offlineNum);
					offlineTicketDao.editOffLineTicket(DbConfig.getInstance().getDb(), offline);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 发送所有未发送的离线数据
	 */
	private void sendOffLine() {
		Log.d(offlineTag, "sendOffLine:发送所有离线数据");
		mOffLineTicketDataList = offlineTicketDao.findOffLineNotSend(DbConfig.getInstance().getDb());
		if (mOffLineTicketDataList != null) {
			int offlen = mOffLineTicketDataList.size();
			for (int i = 0; i < offlen; i++) {
				String day = mOffLineTicketDataList.get(i).getDay();
				int num = mOffLineTicketDataList.get(i).getOfflineNumber();
				if (num != 0)
					sendDayOfflineTicket(num, day);
			}
		}
	}

	/**
	 * 监听返回键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ExitDialog();
			return false;
		}
		return false;
	}

	/**
	 * 获取版本号
	 * 
	 * @return String 版本号
	 */
	public String getVersion() {
		String version = null;
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			version = info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 获取内部版本号
	 * 
	 * @return String 内部版本号
	 */
	public int getVersionCode() {
		int versionCode = 0;
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/*
	 * 提示音
	 */
	private void playSound() {
		AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
		final SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		final int sourceid = soundPool.load(MainActivity.this, R.raw.song, 0);
		soundPool.play(sourceid, 1, 1, 0, 0, 1);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
				soundPool.play(sourceid, 1, 1, 1, 0, 0.5f);
			}
		});
	}

	/*
	 * 讯飞语音设置
	 */
	private void setParam() {
		mTts.setParameter(SpeechConstant.PARAMS, null);
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);// TYPE_CLOUD
																					// 云端
																					// TYPE_LOCAL
																					// 本地
		mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
		// 设置合成语速
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// 设置合成音调
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// 设置合成音量
		mTts.setParameter(SpeechConstant.VOLUME, "100");
		// 设置播放器音频流类型
		mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
		// 设置播放合成音频打断音乐播放，默认为true
		mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
		// mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		// mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,
		// Environment.getExternalStorageDirectory()+"/msc/tts.wav");
	}

	/**
	 * 初始化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			if (code != ErrorCode.SUCCESS) {
				// showTip("初始化失败,错误码："+code);
			} else {
				// 初始化成功，之后可以调用startSpeaking方法
				// 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
				// 正确的做法是将onCreate中的startSpeaking调用移至这里
			}
		}
	};

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {

		@Override
		public void onSpeakBegin() {
			// showTip("开始播放");
		}

		@Override
		public void onSpeakPaused() {
			// showTip("暂停播放");
		}

		@Override
		public void onSpeakResumed() {
			// showTip("继续播放");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
			// 合成进度
			// mPercentForBuffering = percent;
			// showTip(String.format(getString(R.string.tts_toast_format),
			// mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			// 播放进度
			// mPercentForPlaying = percent;
			// showTip(String.format(getString(R.string.tts_toast_format),
			// mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onCompleted(SpeechError error) {
			// if (error == null) {
			// showTip("播放完成");
			// } else if (error != null) {
			// showTip(error.getPlainDescription(true));
			// }
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			// if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			// String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			// Log.d(TAG, "session id =" + sid);
			// }
		}
	};

	private void ShopDialog() {
		hideSystemUI(getWindow().getDecorView());
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		final AlertDialog builder = new Builder(MainActivity.this).create();
		builder.setCancelable(false);
		View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_getdata, null);
		builder.setMessage("请输入密钥");
		builder.setTitle("提示");
		builder.setView(getLayoutInflater().inflate(R.layout.dialog_getdata, null));
		builder.show();
		builder.getWindow().setLayout(width / 2, height / 4);
		builder.getWindow().setContentView(view);

		final TextView tv_secret = (TextView) builder.findViewById(R.id.tv_secret);
		final EditText et_regist = (EditText) builder.findViewById(R.id.et_regist);
		et_regist.setFocusable(true);

		Timer timer = new Timer(); // 弹出软键盘
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0,
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 100);

		builder.getWindow().findViewById(R.id.btn_confirm).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				try {
					Field field = builder.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					field.set(builder, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
				final String regist = et_regist.getText().toString().trim();
				if (regist.equals("") || regist == null) {
					tv_secret.setText("密钥不能为空");
				} else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							String result = setShop1(regist);
							ResultData = result;
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									if (ResultData.equals("success")) {
										builder.dismiss();
									} else {
										tv_secret.setText(ResultData);
									}
								}
							});
						}
					}).start();
				}
				hideSystemUI(getWindow().getDecorView());
			}
		});

		builder.getWindow().findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				hideSystemUI(getWindow().getDecorView());
				builder.dismiss();
			}
		});
	}

	private void ExitDialog() {
		hideSystemUI(getWindow().getDecorView());
		Builder builder = new Builder(MainActivity.this);
		builder.setCancelable(false);
		builder.setMessage("确定退出吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				System.exit(0);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				hideSystemUI(getWindow().getDecorView());
				arg0.dismiss();
			}
		});
		hideSystemUI(getWindow().getDecorView());
		builder.create().show();
	}

	// 定义Handler对象
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 101:
				mTts.startSpeaking("纸币投入成功", mTtsListener);
				new Thread(new Runnable() {
					@Override
					public void run() {
						sellMachineTicket();
					}
				}).start();
				break;
			case 102:
				tv_line_peo.setText(totalquene);
				break;
			case 103:
				tv_line_peo.setText(totalquene);
				tv_line_num.setText(quenenumber1);
				tv_ticketid.setText(ticketid1);
				break;
			case 104:
				video_view.setVideoPath(listVideo[0]);
				// Uri uri = Uri.parse("android.resource://" + getPackageName()
				// + "/" + R.raw.test);
				// video_view.setVideoURI(uri);
				video_view.start();
				img_meiti.setVisibility(View.GONE);
				hideSystemUI(getWindow().getDecorView());
				break;
			case 105:
				long time = System.currentTimeMillis();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");

				final Calendar mCalendar = Calendar.getInstance();
				mCalendar.setTimeInMillis(time);
				int mWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

				if (mWeek == 1) {
					tv_week.setText("星期日");
				} else if (mWeek == 2) {
					tv_week.setText("星期一");
				} else if (mWeek == 3) {
					tv_week.setText("星期二");
				} else if (mWeek == 4) {
					tv_week.setText("星期三");
				} else if (mWeek == 5) {
					tv_week.setText("星期四");
				} else if (mWeek == 6) {
					tv_week.setText("星期五");
				} else if (mWeek == 7) {
					tv_week.setText("星期六");
				}

				Date curDate = new Date(time);
				String str = formatter.format(curDate);
				String str2 = formatter2.format(curDate);
				tv_time.setText(str2);
				tv_day.setText(str);
				if (str2.equals("00:00")) {
					handler.sendEmptyMessage(114);
				}
				sendEmptyMessageDelayed(105, 60000);
				break;
			case 106: // ping通过
				img_view.setVisibility(View.GONE);
				iv_peo_nonet.setVisibility(View.GONE);
				iv_shopid_nonet.setVisibility(View.GONE);
				img_weboutline.setVisibility(View.GONE);
				// getSharedPerferences();
				setData();
				web_view.loadUrl(Constans.getWebUrl(ShopId));
				// TODO
				// sendOffLine();
				break;
			case 107: // ping不通
				// img_view.setVisibility(View.VISIBLE);
				img_meiti.setVisibility(View.GONE);
				// img_weboutline.setVisibility(View.VISIBLE);
				iv_peo_nonet.setVisibility(View.VISIBLE);
				iv_shopid_nonet.setVisibility(View.VISIBLE);
				// video_view.pause();
				break;
			case 108:
				tv_sendmoney2.setText("请继续投入" + (Integer.parseInt(Price) - Money) + "元纸币");
				mTts.startSpeaking("请继续投入" + (Integer.parseInt(Price) - Money) + "元纸币", mTtsListener);
				break;
			case 109:
				tv_sendmoney2.setText("请投入" + Price + "元纸币");
				break;
			case 110: // TCP推送
				Log.i("TCP", "TCP " + msg.obj.toString());
				// {"type":2,"ticket_no":"1880023114","queue_number":"4","station":"15001","queue_size":"5"}
				if (msg.obj.toString().indexOf("connected") != -1) {
					return;
				}
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					String type = object.getString("type");
					if ("11".equals(type)) {// TODO type类型操作
						getVersionInfo();
					} else if ("12".equals(type)) {
						SharedPreferences sp = getSharedPreferences("ShopId", Context.MODE_PRIVATE);
						Editor ed = sp.edit();
						ed.putString("secret", "");
						ed.commit();
						Intent i = new Intent(MainActivity.this, FirstRegistActivity.class);
						Log.i("TCP", "startActivity2FirstRegistActivity");
						MainActivity.this.finish();
						startActivity(i);
					} else {
						String ticket_no = object.getString("ticket_no");
						String queue_number = object.getString("queue_number");
						totalquene = object.getString("queue_size");
						String queueNum = tv_line_num.getText().toString();

						if (queue_number.equals("null") || queue_number.equals("")) {
							queue_number = "无";
							quenenumber1 = "无";
						} else {
							quenenumber1 = queue_number;
						}
						if (ticket_no.equals("null") || ticket_no.equals("")) {
							ticket_no = "0";
						}
						if ("2".equals(type)) { // 验票后推送
							// {"type":2,"ticket_no":null,"queue_number":null,"station":"15001","queue_size":"0"}
							// {"type":2,"ticket_no":"","queue_number":"","station":"01","queue_size":"0"}
							String station = object.getString("station"); // 工号
							tv_line_peo.setText(totalquene);
							tv_line_num.setText(queue_number);
							tv_ticketid.setText(ticket_no);
							playSound();
							if (!queue_number.equals("无") && !queue_number.equals("")) {
								if (station.equals("")) {
									mTts.startSpeaking("请" + queue_number + "号顾客前往理发店理发", mTtsListener);
								} else {
									mTts.startSpeaking("请" + queue_number + "号顾客到" + station.substring(1) + "号工位理发",
											mTtsListener);
								}
							}
						} else if ("1".equals(type)) { // 扫码出票
							// {"type":1,"ticket_no":"1880034717","queue_number":13,"today_sell_num":13,"queue_size":"0"}
							// {"type":1,"ticket_no":"1880006610","queue_number":"15","create_time":"2016-08-09
							// 15:01:48","queue_size":"0"}
							// "type":1,"ticket_no":"1880053201","queue_number":"4","create_time":"2016-08-11
							// 13:47:23","queue_size":1}
							boolean isTime = object.isNull("create_time");
							String create_time = "";
							tv_line_peo.setText(totalquene);

							if (queueNum.equals("无")) {
								tv_line_num.setText(queue_number);
								tv_ticketid.setText(ticket_no);
							}

							if (!isTime) {
								create_time = object.getString("create_time"); // 购票时间
							} else {
								SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date curDate1 = new Date(System.currentTimeMillis());// 获取当前时间
								create_time = formatter1.format(curDate1);
							}

							if (ticket_no != null) {
								probe();
								SerialOldPrint.pushJPushTicket(MainActivity.this, create_time, quenenumber1, ticket_no); // 打印推送票
								SerialNewPrint.newPrintScaneTicket(create_time, quenenumber1, ticket_no); // 新打印机
								mTts.startSpeaking("正在出票，请妥善保管好小票", mTtsListener);
								// TODO 判断打印机是否打印成功
								printReport(ticket_no, "1");
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case 111:
				if (WeiXinBuyTicket != null && !WeiXinBuyTicket.equals("")) {
					Bitmap wxBuy = BitmapQRUtils.generateQRCode(WeiXinBuyTicket);
					iv_peo.setImageBitmap(wxBuy);
				}
				break;
			case 112:
				if (WeiXinGetTicket != null && !WeiXinGetTicket.equals("")) {
					Bitmap wxGet = BitmapQRUtils.generateQRCode(WeiXinGetTicket);
					iv_shopid.setImageBitmap(wxGet);
				}
				break;
			case 113:
				img_meiti.setVisibility(View.VISIBLE); // ?媒体加载中
				break;
			case 114:
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dateToday = new Date(System.currentTimeMillis());// 获取当前时间
				Today = sdf.format(dateToday);
				setData();
				break;
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		CloseComPort(ComChair);
		CloseComPort(ComPrint);
		CloseComPort(ComMoney);
		WorkService.delHandler(mHandler);
		mHandler = null;
		mTts.stopSpeaking();
	}

	public void onResume() {
		super.onResume();
		hideSystemUI(getWindow().getDecorView());
		WorkService.addHandler(mHandler);
		// getSharedPerferences();
		setData();
		handler.sendEmptyMessageDelayed(105, 1000);
		web_view.loadUrl(Constans.getWebUrl(ShopId));
		probe();
		getVideoName();
		Log.d(offlineTag, "onResume");
		// TODO
		// sendOffLine();
		if (!isDone) {
			String dirName = "/sdcard/QChouse";
			File f = new File(dirName);
			File[] fl = f.listFiles();
			if (null != fl && fl.length > 0) {
				isVideo = true;
				VideoLen = fl.length;
				listVideo = new String[VideoLen];
				listCysles = new String[VideoLen];
				for (int i = 0; i < fl.length; i++) {
					listVideo[i] = fl[i].getPath();
					listCysles[i] = "1";
				}
				if (video_view != null && VideoLen != 0) {
					video_view.setVideoPath(listVideo[0]);
					// Uri uri = Uri.parse("android.resource://" +
					// getPackageName() + "/" + R.raw.test);
					// video_view.setVideoURI(uri);
					video_view.start();
				}
			} else {
				isVideo = false;
				handler.sendEmptyMessage(113);
				// img_view.setVisibility(View.VISIBLE);
			}
		}
	}

	public void onPause() {
		super.onPause();
	}

	// 打印机线程
	static class MHandler extends Handler {
		WeakReference<MainActivity> mActivity;

		MHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			MainActivity theActivity = mActivity.get();
			switch (msg.what) {
			case Global.MSG_WORKTHREAD_SEND_CONNECTUSBRESULT: {
				int result = msg.arg1;
				// Toast.makeText(theActivity,(result == 1) ?
				// Global.toast_success: Global.toast_fail,
				// Toast.LENGTH_SHORT).show();
				if (1 == result) {
					Log.v("111", "打印机 成功");
				} else {
					Log.v("111", "打印机 失败");
				}
				break;
			}
			}
		}
	}

	// ----------------------------------------------------串口控制类
	public class SerialControl extends SerialHelper {

		// public SerialControl(String sPort, String sBaudRate){
		// super(sPort, sBaudRate);
		// }
		public SerialControl() {
		}

		@Override
		protected void onDataReceived(final ComBean ComRecData) {
			// 数据接收量大或接收时弹出软键盘，界面会卡顿,可能和6410的显示性能有关
			// 直接刷新显示，接收数据量大时，卡顿明显，但接收与显示同步。
			// 用线程定时刷新显示可以获得较流畅的显示效果，但是接收数据速度快于显示速度时，显示会滞后。
			// 最终效果差不多-_-，线程定时刷新稍好一些。
			// DispQueue.AddQueue(ComRecData);// 线程定时刷新显示(推荐)
			runOnUiThread(new Runnable() { // 直接刷新显示
				public void run() {
					DispRecData(ComRecData);
				}
			});
		}
	}

	// ----------------------------------------------------刷新显示线程
	private class DispQueueThread extends Thread {
		private Queue<ComBean> QueueList = new LinkedList<ComBean>();

		@Override
		public void run() {
			super.run();
			while (!isInterrupted()) {
				final ComBean ComData;
				while ((ComData = QueueList.poll()) != null) {
					runOnUiThread(new Runnable() {
						public void run() {
							DispRecData(ComData);// 监听
						}
					});
					try {
						Thread.sleep(100);// 显示性能高的话，可以把此数值调小。
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}

		public synchronized void AddQueue(ComBean ComData) {
			QueueList.add(ComData);
		}
	}
	// TODO 考虑用于处理分行指令
	// private StringBuffer newData = new StringBuffer();
	// private int newDataIndex = 0;

	/*
	 * HEX 81 8F 42 钱收入 (40 代表一元 41代表5元 42-10 43-20 44-50 45-100)
	 * 发送02指令收钱,0f指令吐钱,18指令挂起 11 2F 钱吐出 10 收钱 11收币出错
	 * 
	 * 发送3e请求使用 5e请求禁止 发送0c查询当前纸币机的状态
	 * 
	 * 
	 */
	// ----------------------------------------------------显示接收数据
	private void DispRecData(ComBean ComRecData) {
		String time = ComRecData.sRecTime; // 接收数据时间
		String port = ComRecData.sComPort; // 接收数据串口
		String realtime = ComRecData.sTime; // 时间戳
		String data = MyFunc.ByteArrToNospaceHex(ComRecData.bRec); // 没有空格转换后的数据
		int dataLength = data.length();

		String port2[] = port.split("/");
		String port3 = port2[2].substring(0, 5); // 串口号
		String str = data.substring(0, 2);
		// newData.append(data); //分行指令处理(未完成)

		String fileName = "/sdcard/QChouseDebug";
		File f = new File(fileName);
		String newFilename = fileName + "/" + ShopId + " " + Today + ".txt";
		File file = new File(newFilename);

		if (!f.exists()) {
			f.mkdir();
		} else {
			File[] fl = f.listFiles();
			int fileLen = 0;
			if (null != fl) {
				fileLen = fl.length;
			}
			try {
				FileInputStream fis = new FileInputStream(file);
				int filelen = fis.available();
				if (filelen > 1024) {
					if (fileLen != 0) {
						for (int i = 0; i < fileLen; i++) {
							fl[i].delete();
						}
					}
					file.delete();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileWriter writer = new FileWriter(newFilename, true);
			writer.write(data + "  ");
			writer.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Log.v("ComBean", "接收到的时间" + time);
		// Log.v("ComBean", "接收到的时间戳" + realtime);
		// Log.v("ComBean", "接收数据串口" + port);
		Log.v("ComBean", "接收到的数据 " + data);

		if (port3.equals("ttyS3") && str.equals("8F")) { // 如果收到的是8F，则是纸钞进纸
			int datalen = data.length();
			if (datalen == 2) {

			} else if (datalen == 4) {
				String money = data.substring(2, 4);
				probe();
				if (money.equals("40")) {
					int NowMoney = Money + 1;
					if (NowMoney > Integer.parseInt(Price)) {
						sendPortData(ComMoney, "0f");
					} else {
						Money = Money + 1;
						sendPortData(ComMoney, "02");
						new Thread(new Runnable() {
							public void run() {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}).start();
						sendPortData(ComMoney, "02");
						sendPortData(ComMoney, "02");
					}
				} else if (money.equals("41")) { // 5元
					int NowMoney = Money + 5;
					if (NowMoney > Integer.parseInt(Price)) {
						sendPortData(ComMoney, "0f");
					} else {
						Money = Money + 5;
						sendPortData(ComMoney, "02");
						new Thread(new Runnable() {
							public void run() {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}).start();
						sendPortData(ComMoney, "02");
						sendPortData(ComMoney, "02");
					}
				} else if (money.equals("42")) { // 10元
					int NowMoney = Money + 10;
					if (NowMoney > Integer.parseInt(Price)) {
						sendPortData(ComMoney, "0f");
					} else {
						Money = Money + 10;
						sendPortData(ComMoney, "02"); // 为了确定收钱发送三次
						new Thread(new Runnable() {
							public void run() {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}).start();
						sendPortData(ComMoney, "02");
						sendPortData(ComMoney, "02");
					}
				} else if (money.equals("43")) { // 20元
					int NowMoney = Money + 20;
					if (NowMoney > Integer.parseInt(Price)) {
						sendPortData(ComMoney, "0f");
					} else {
						Money = Money + 20;
						sendPortData(ComMoney, "02"); // 为了确定收钱发送三次
						new Thread(new Runnable() {
							public void run() {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}).start();
						sendPortData(ComMoney, "02");
						sendPortData(ComMoney, "02");
					}
				} else { // 以后可以加拓展 "0f"为吐钱
					sendPortData(ComMoney, "0f");
				}
			}
		} else if (port3.equals("ttyS3") && str.equals("10")) { // 10
																// 为收钞后显示，确认收钞后才打印
			// handler.sendEmptyMessage(101);
			if (Money == Integer.parseInt(Price)) {
				Money = 0;
				handler.sendEmptyMessage(101);
				handler.sendEmptyMessage(109);
			} else {
				handler.sendEmptyMessage(108);
			}
		} else if (port3.equals("ttyS3") && str.equals("20") || str.equals("21") || str.equals("22")) {// 收银机若遇到各种问题
			sendPortData(ComMoney, "30"); // 收银机重启
		} else if (port3.equals("ttyS3") && data.equals("7F8001F02380")) { // 发送synchronisation命令后成功
			isNewCash = true;
		} else if (port3.equals("ttyS3") && data.equals("7F") || data.equals("8001F02380")) {
			isNewCash = true;
		} else if (port3.equals("ttyS3") && dataLength < 20 && dataLength > 10
				&& (str.equals("80") || str.equals("00"))) { // 主板2
			try {

				int cmdLenth = StringHexUtils.hexStr2Bytes(data.substring(2, 4))[0];
				parseNewCashResponse(data, cmdLenth, 4);

			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
				return;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		} else if (port3.equals("ttyS3") && dataLength < 20 && dataLength > 12 && str.equals("7F")) { // 主板1
			try {
				int cmdLenth = StringHexUtils.hexStr2Bytes(data.substring(4, 6))[0];
				parseNewCashResponse(data, cmdLenth, 6);
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
				return;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		} else if (port3.equals("ttyS2")) {
			Log.i(Tag, data);
			if (data.equals("10") || data.equals("12")) {
				printingPaperTip.setVisibility(View.GONE);
			} else if (data.equals("1C") || data.equals("1E")) {
				printingPaperTip.setVisibility(View.VISIBLE);
				printingPaperTip.setText("打印纸即将用完");
			} else {
				// ShowMessage("打印机纸张用完或纸张移位，请及时处理");
				Toast.makeText(this, "打印机故障，打印纸可能已用完，请及时检查", Toast.LENGTH_LONG).show();
				printingPaperTip.setVisibility(View.VISIBLE);
				printingPaperTip.setText("打印机故障或打印纸已用完");
			}
		} else if (port3.equals("ttyS1")) { // 椅子指令 有两种主板一种会分行
			if (data.substring(0, 2).equals("AA") && data.length() == 12) { // 可能为AA00
				// AA00000649F9 AA为头 000006为椅子数据 转为2进制末尾判断
				String chairNum = data.substring(2, 8); // 椅子的编号
				String chairData = data.substring(8, 10); // 椅子的数据
				String binary = hex.HToB(chairData);
				String chairState = binary.substring(binary.length() - 1); // 判断椅子是坐下还是站起
																			// 0站起
																			// 1坐下
				String battery = binary.substring(0, binary.length() - 1); // 获取电池电压
																			// 2进制
																			// 101000
				String decBattery = hex.toD(battery, 2); // 十进制电池电压 40

				Log.v("chairState", "chairState 椅子是站起0还是坐下1 " + chairState);
				Log.v("battery", "battery 电池电压2进制 " + battery);
				Log.v("decBattery", "decBattery 电池电压10进制 " + decBattery);

				ChairData sitdownChairdata = null;
				if (chairState.equals("1")) { // 1是坐下
					sitdownChairdata = chairDao.findChairByName(DbConfig.getInstance().getDb(), chairNum);
					if (sitdownChairdata != null) {
						sitdownChairdata.setStartTime(realtime);
						chairDao.editChair(DbConfig.getInstance().getDb(), sitdownChairdata);
					} else {
						sitdownChairdata = new ChairData();
						sitdownChairdata.setStartTime(realtime);
						sitdownChairdata.setChairName(chairNum);
						chairDao.addChair(DbConfig.getInstance().getDb(), sitdownChairdata);
					}
				} else { // 站起
					ChairData standupChairdata = chairDao.findChairByName(DbConfig.getInstance().getDb(), chairNum);
					standupChairdata.setEndTime(realtime);

					String startTime = standupChairdata.getStartTime();
					int chairNumber = standupChairdata.getNumber();
					long s = Long.parseLong(realtime) - Long.parseLong(startTime);
					if (s >= 3 * 60) { // 若大于3分钟则增加
						chairNumber++;
						sendChairNum();
					}
					standupChairdata.setNumber(chairNumber);
					chairDao.editChair(DbConfig.getInstance().getDb(), standupChairdata);
				}
			} else if (data.substring(0, 2).equals("AA") && data.length() == 2) { // 如果收到只是AA
				getDataTime = realtime;
			} else if (realtime.equals(getDataTime)) {
				getDataTime = null;
				// 00000649F9 000006为椅子数据 转为2进制末尾判断
				String chairNum = data.substring(0, 6); // 椅子的编号
				String chairData = data.substring(6, 8); // 椅子的数据
				String binary = hex.HToB(chairData);
				String chairState = binary.substring(binary.length() - 1); // 判断椅子是坐下还是站起
																			// 0站起
																			// 1坐下
				String battery = binary.substring(0, binary.length() - 1); // 获取电池电压
																			// 2进制
																			// 101000
				String decBattery = hex.toD(battery, 2); // 十进制电池电压 40

				Log.v("second", "chairState 椅子是站起0还是坐下1 " + chairState);
				Log.v("second", "battery 电池电压2进制 " + battery);
				Log.v("second", "decBattery 电池电压10进制 " + decBattery);

				ChairData sitdownChairdata = null;
				if (chairState.equals("1")) { // 1是坐下
					sitdownChairdata = chairDao.findChairByName(DbConfig.getInstance().getDb(), chairNum);
					if (sitdownChairdata != null) {
						sitdownChairdata.setStartTime(realtime);
						chairDao.editChair(DbConfig.getInstance().getDb(), sitdownChairdata);
					} else {
						sitdownChairdata = new ChairData();
						sitdownChairdata.setStartTime(realtime);
						sitdownChairdata.setChairName(chairNum);
						chairDao.addChair(DbConfig.getInstance().getDb(), sitdownChairdata);
					}
				} else { // 站起
					ChairData standupChairdata = chairDao.findChairByName(DbConfig.getInstance().getDb(), chairNum);
					standupChairdata.setEndTime(realtime);

					String startTime = standupChairdata.getStartTime();
					int chairNumber = standupChairdata.getNumber();
					long s = Long.parseLong(realtime) - Long.parseLong(startTime);
					if (s >= 3 * 60) { // 若大于3分钟则增加
						chairNumber++;
						sendChairNum();
					}
					standupChairdata.setNumber(chairNumber);
					chairDao.editChair(DbConfig.getInstance().getDb(), standupChairdata);
				}

			}
		}
	}

	/**
	 * ef命令之后判断是否大于应收金额
	 */
	private void outMoney(int money) {
		int NowMoney = Money + money;
		if (NowMoney > Integer.parseInt(Price)) {
			// 退钞
			isRejectCash = false;
			Log.e("cmd", "退币 ：" + money);
			c.cancel();
			if (index % 2 == 0) {
				send80CahsData();
			} else {
				send00CashData();
			}
			index += 1;
			isRejectCash = true;
			c.start();
		}
	}

	private void printTicket(int money) {
		int NowMoney = Money + money;
		Money = Money + money;
		if (Money == Integer.parseInt(Price)) {
			Money = 0;
			handler.sendEmptyMessage(101);// 出票
			handler.sendEmptyMessage(109);// 重置投入金额显示
			Log.e("cmd", "收钱，出票");
		} else if (NowMoney < Integer.parseInt(Price)) {
			handler.sendEmptyMessage(108);// 设置继续投入金额
			Log.e("cmd", "收钱，继续投币");
		}
	}

	private int indexEF = 0;// 异常EF指令计数处理
	private boolean isCollectCash = false;

	private void parseNewCashResponse(String data, int cmdLenth, int i) throws ArrayIndexOutOfBoundsException {
		isCollectCash = false;
		String[] cmds = new String[cmdLenth];
		String cmd;
		for (int j = 0; j < cmdLenth; i += 2, j++) {
			cmd = data.substring(i, i + 2);
			cmds[j] = cmd;
		}
		for (int k = 0; k < cmdLenth; k++) {
			switch (cmds[k]) {
			case "CC":
				Log.i("cmd", "压币");
				break;
			case "E7":
				Log.i("cmd", "钱箱满，需要把纸币取走");
				break;
			case "E8":
				Log.i("cmd", "识币器处理关闭状态，需要向识币器发送Enable命令");
				sendPortData(ComMoney, "7F00010A3C08");
				break;
			case "EB":
				Log.i("cmd", "堆叠纸币结束");
				break;
			case "ED":
				Log.i("cmd", "拒绝接受纸币");
				break;
			case "EC":
				Log.i("cmd", "拒绝接受纸币执行完毕");
				indexEF = 0;
				break;
			case "EE":
				Log.i("cmd", "有效收币");
				indexEF = 0;
				isCollectCash = true;
				switch (cmds[k + 1]) {
				case "01":
					Log.i("cmd", "1元");
					printTicket(1);
					break;
				case "02":
					Log.i("cmd", "5元");
					printTicket(5);
					break;
				case "03":
					Log.i("cmd", "10元");
					printTicket(10);
					break;
				case "04":
					Log.i("cmd", "20元");
					printTicket(20);
					break;
				case "05":
					Log.i("cmd", "50元");
					printTicket(50);
					break;
				default:
					break;
				}
				return;
			case "EF":
				Log.i("cmd", "纸币位于哪个通道");
				indexEF += 1;
				initRejectEFRepeat();
				switch (cmds[k + 1]) {
				case "01":
					Log.i("cmd", "EF-1元");
					outMoney(1);
					break;
				case "02":
					Log.i("cmd", "EF-5元");
					outMoney(5);
					break;
				case "03":
					Log.i("cmd", "EF-10元");
					outMoney(10);
					break;
				case "04":
					Log.i("cmd", "EF-20元");
					outMoney(20);
					break;
				case "05":
					Log.i("cmd", "EF-50元");
					outMoney(50);
					break;
				default:
					break;
				}
				break;
			case "F1":
				Log.i("cmd", "识币器正在复位");
				break;
			case "F2":
				Log.i("cmd", "unKnown Command");
				break;
			case "F3":
				Log.i("cmd", "参数错误");
				break;
			case "F5":
				Log.i("cmd", "指令无法执行");
				break;
			default:
				break;
			}
		}
	}

	/**
	 * EF命令不正常重复发送时退币并重新开发识币器
	 */
	private void initRejectEFRepeat() {
		if (indexEF > 7) {
			setSleep();
			sendPortData(ComMoney, "7F8001116582"); // 初始化
			setWaitSleep();
			if (!isCollectCash) {
				sendPortData(ComMoney, "7F0001083388");
				setSleep();
				sendPortData(ComMoney, "7F8001080230");
				setSleep();
			}
			sendPortData(ComMoney, "7F00010A3C08");
			Log.d("cmd", "开放0A命令开放识别器");
			indexEF = 0;
			index = 0;
			setSleep();
		}
	}

	// ----------------------------------------------------串口发送
	private void sendPortData(SerialHelper ComPort, String sOut) {
		if (ComPort != null && ComPort.isOpen()) {
			// ComPort.sendTxt(sOut); // TEXT 命令
			ComPort.sendHex(sOut); // HEX 命令
		} else {
			Log.v("串口未开", "comport not ");
		}
	}

	private void sendTextData(SerialHelper ComPort, String sOut) {
		if (ComPort != null && ComPort.isOpen()) {
			ComPort.sendTxt(sOut); // TEXT 命令
			// ComPort.sendHex(sOut); // HEX 命令
		} else {
			Log.v("串口未开", "comport not ");
		}
	}

	// 椅子串口
	private void openTTYS1Port() {
		ComChair.setPort("/dev/ttyS1");
		ComChair.setBaudRate(9600);
		OpenComPort(ComChair);
	}

	// 打印机串口
	private void openTTYS2Port() {
		ComPrint.setPort("/dev/ttyS2");
		ComPrint.setBaudRate(9600);
		OpenComPort(ComPrint);
	}

	// 收银机串口
	private void openTTYS3Port() {
		ComMoney.setPort("/dev/ttyS3");
		ComMoney.setBaudRate(9600);
		OpenComPort(ComMoney);
	}

	// ----------------------------------------------------关闭串口
	private void CloseComPort(SerialHelper ComPort) {
		if (ComPort != null) {
			ComPort.stopSend();
			ComPort.close();
		}
	}

	// ----------------------------------------------------打开串口
	private void OpenComPort(SerialHelper ComPort) {
		try {
			ComPort.open();
		} catch (SecurityException e) {
			ShowMessage("打开串口失败:没有串口读/写权限!");
		} catch (IOException e) {
			ShowMessage("打开串口失败:未知错误!");
		} catch (InvalidParameterException e) {
			ShowMessage("打开串口失败:参数错误!");
		}
	}

	// ------------------------------------------显示消息
	private void ShowMessage(String sMsg) {
		Toast.makeText(this, sMsg, Toast.LENGTH_SHORT).show();
	}

	// USB端口设置
	private void probe() {
		final UsbManager mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
		Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
		if (deviceList.size() > 0) {
			// 初始化选择对话框布局，并添加按钮和事件
			while (deviceIterator.hasNext()) {
				UsbDevice device2 = deviceIterator.next();
				if (device2.getVendorId() == 1659) { // 1659
					if (mUsbManager.hasPermission(device2)) {
						USBPort port = new USBPort(mUsbManager, device2);
						TTYTermios serial = new TTYTermios(9600, FlowControl.NONE, Parity.NONE, StopBits.ONE, 8);
						if (null == WorkService.workThread) {
							Intent intent = new Intent(MainActivity.this, WorkService.class);
							startService(intent);
						} else {
							WorkService.workThread.connectUsb(port, serial);
						}
					} else {
						PendingIntent mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
								new Intent(MainActivity.this.getApplicationInfo().packageName), 0);
						mUsbManager.requestPermission(device2, mPermissionIntent);
					}
				} else {

				}
			}
		}
	}

	private long firstTime = 0;

	/*
	 * 门店收钱出票
	 */
	private void sellMachineTicket() {
		String url = Constans.URL + "sell_ticket";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("shopid", ShopId);
		params.addBodyParameter("unit_price", Price);
		HttpUtils http = new HttpUtils(15 * 1000);
		long secondTime = System.currentTimeMillis();
		// 用于限制出票，1s内只能有一次出票请求
		if (secondTime - firstTime < 1000) {
			firstTime = secondTime;
			Log.d(offlineTag, "间隔小于1s");
			return;
		}
		firstTime = secondTime;
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.d(offlineTag, error.getExceptionCode() + ":" + msg);
				probe();
				SerialOldPrint.PushLeaveTicket(MainActivity.this);// 如果没有网络，打印离线票
				SerialNewPrint.newPrintLeaveTicket();// 新离线票
				mTts.startSpeaking("正在出票，请妥善保管好小票", mTtsListener);
				addOfflineTicketNumber(); // 离线票统计
				addOfflineTicket();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.statusCode == 200) {
					String result = responseInfo.result;
					boolean isFirst = true;
					try {
						JSONObject object = new JSONObject(result).getJSONObject("result");
						String lineNum = tv_line_num.getText().toString();
						if (lineNum.equals("无")) {
							isFirst = true;
						} else {
							isFirst = false;
						}
						quenenumber1 = object.getString("queue_number");
						totalquene = object.getString("queue_size");// 排队人数
						ticketid1 = object.getString("ticket_no");
						if (isFirst) {
							handler.sendEmptyMessage(103);
						} else {
							handler.sendEmptyMessage(102);
						}
						probe();
						SerialOldPrint.pushTicket(MainActivity.this, quenenumber1, ticketid1);// 打印出票
						SerialNewPrint.newPrintTicket(quenenumber1, ticketid1); // 新打印机
						mTts.startSpeaking("正在出票，请妥善保管好小票", mTtsListener);
						// TODO 判断打印机是否打印成功
						printReport(ticketid1, "1");
						// sendOffLine();// 上传离线票
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					probe();
					SerialOldPrint.PushLeaveTicket(MainActivity.this);
					SerialNewPrint.newPrintLeaveTicket();
					mTts.startSpeaking("正在出票，请妥善保管好小票", mTtsListener);
					addOfflineTicketNumber();
					addOfflineTicket();
					Log.d(offlineTag, "ResponseCode:" + responseInfo.statusCode);
				}
			}
		});
		sendOfflineNotSend();
		// HttpPost httpPost = new HttpPost(url);
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("qc_key", Constans.QCKEY));
		// params.add(new BasicNameValuePair("shopid", ShopId));
		// params.add(new BasicNameValuePair("unit_price", Price));
		// HttpResponse httpResponse = null;
		// HttpParams httpParameters1 = new BasicHttpParams();
		// // 超时设置
		// HttpConnectionParams.setConnectionTimeout(httpParameters1, 5 * 1000);
		// HttpConnectionParams.setSoTimeout(httpParameters1, 5 * 1000);

		// try {
		// httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		// httpResponse = new DefaultHttpClient().execute(httpPost);
		// if (httpResponse.getStatusLine().getStatusCode() == 200) {
		// String result = EntityUtils.toString(httpResponse.getEntity());
		// System.out.println("result:---------" + result);
		// boolean isFirst = true;
		// try {
		// JSONObject object = new JSONObject(result).getJSONObject("result");
		// String lineNum = tv_line_num.getText().toString();
		// if (lineNum.equals("无")) {
		// isFirst = true;
		// } else {
		// isFirst = false;
		// }
		// quenenumber1 = object.getString("queue_number");
		// totalquene = object.getString("queue_size");// 排队人数
		// ticketid1 = object.getString("ticket_no");
		// if (isFirst) {
		// handler.sendEmptyMessage(103);
		// } else {
		// handler.sendEmptyMessage(102);
		// }
		// probe();
		// SerialOldPrint.pushTicket(MainActivity.this, quenenumber1,
		// ticketid1);// 打印出票
		// SerialNewPrint.newPrintTicket(quenenumber1, ticketid1); // 新打印机
		// mTts.startSpeaking("正在出票，请妥善保管好小票", mTtsListener);
		// printReport(ticketid1, "1");
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// } else {
		// probe();
		// SerialOldPrint.PushLeaveTicket(MainActivity.this);
		// SerialNewPrint.newPrintLeaveTicket();
		// mTts.startSpeaking("正在出票，请妥善保管好小票", mTtsListener);
		// addOfflineTicketNumber();
		// Log.d(offlineTag, "ResponseCode:" +
		// httpResponse.getStatusLine().getStatusCode());
		// }
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// Log.d(offlineTag, "IOException");
		// e.printStackTrace();
		// // 超时设置
		// probe();
		// SerialOldPrint.PushLeaveTicket(MainActivity.this);// 如果没有网络，打印离线票
		// SerialNewPrint.newPrintLeaveTicket();// 新离线票
		// mTts.startSpeaking("正在出票，请妥善保管好小票", mTtsListener);
		// addOfflineTicketNumber(); // 离线票统计
		// }
	}

	/**
	 * 票券打印报告
	 * 
	 * @param ticketNo
	 *            票号
	 * @param status
	 *            票券打印结果 1=成功 0=失败
	 * @return
	 */
	private void printReport(String ticketNo, String status) {
		String url = Constans.URL + "print_report";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key", Constans.QCKEY);
		params.addBodyParameter("ticket_no", ticketNo);
		params.addBodyParameter("status", status);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.w(offlineTag, "HttpException.code:" + arg0.getExceptionCode() + "  上传失败");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					if (code.equals("200")) {

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void setSystemTime(final Context cxt, String datetimes) {
		// yyyyMMdd.HHmmss】
		try {
			Process process = Runtime.getRuntime().exec("su");
			// String datetime = "20160101.105000"; // 测试的设置的时间【时间格式
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd.HHmmss");
			Date d = sdf.parse(datetimes);
			c.setTime(d);
			// c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 8);
			datetimes = sdf1.format(c.getTime());
			String datetime = ""; // 测试的设置的时间【时间格式
			datetime = datetimes.toString(); // yyyyMMdd.HHmmss】
			System.setProperty("user.timezone", "Asia/Shanghai");
			TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
			TimeZone.setDefault(tz);
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			// os.writeBytes("setprop persist.sys.timezone GMT\n");
			// os.writeBytes("setprop persist.sys.timezone=Asia/Shanghai\n");
			os.writeBytes("/system/bin/date -s " + datetime + "\n");
			os.writeBytes("clock -w\n");
			os.writeBytes("exit\n");
			os.flush();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(cxt, "请获取Root权限", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSystemTime(String time) {
		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(time);
			c.setTime(d);
			AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
			am.setTime(c.getTimeInMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private CountDownTimer cdt = new CountDownTimer(1000 * 60 * 60, 60 * 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			ComPrint.send(state0);
			Log.v(Tag, "传送卷纸传感器状态");
		}

		@Override
		public void onFinish() {
			cdt.start();
			index = 0;
		}
	};
}