package com.zxk.aptdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zxk.apt_annotation.AptDemoAnnotation
import com.zxk.apt_api.MyAptApi


@Route(path="/main/main")
class MainActivity : AppCompatActivity() {

    @JvmField
    @Autowired(name="id")
    var id:Int=0

    @AptDemoAnnotation(desc = "MainActivity test变量")
    var test:String=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyAptApi.init(this)
        val tvBtn=findViewById<TextView>(R.id.tv_btn)
        tvBtn.setOnClickListener {
        }
    }
}