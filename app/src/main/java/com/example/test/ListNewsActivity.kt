package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_news.*

class ListNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        var list = mutableListOf<NewsBean>()
        for (i in 0..100) {
            list.add(NewsBean(i, "hello world $i", "你好"))
        }
        var newsAdapter = NewsListAdapter(list)
        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_news.layoutManager = layoutManager
        rv_news.adapter = newsAdapter

    }
}