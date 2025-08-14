package com.example.parsexml

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class XMLParser {
    fun getXmlFromUrl(urlString: String): String? {
        var result: String? = null
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            reader.close()
            result = sb.toString()

            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}
