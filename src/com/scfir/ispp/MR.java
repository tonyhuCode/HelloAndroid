package com.scfir.ispp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MR extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		PopManager.init(context);//初始化获取图片
		PopManager.show(context);//显示插屏广告，两种方式显示(对话框Dialog，activity)
	}
}
