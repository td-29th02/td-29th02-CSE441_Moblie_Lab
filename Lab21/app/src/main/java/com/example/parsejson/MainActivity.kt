package com.example.parsejson

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    private lateinit var btnParse: Button
    private lateinit var lv: ListView
    private val myList = ArrayList<String>()
    private lateinit var myAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnParse = findViewById(R.id.btnparse)
        lv = findViewById(R.id.lv)

        myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myList)
        lv.adapter = myAdapter

        btnParse.setOnClickListener {
            parseJson()
        }
    }

    private fun parseJson() {
        try {
            val json = assets.open("computer.json").use { inputStream ->
                val buffer = ByteArray(inputStream.available())
                inputStream.read(buffer)
                String(buffer, Charsets.UTF_8)
            }

            val reader = JSONObject(json)
            myList.clear()
            myList.add(reader.getString("MaDM"))
            myList.add(reader.getString("TenDM"))

            val myArray = reader.getJSONArray("Sanphams")
            for (i in 0 until myArray.length()) {
                val myObj = myArray.getJSONObject(i)
                myList.add("${myObj.getString("MaSP")} - ${myObj.getString("TenSP")}")
                myList.add("${myObj.getString("SoLuong")} * ${myObj.getString("DonGia")} = ${myObj.getString("ThanhTien")}")
                myList.add(myObj.getString("Hinh"))
            }

            myAdapter.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
