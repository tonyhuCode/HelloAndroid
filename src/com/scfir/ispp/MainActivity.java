package com.scfir.ispp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PopManager.init(getApplicationContext());//初始化获取图片
		
		//new commit test.
		
		//显示插屏广告，两种方式显示(对话框Dialog，activity),在show函数中判断哪种方式
		//PopManager.show(getApplicationContext());  //activity
		PopManager.show(MainActivity.this);  //对话框Dialog
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
