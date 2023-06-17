package com.example.hrkotlin.base.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<B : ViewDataBinding, T> : RecyclerView.Adapter<BaseViewHolder<B>>() {
    protected var context: Context? = null
    protected var onItemRecyclerViewClick: OnItemRecyclerViewClick? = null
    private val listObjects = ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        if (context == null) {
            context = parent.context
        }
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: B = DataBindingUtil.inflate(layoutInflater, getLayoutIdForViewType(viewType), parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        holder.getBinding().root.isClickable = true
        holder.getBinding().root.setOnClickListener { view ->
            onItemRecyclerViewClick?.onItemRecyclerViewClick(position, view)
        }
    }

    protected abstract fun getLayoutIdForViewType(viewType: Int): Int


    interface OnItemRecyclerViewClick {
        fun onItemRecyclerViewClick(position: Int, view: View)
    }

    override fun getItemCount(): Int {
        return listObjects.size
    }

    fun getItem(position: Int): T? {
        return if (position >= 0 && position < itemCount) {
            listObjects[position]
        } else null
    }

    fun addItem(objects: List<T>?) {
        listObjects.clear()
        objects?.let { listObjects.addAll(it) }
        notifyDataSetChanged()
    }
}