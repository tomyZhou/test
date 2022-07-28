package com.example.test

import android.util.Log
import java.util.*

class KotlinSample {

    //直接定义函数

    //带参数，没有返回值的函数
    private var myPrint: (msg: String) -> Unit = { msg -> Log.e("xxx5",msg) }

    //带参数，有返回值的函数
    private var hello : (msg: String) -> String = { "hello ${it.uppercase()}!!" }

    //函数作为参数
    fun callMethods() {
        method1 {
            var hello = "hello world"
            Log.e("xxx1", hello)
            hello
        }

        method2 {
            var hello = "hello world"
            Log.e("xxx2", hello)
        }

        method3("hello world") { msg ->
            Log.e("xxx3", msg)
        }

        //函数作为返回值
        var method4 = method4("hello world")
        method4.invoke()

        //使用定义的函数
        myPrint.invoke("hello world")

        var greet = hello("zhang san")
        myPrint.invoke(greet)
    }

    //返回String 但是不需要return 直接将字符串写在最后一行
    private fun method1(method: () -> String) {
        method.invoke()
    }

    //Unit表示没有返回值
    private fun method2(method: () -> Unit) {
        method.invoke()
    }

    //函数作为参数，有一个输入参数时。不能直接带给它，需要另外一个参数传进来。
    private fun <T> method3(msg1: T, method: (msg: T) -> Unit) {
        method.invoke(msg1)
    }


    //函数作为返回值
    private fun method4(str: String): () -> Unit {
        Log.e("xxx4", "这一部分不返回，直接运行")
        return {
            var strNew = str.uppercase(Locale.getDefault())
            Log.e("xxx4", strNew)
        }
    }

}