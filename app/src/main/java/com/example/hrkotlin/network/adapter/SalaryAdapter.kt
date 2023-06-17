package com.example.hrkotlin.network.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hrkotlin.MainActivity
import com.example.hrkotlin.R

class SalaryAdapter(val salaryList: List<String>) : RecyclerView.Adapter<SalaryAdapter.ViewHolder>() {

        private var salaryListenner : SalaryListenner? = null

        fun setListenner(salaryListenner: SalaryListenner ) {
            this.salaryListenner = salaryListenner
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_salary,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return salaryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMonth?.setText(salaryList[position])
        holder.tvMonth?.setOnClickListener() {
            salaryListenner?.onSalarySelected()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMonth: TextView? = itemView.findViewById(R.id.month_item_salary)
    }

}

interface SalaryListenner {
    fun onSalarySelected()
}
