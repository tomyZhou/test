package com.example.test

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt

object StatusbarUtil {


    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return context.resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    /**
     * 设置状态栏透明
     *
     */
    fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 设置状态栏背景颜色
     */
    fun setStatusBarBgColor(activity: Activity, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = color
        }
    }


    /**
     * 设置状态栏白色 和 图标黑色
     */
    fun lightMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            activity.window.statusBarColor = Color.WHITE  //白底

            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR    //黑字
        }
    }

    /**
     *   设置状态栏颜色 和 图标颜色
     *   backgroundColor  背景颜色
     *   isIconBlack      图标+文本颜色    true 是黑色  false 白色
     */
    fun customColorMode(activity: Activity, backgroundColor: String, isIconBlack: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            activity.window.statusBarColor = Color.parseColor(backgroundColor)  //白底

            if (isIconBlack) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR    //黑色图标+文字
            } else {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE  //白色图标+文字
            }
        }
    }

    /**
     *  设置状态栏透明 是否全屏 图标颜色
     *
     *  isIconBlack   true 黑色黑标+文字  false白色
     *
     *  isFullScreen 是否是全屏 true全屏，可以实现沉浸式状态栏
     */
    fun transparentMode(activity: Activity, isIconBlack: Boolean, isFullScreen: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            activity.window.statusBarColor = Color.TRANSPARENT

            if (isIconBlack) {
                if (isFullScreen) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  //黑色图标+文字 全屏
                } else {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR   //黑色图标+文字
                }
            } else {
                if (isFullScreen) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //白色图标+文字  全屏
                } else {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                }
            }
        }
    }

}