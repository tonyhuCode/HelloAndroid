package com.scfir.ispp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MA extends Activity {
	private View adview;
	private Activity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		if(context==null){//？
			System.out.println("oncreate........................................");
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setActivity(this);
		}
		init(context);
	}
	
	public void setActivity(Activity paramActivity) {
		context = paramActivity;
		System.out.println("setActivity context = "+context.getClass());
		System.out.println("setActivity........................................");
	}
	
	private void init(Context context) {
		adview = new AdView(context);
		((Activity) context).setContentView(adview);
	}
}
