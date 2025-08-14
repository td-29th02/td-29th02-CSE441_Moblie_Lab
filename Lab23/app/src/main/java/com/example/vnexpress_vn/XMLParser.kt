package com.example.vnexpress_vn

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.ByteArrayInputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class XMLParser {

    fun getXmlFromUrl(urlStr: String): String? {
        return try {
            val url = URL(urlStr)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getDomElement(xml: String?): Document? {
        return try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val inputStream = ByteArrayInputStream(xml?.toByteArray(Charsets.UTF_8))
            builder.parse(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getValue(item: Element, str: String): String {
        val n = item.getElementsByTagName(str)
        return this.getElementValue(n.item(0))
    }

    private fun getElementValue(elem: Node?): String {
        var child: Node? = null
        if (elem != null) {
            if (elem.hasChildNodes()) {
                child = elem.firstChild
                while (child != null) {
                    if (child.nodeType == Node.TEXT_NODE) {
                        return child.nodeValue
                    }
                    child = child.nextSibling
                }
            }
        }
        return ""
    }
}
