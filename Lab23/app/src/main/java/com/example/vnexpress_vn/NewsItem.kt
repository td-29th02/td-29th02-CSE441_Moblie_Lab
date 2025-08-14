package com.example.vnexpress_vn

import android.graphics.Bitmap

data class NewsItem(
    var img: Bitmap?,
    var title: String,
    var info: String,
    var link: String
)
