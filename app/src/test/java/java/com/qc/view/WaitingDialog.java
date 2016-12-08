package java.com.qc.view;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.qc.qchouse.R;

public class WaitingDialog extends Dialog {
	
	private ImageView img_waiting;
	
	public WaitingDialog(Context context,int theme) {
		super(context, theme);
		this.setContentView(R.layout.dialog_waiting);
		
		this.setCanceledOnTouchOutside(false);
		img_waiting = (ImageView)findViewById(R.id.img_waiting2);
	}
	
	@Override
	public void show(){
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);  
		LinearInterpolator lin = new LinearInterpolator();  
		animation.setInterpolator(lin);  
		img_waiting.setAnimation(animation);
		animation.startNow();
		super.show();
		
//		animation = new RotateAnimation(0f, 339f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		animation.setInterpolator(new LinearInterpolator());
//		animation.setRepeatCount(Animation.INFINITE);
//		animation.setDuration(600);		
//		img_waiting.setAnimation(animation);
//		animation.startNow();
	}
	
	@Override
	public void dismiss(){
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);  
		animation.cancel();
		super.dismiss();
	}

}
