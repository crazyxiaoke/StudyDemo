package com.zxk.aptdemo

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.zxk.aptdemo.uitl.ClassUtils

/**
 * @ClassName com.zxk.aptdemo
 * @author zhengxiaoke
 * @Description
 * @Date： 2024-01-19 15:37
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        val classNames=ClassUtils.getFileNameByPackageName(this,"com.zxk.manager")
        classNames.forEach {
            Log.d("TAG","path=$it")
        }
    }
}