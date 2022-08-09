package com.example.test

import android.os.Parcelable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.parcel.Parcelize


class NewsListAdapter(list: MutableList<NewsBean>) : BaseQuickAdapter<NewsBean, BaseViewHolder>(R.layout.layout_item_news, list) {

    override fun convert(holder: BaseViewHolder, item: NewsBean) {
        holder.setText(R.id.tv_title,item.name)
    }

}

@Parcelize  //kotlin对于实现Parcelable接口很友好  https://blog.csdn.net/qq_29598727/article/details/105983182
class NewsBean(
    var id: Int?, var name: String?, var content: String?
) : Parcelable