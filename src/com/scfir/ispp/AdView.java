package com.scfir.ispp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class AdView extends RelativeLayout {

	private int width;
	private int height;
	static final int VIEWFLIPPERID = 0x8765400;
	static final int CLOSEIMGID = 0x8765401;
	static final int PCVID = 0x8765402;
	static final int POPIMGID = 0x8765403;
	static final int TSIZEID = 0x8765404;
	static final int DBTNID = 0x8765405;
	static final int BGVIEWID = 0x8765406;
	static final int RVIEWID = 0x8765407;
	
	static final int EXITBTNID = 0x8765408;
	static final int CANCELBTNID = 0x8765409;
	static final int MOREBTNID = 0x8765410;

	public AdView(Context context) {
		super(context);
		setDispaly(context);//获取当前手机的屏幕的宽高，并且将值放置在全局width，height
		initview(context);
	}
	
	public AdView(Context context,int type) {
		super(context);
		setDispaly(context);
	}

	private void initview(Context context) {
		//屏幕的宽高取最小值
		int temp = width < height ? width : height;
		
		int mwidth = (int) (temp * 0.75);//%75的手机宽度
		
//		this.setGravity(Gravity.CENTER);

		//充满全屏的透明背景
		LinearLayout bgview = new LinearLayout(context);
		bgview.setId(BGVIEWID);
		bgview.setBackgroundColor(Color.TRANSPARENT);
		
		RelativeLayout.LayoutParams bgviewParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		this.addView(bgview, bgviewParams);
		

		//计算过宽高的悬浮框
		RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(mwidth, mwidth);
		viewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		RelativeLayout rview = new RelativeLayout(context);
		rview.setId(RVIEWID);
		rview.setBackgroundColor(Color.TRANSPARENT);
		this.addView(rview, viewParams);
		
		//悬浮框上图片
		ImageView img = new ImageView(context);
		img.setId(VIEWFLIPPERID);
		
		//通过当前的方式获取图片对应的Drawable
		Drawable loadDrawable = AdUtil.readDrawable(context, "tank.png");
		
		img.setBackgroundDrawable(loadDrawable);
		RelativeLayout.LayoutParams vfParams = new RelativeLayout.LayoutParams(mwidth, mwidth);
		vfParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		rview.addView(img, vfParams);
	}

	private void setDispaly(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		width = dm.widthPixels;
		height = dm.heightPixels;
		if (width <= 320) {
			width = (int) Math.ceil(width * density);
			height = (int) Math.ceil(height * density);
		}
	}
}
