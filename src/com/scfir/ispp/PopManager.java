package com.scfir.ispp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

public class PopManager {
	private static String ACTIVITY_INFO = "activity_info";
	private static String LOCALACTIVITY = "localactivity";
	private static Class<?> adActivity = null;
	
	public static void show(Context context) {
		if (context instanceof Activity
				&& !((Activity) context).isFinishing()) {//通过传递context参数去判断开启方式，Dialog
			AdDialog ad = new AdDialog(context, 0);
			ad.init(context);
		} else {//开启activity
			try {
				Class<?> clazz = PopManager.getActivity(context);

				if (clazz != null) {
					Intent i = new Intent(context, clazz);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
					System.out.println("通过开启activity显示广告");
				} else {
					Log.i("log", "no activity config");
				}
			} catch (Exception e) {
				Toast.makeText(context, "hava you config activity?", 1).show();
				e.printStackTrace();
			}
		}
	}
	
    public static void setLocalActivity(Context context,String paramClass){
    	 SharedPreferences userInfo = context.getSharedPreferences(PopManager.ACTIVITY_INFO, 0);  
    	 userInfo.edit().putString(PopManager.LOCALACTIVITY, paramClass).commit();
    }
    
	private static Class<?> getActivity(Context context) {
        if (context == null) {
            return null;
        }
        //如果类名称有存储
        SharedPreferences userInfo = context.getSharedPreferences(PopManager.ACTIVITY_INFO, 0);  
        String lactivityName = userInfo.getString(PopManager.LOCALACTIVITY, "");  
		
		if("".equals(lactivityName)){
	        if (adActivity == null) {//为空
	            adActivity = PopManager.getChildActivityClass(context, MA.class);//如果有MyActivity extends MA，则返回MyActivity对象
	        }
		}else{
			try {
				adActivity = Class.forName(lactivityName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
        return adActivity;
    
	}
	
	public static Class<?> getChildActivityClass(Context paramContext,
			Class<?> paramClass) {//paramClass即是MA.class
		ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo();
		String str = localApplicationInfo.packageName;//应用包明
		
		try {
			//获取当前包中的信息
			PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(str, PackageManager.GET_ACTIVITIES);
			
			ActivityInfo[] arrayOfActivityInfo = localPackageInfo.activities;
			
			for (int i = 0; i < arrayOfActivityInfo.length; i++) {
				try {
					Class<?> localClass = Class.forName(arrayOfActivityInfo[i].name);
					boolean bool = paramClass.isAssignableFrom(localClass);//MA母类，可继承
					if (bool)
						return localClass;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return null;
	}

	public static void init(Context context) {
		AdUtil.UnZipFolder(context);
	}
}
