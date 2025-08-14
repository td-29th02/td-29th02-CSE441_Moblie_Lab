package com.example.arsyntask

import android.app.Activity
import android.os.AsyncTask
import android.os.SystemClock
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.arsyntask.R

class MyAsyncTask(private val context: Activity) : AsyncTask<Void, Int, Void>() {

    override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context, "onPreExecute", Toast.LENGTH_SHORT).show()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        for (i in 0..100) {
            SystemClock.sleep(100)
            publishProgress(i)
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        val bar = context.findViewById<ProgressBar>(R.id.progressBar2)
        val progress = values[0] ?: 0
        bar.progress = progress

        val msg = context.findViewById<TextView>(R.id.textview)
        msg.text = "$progress%"
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show()
    }
}
