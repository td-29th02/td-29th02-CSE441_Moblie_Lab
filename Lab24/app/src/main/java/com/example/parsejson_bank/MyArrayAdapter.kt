package com.example.parsejson_bank

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyArrayAdapter(
    private val contextActivity: Activity,
    private val resourceLayout: Int,
    private val objectsList: List<Tygia>
) : ArrayAdapter<Tygia>(contextActivity, resourceLayout, objectsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = contextActivity.layoutInflater
        val itemView = inflater.inflate(resourceLayout, null)

        val tygia = objectsList[position]

        val imgHinh: ImageView = itemView.findViewById(R.id.imgHinh)
        val txtType: TextView = itemView.findViewById(R.id.txtType)
        val txtMuaTM: TextView = itemView.findViewById(R.id.txtMuaTM)
        val txtBanTM: TextView = itemView.findViewById(R.id.txtBanTM)
        val txtMuaCK: TextView = itemView.findViewById(R.id.txtMuaCK)
        val txtBanCK: TextView = itemView.findViewById(R.id.txtBanCK)

        imgHinh.setImageBitmap(tygia.bitmap)
        txtType.text = tygia.type
        txtMuaTM.text = tygia.muatienmat
        txtBanTM.text = tygia.bantuenmat
        txtMuaCK.text = tygia.muack
        txtBanCK.text = tygia.banck

        return itemView
    }
}
