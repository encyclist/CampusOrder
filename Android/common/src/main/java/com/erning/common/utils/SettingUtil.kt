package com.erning.common.utils;

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

class SettingUtil {
    companion object{
        fun openNotifySetting(context: Context,channelId:String){
            try {
                // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                val intent = Intent()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.action = Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS
                    //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
                }else{
                    //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                    intent.putExtra("app_package", context.packageName)
                    intent.putExtra("app_uid", context.applicationInfo.uid)
                }

                // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
                //  if ("MI 6".equals(Build.MODEL)) {
                //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                //      Uri uri = Uri.fromParts("package", getPackageName(), null);
                //      intent.setData(uri);
                //      // intent.setAction("com.android.settings/.SubSettings");
                //  }
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
                val intent = Intent()

                //下面这种方案是直接跳转到当前应用的设置界面。
                //https://blog.csdn.net/ysy950803/article/details/71910806
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        }
    }
}
