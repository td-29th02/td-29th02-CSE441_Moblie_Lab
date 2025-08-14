package com.example.parsejson_bank

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var lvTigia: ListView
    private lateinit var txtdate: TextView
    private lateinit var dstygia: ArrayList<Tygia>
    private lateinit var myadapter: MyArrayAdapter

    // Ánh xạ mã tiền tệ với resource hình ảnh
    private lateinit var imageMap: HashMap<String, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvTigia = findViewById(R.id.lv1)
        txtdate = findViewById(R.id.txtdate)

        getdate()
        dstygia = ArrayList()
        myadapter = MyArrayAdapter(this, R.layout.layout_listview, dstygia)
        lvTigia.adapter = myadapter

        initImageMap()

        TyGiaTask().execute()
    }

    private fun getdate() {
        val currentDate = Calendar.getInstance().time
        val simpleDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        txtdate.text = "Hôm Nay: ${simpleDate.format(currentDate)}"
    }

    private fun initImageMap() {
        imageMap = HashMap()
        imageMap["AUD"] = R.drawable.aud
        imageMap["CAD"] = R.drawable.cad
        imageMap["CHF"] = R.drawable.chf
        imageMap["EUR"] = R.drawable.eur
        imageMap["GBP"] = R.drawable.gbp
        imageMap["JPY"] = R.drawable.jpy
        imageMap["USD"] = R.drawable.usd
        imageMap["VND"] = R.drawable.vnd
    }

    inner class TyGiaTask : AsyncTask<Void, Void, ArrayList<Tygia>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<Tygia> {
            val ds = ArrayList<Tygia>()
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            try {
                val url = URL("https://open.er-api.com/v6/latest/USD")
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", "application/json")

                val inputStream = connection.inputStream
                val isr = InputStreamReader(inputStream, Charsets.UTF_8)
                reader = BufferedReader(isr)
                val builder = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }

                val json = builder.toString()
                Log.d("JSON_API", json)

                val jsonObject = JSONObject(json)
                val rates = jsonObject.getJSONObject("rates")

                val keys = rates.keys()
                while (keys.hasNext()) {
                    val currency = keys.next()
                    val value = rates.getDouble(currency)

                    val tygia = Tygia()
                    tygia.type = currency
                    tygia.muatienmat = value.toString()
                    tygia.muack = value.toString()
                    tygia.bantuenmat = value.toString()
                    tygia.banck = value.toString()

                    val resId = imageMap[currency]
                    tygia.bitmap = if (resId != null) {
                        BitmapFactory.decodeResource(resources, resId)
                    } else {
                        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                    }

                    ds.add(tygia)
                }
            } catch (ex: Exception) {
                Log.e("TyGiaTask", "Error: ${ex.message}", ex)
            } finally {
                connection?.disconnect()
                reader?.close()
            }
            return ds
        }

        override fun onPreExecute() {
            super.onPreExecute()
            myadapter.clear()
        }

        override fun onPostExecute(result: ArrayList<Tygia>?) {
            super.onPostExecute(result)
            if (!result.isNullOrEmpty()) {
                myadapter.clear()
                myadapter.addAll(result)
                myadapter.notifyDataSetChanged()
            } else {
                Log.d("TyGiaTask", "No data received")
            }
        }
    }
}


