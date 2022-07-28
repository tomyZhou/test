package com.example.test

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object PrintTimeUtil {

    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
    var date: Date? = null

    //打印当前时间
    fun time(string:String) {
        if (BuildConfig.DEBUG) {
            date = Date()
            Log.d("Time-xx:$string", simpleDateFormat.format(date))
        }
    }
}