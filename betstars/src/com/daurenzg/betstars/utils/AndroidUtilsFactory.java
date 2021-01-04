package com.daurenzg.betstars.utils;

import android.content.Context;
import android.os.Build;

public class AndroidUtilsFactory {

	private static AndroidUtilsFactory instanse = null;
	private Context context = null;
	
	public static AndroidUtilsFactory getInstanse(Context context){
		if (instanse == null){
			instanse = new AndroidUtilsFactory(context);
		}
		return instanse;
	}
	
	private AndroidUtilsFactory(Context context){
		super();
		this.context = context;
	}
	
	public IAndroidUtils getAndroidUtils(){
		int apiLevel = Build.VERSION.SDK_INT;
		switch (apiLevel) {
		case 3:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 4:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 7:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 8:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 10:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 11:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 12:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 13:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 14:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 15:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 16:
			return AndroidUtilLevel7Impl.getInstanse(context);
		case 17:
			return AndroidUtilLevel7Impl.getInstanse(context);
		default:
			return AndroidUtilLevel7Impl.getInstanse(context);
		}
	}
	
}
