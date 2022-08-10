package com.example.test

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list_news.*

class ListNewsActivity : AppCompatActivity() {

    private var mViewRect = Rect()
    private var mFirstExpose = false
    private var mList = mutableListOf<NewsBean>()
    private var mExposeList = mutableListOf<Int>()  //已经曝光的列表

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      StatusbarUtil.transparentMode(this, true,false)
        setContentView(R.layout.activity_list_news)

        for (i in 0 .. 100) {
            mList.add(NewsBean(i, "hello world $i", "你好"))
        }
        var newsAdapter = NewsListAdapter(mList)

        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_news.layoutManager = layoutManager
        rv_news.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_news.adapter = newsAdapter

        initView()
    }

    fun initView() {

        rv_news.viewTreeObserver.addOnGlobalLayoutListener {

            if (rv_news.childCount == 0) {
                return@addOnGlobalLayoutListener
            }

//            if (!mFirstExpose) {
//                Log.e("xxx", "开始展示首屏")
//                checkChildExposeStatus()
//                mFirstExpose = true
//            }

        }

        rv_news.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            /**
             *  @param recyclerView
             *  @param dx 水平（horizontal scroll）滚量（距离）
             *  @param dy 竖直（vertical scroll）滚动量
             */
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

//                when {
//                    dy == 0 -> {
//                        Log.e("xxx", "没有滚动->$dy")
//                    }
//                    dy > 0 -> {
//                        Log.e("xxx","向上滚动->$dy")
//                    }
//                    dy <0 -> {
//                        Log.e("xxx","向下滚动->$dy")
//                    }
//                }
                checkChildExposeStatus()
            }
        })
    }

    private fun checkChildExposeStatus() {
        val length = rv_news.adapter!!.itemCount
        var view: View? = null

        //这里的 0..5 左右都是闭区间的  012345,    0 until 5 左闭右开   01234
        for (i in 0 .. length) {
//            Log.e("xxx","循环检查$i")
            view = rv_news.getChildAt(i)  //只有首屏能容纳item个数+1个childView，这种打点方式不可行
            if (view != null) {
                val isVisible = view.getGlobalVisibleRect(mViewRect)  //复用，总共只有首屏个数+1个item

                Log.e("xxx",i.toString()+ isVisible.toString())

                Log.e("xxx","$i:mVewiRect.height->"+mViewRect.height())
                Log.e("xxx","$i:mVewiRect.top->"+mViewRect.top)
                if (!mExposeList.contains(i)) {
                    Log.e("xxx", "曝光位置:$i ->" + mList[i].name)
                    mExposeList.add(i)
                }

            }
        }
    }
}