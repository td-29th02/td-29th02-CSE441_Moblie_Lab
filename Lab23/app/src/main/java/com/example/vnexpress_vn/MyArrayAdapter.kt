package com.example.vnexpress_vn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyArrayAdapter(
    private val context: Context,
    private val data: List<NewsItem>
) : BaseAdapter() {

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            holder = ViewHolder(
                view.findViewById(R.id.imgNews),
                view.findViewById(R.id.txtTitle),
                view.findViewById(R.id.txtInfo)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val news = data[position]
        holder.img.setImageBitmap(news.img)
        holder.title.text = news.title
        holder.info.text = news.info

        return view
    }

    private data class ViewHolder(
        val img: ImageView,
        val title: TextView,
        val info: TextView
    )
}
