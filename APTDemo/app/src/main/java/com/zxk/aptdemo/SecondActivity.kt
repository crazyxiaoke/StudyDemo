package com.zxk.aptdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zxk.apt_annotation.AptDemoAnnotation

/**
 * @ClassName com.zxk.aptdemo
 * @author zhengxiaoke
 * @Description
 * @Date： 2024-01-18 15:30
 */
class SecondActivity:AppCompatActivity() {

    @AptDemoAnnotation(desc = "我是SecondActivity test变量")
    var test:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        String::class.java
    }
}