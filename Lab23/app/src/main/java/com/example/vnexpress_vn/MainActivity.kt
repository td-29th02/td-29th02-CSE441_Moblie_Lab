package com.example.vnexpress_vn

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val newsList = mutableListOf<NewsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        LoadNewsTask().execute("https://vnexpress.net/rss/tin-moi-nhat.rss")
    }

    inner class LoadNewsTask : AsyncTask<String, Void, List<NewsItem>>() {
        override fun doInBackground(vararg params: String?): List<NewsItem> {
            val url = URL(params[0])
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream

            val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream)
            doc.documentElement.normalize()

            val nodeList: NodeList = doc.getElementsByTagName("item")

            for (i in 0 until nodeList.length) {
                val element = nodeList.item(i) as Element
                val title = element.getElementsByTagName("title").item(0).textContent
                val link = element.getElementsByTagName("link").item(0).textContent
                val description = element.getElementsByTagName("description").item(0).textContent

                // Tìm ảnh trong description
                val imgUrl = Regex("src=\"(.*?)\"").find(description)?.groups?.get(1)?.value
                val bitmap = if (imgUrl != null) {
                    try {
                        val imgStream = URL(imgUrl).openStream()
                        BitmapFactory.decodeStream(imgStream)
                    } catch (e: Exception) {
                        null
                    }
                } else null

                newsList.add(NewsItem(bitmap, title, description, link))
            }
            return newsList
        }

        override fun onPostExecute(result: List<NewsItem>?) {
            super.onPostExecute(result)
            if (result != null) {
                val adapter = MyArrayAdapter(this@MainActivity, result)
                listView.adapter = adapter
            }
        }
    }
}
