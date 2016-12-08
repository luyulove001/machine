package java.com.qc.qchouse;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.qc.qchouse.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.com.qc.utils.Constans;
import java.com.qc.view.WaitingDialog;

public class FirstRegistActivity extends Activity {

	private Button btn_determine,btn_cancel;
	private EditText et_input;
	private WaitingDialog waitingDialog;
	private String qc_key;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_regist);
		
		initView();
	}
	
	private void initView() {
		waitingDialog = new WaitingDialog(FirstRegistActivity.this, R.style.callDialogTheme);
		
		et_input = (EditText) findViewById(R.id.et_input);
		
		btn_determine = (Button) findViewById(R.id.btn_determine);
		btn_determine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				waitingDialog.show();
				final String Keyword = et_input.getText().toString().trim();
				if(Keyword.equals("")){
					waitingDialog.dismiss();
					Toast.makeText(FirstRegistActivity.this, "��������Կ", Toast.LENGTH_SHORT).show();
				}else {
					setShop(Keyword);
				}
			}
		});
		
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
	
	/**
	 * �ŵ��������
	 * @param secret ��Կ
	 */
	private void setShop(final String secret) {
		String url = Constans.URL + "set_shop";
		RequestParams params = new RequestParams();
		params.addBodyParameter("qc_key",Constans.QCKEY);
		params.addBodyParameter("secret_key",secret);
		params.addBodyParameter("machine_version", "v" + getVersion());
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, url, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(FirstRegistActivity.this, "�������ӳ�ʱ", Toast.LENGTH_SHORT).show();
				waitingDialog.dismiss();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					String code = object.getString("code");
					if(code.equals("200")){
						JSONObject jsondata = object.getJSONObject("result");
						String ShopId = jsondata.getString("shopid"); 
						String ShopName = jsondata.getString("shopname"); 
						String Price = jsondata.getString("cash_price"); 
						
						SharedPreferences sp = getSharedPreferences("ShopId",Context.MODE_PRIVATE);
						SharedPreferences chai = getSharedPreferences("Chairs",Context.MODE_PRIVATE);
						Editor ed = sp.edit();
						Editor edchair = chai.edit();
						ed.putString("id", ShopId);
						ed.putString("shopname", ShopName);
						ed.putString("price",Price);
						ed.putString("secret", secret);
						ed.commit();
						//ɾ�����Ӽ���
						edchair.clear();
						edchair.commit();
						
						Intent intent = new Intent(FirstRegistActivity.this,MainActivity.class);
						startActivity(intent);
						finish();
						waitingDialog.dismiss();
						
					}else { //��Կ����ȷ
						waitingDialog.dismiss();
						String message = object.getString("result");
						ExitDialog(message);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * ��ȡ�汾��
	 * 
	 * @return String �汾��
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
	
	private void ExitDialog(String message) {
		Builder builder = new Builder(FirstRegistActivity.this);
		builder.setCancelable(false); 
		builder.setMessage(message);
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		builder.create().show();
	}
	
	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

}
