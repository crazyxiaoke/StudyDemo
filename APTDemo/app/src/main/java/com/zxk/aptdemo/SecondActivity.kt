package com.zxk.aptdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.zxk.apt_annotation.AptDemoAnnotation

/**
 * @ClassName com.zxk.aptdemo
 * @author zhengxiaoke
 * @Description
 * @Date： 2024-01-18 15:30
 */
@Route(path="/main/second")
class SecondActivity:AppCompatActivity() {

    @AptDemoAnnotation(desc = "我是SecondActivity test变量")
    var test:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}