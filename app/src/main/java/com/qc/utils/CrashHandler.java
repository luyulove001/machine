package com.qc.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.qc.qchouse.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 * UncaughtException������,��������Uncaught�쳣��ʱ��,�и������ӹܳ���,����¼���ʹ��󱨸�. 
 *  
 * @author user 
 *  
 */  
public class CrashHandler implements UncaughtExceptionHandler {  
      
    public static final String TAG = "CrashHandler";  
      
    //ϵͳĬ�ϵ�UncaughtException������   
    private UncaughtExceptionHandler mDefaultHandler;
    //CrashHandlerʵ��  
    private static CrashHandler INSTANCE = new CrashHandler();
    //�����Context����  
    private Context mContext;  
    //�����洢�豸��Ϣ���쳣��Ϣ  
    private Map<String, String> infos = new HashMap<String, String>();  
  
    //���ڸ�ʽ������,��Ϊ��־�ļ�����һ����  
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
  
    /** ��ֻ֤��һ��CrashHandlerʵ�� */  
    private CrashHandler() {  
    }  
  
    /** ��ȡCrashHandlerʵ�� ,����ģʽ */  
    public static CrashHandler getInstance() {
        return INSTANCE;  
    }  
  
    /** 
     * ��ʼ�� 
     *  
     * @param context 
     */  
    public void init(Context context) {  
        mContext = context;  
        //��ȡϵͳĬ�ϵ�UncaughtException������  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
        //���ø�CrashHandlerΪ�����Ĭ�ϴ�����  
        Thread.setDefaultUncaughtExceptionHandler(this);  
    }  
  
    /** 
     * ��UncaughtException����ʱ��ת��ú��������� 
     */  
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {  
        if (!handleException(ex) && mDefaultHandler != null) {  
            //����û�û�д�������ϵͳĬ�ϵ��쳣������������  
            mDefaultHandler.uncaughtException(thread, ex);  
        } else {  
            try {  
                Thread.sleep(3000);  
            } catch (InterruptedException e) {  
                Log.e(TAG, "error : ", e);  
            }  
            //�˳�����  
            android.os.Process.killProcess(android.os.Process.myPid());  
            System.exit(1);  
        }  
    }  
  
    /** 
     * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����. 
     *  
     * @param ex 
     * @return true:��������˸��쳣��Ϣ;���򷵻�false. 
     */  
    private boolean handleException(Throwable ex) {  
        if (ex == null) {  
            return false;  
        }  
        //ʹ��Toast����ʾ�쳣��Ϣ  
        new Thread() {  
            @Override  
            public void run() {  
                Looper.prepare(); 
                
                Looper.loop();  
            }  
        }.start();  
        //�ռ��豸������Ϣ   
        collectDeviceInfo(mContext);  
        //������־�ļ�   
        String name = saveCrashInfo2File(ex); 
//        sendFile(name,"/sdcard/QChouseDebug/" + name);
//        sendFile(MainActivity.ShopId + " " + MainActivity.Today,"/sdcard/QChouseDebug/" + MainActivity.ShopId + " " + MainActivity.Today + ".txt");
        return true;  
    }  
      
    private void sendFile(String filename,String path) {
    	// �����ϴ�����
    	PutObjectRequest put = new PutObjectRequest(Constans.testBucket, filename, path);

    	// �ļ�Ԫ��Ϣ�������ǿ�ѡ��
    	// ObjectMetadata metadata = new ObjectMetadata();
    	// metadata.setContentType("application/octet-stream"); // ����content-type
    	// metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath)); // У��MD5
    	// put.setMetadata(metadata);

    	try {
    	    PutObjectResult putResult = MainActivity.oss.putObject(put);
    	    Log.d("PutObject", "UploadSuccess");
    	    Log.d("ETag", putResult.getETag());
    	    Log.d("RequestId", putResult.getRequestId());
    	} catch (ClientException e) {
    	    // �����쳣�������쳣��
    	    e.printStackTrace();
    	} catch (ServiceException e) {
    	    // �����쳣
    	}
    	// task.cancel(); // ����ȡ������
    	// task.waitUntilFinished(); // ���Եȴ��������
    }
    
    /** 
     * �ռ��豸������Ϣ 
     * @param ctx 
     */  
    public void collectDeviceInfo(Context ctx) {  
        try {  
            PackageManager pm = ctx.getPackageManager();  
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
            if (pi != null) {  
                String versionName = pi.versionName == null ? "null" : pi.versionName;  
                String versionCode = pi.versionCode + "";  
                infos.put("versionName", versionName);  
                infos.put("versionCode", versionCode);  
            }  
        } catch (NameNotFoundException e) {  
            Log.e(TAG, "an error occured when collect package info", e);  
        }  
        Field[] fields = Build.class.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
                field.setAccessible(true);  
                infos.put(field.getName(), field.get(null).toString());  
                Log.d(TAG, field.getName() + " : " + field.get(null));  
            } catch (Exception e) {  
                Log.e(TAG, "an error occured when collect crash info", e);  
            }  
        }  
    }  
  
    /** 
     * ���������Ϣ���ļ��� 
     *  
     * @param ex 
     * @return  �����ļ�����,���ڽ��ļ����͵������� 
     */  
    private String saveCrashInfo2File(Throwable ex) {  
          
        StringBuffer sb = new StringBuffer();  
        for (Map.Entry<String, String> entry : infos.entrySet()) {  
            String key = entry.getKey();  
            String value = entry.getValue();  
            sb.append(key + "=" + value + "\n");  
        }  
          
        Writer writer = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(writer);  
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();  
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }  
        printWriter.close();  
        String result = writer.toString();  
        sb.append(result);  
        try {  
            long timestamp = System.currentTimeMillis();  
            String time = formatter.format(new Date());  
            String fileName = "crash-" + MainActivity.ShopId + "-" + time + "-" + timestamp + ".txt";  
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
                String path = "/sdcard/QChouseDebug/";  
                File dir = new File(path);  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                FileOutputStream fos = new FileOutputStream(path + fileName);  
                fos.write(sb.toString().getBytes());  
                fos.close();  
            }  
            return fileName;  
        } catch (Exception e) {  
            Log.e(TAG, "an error occured while writing file...", e);  
        }  
        return null;  
    }  
} 