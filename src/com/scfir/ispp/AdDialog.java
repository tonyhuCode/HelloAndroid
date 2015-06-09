package com.scfir.ispp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

public class AdDialog extends Dialog {
	private AdView adview;
	public AdDialog(Context context, int theme) {
		super(context, 16973840);//16973840让其找不到指定样式即可了
	}
	
	public void init(Context context){
		if(adview==null){
			adview = new AdView(context);
			adview.setBackgroundColor(Color.argb(60, 0, 0, 0));
		}
		setContentView(adview);
		System.out.println("通过弹出Dialog显示广告");
		show();
	}
}
