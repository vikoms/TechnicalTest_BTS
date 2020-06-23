package com.example.technicaltest_bts.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest_bts.LoginActivity
import com.example.technicaltest_bts.R
import com.example.technicaltest_bts.api.ApiHelper
import com.example.technicaltest_bts.api.RetrofitClient
import com.example.technicaltest_bts.entity.Checklist
import kotlinx.android.synthetic.main.item_checklist.view.*

class CheckListAdapter: RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder>() {

    private val checkList = ArrayList<Checklist>()
    fun setData(checkList: ArrayList<Checklist>) {
        this.checkList.clear()
        this.checkList.addAll(checkList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checklist,parent,false)
        return CheckListViewHolder(view)
    }

    override fun getItemCount(): Int  = checkList.size

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        holder.bind(checkList[position])
    }

    class CheckListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Checklist) {
            with(itemView) {
                tv_name_checklist.text = data.name
                setOnClickListener{
                    val dialog = AlertDialog.Builder(itemView.context)
                    dialog.setTitle("Hapus checklist")
                    dialog.setMessage("Anda yakin akan menghapus checklist ini ? ")

                    dialog.setPositiveButton("Yes", DialogInterface.OnClickListener {dialog,id ->

                        val apiHelper = RetrofitClient.getClient().create(ApiHelper::class.java)
                        val header = HashMap<String,String>()
                        header["Authorization"] = "Bearer ${LoginActivity.token}"
                        header["Content-Type"] = "application/json"
                        header["Accept"] = "application/json"

                    })
                }
            }

        }
    }
}