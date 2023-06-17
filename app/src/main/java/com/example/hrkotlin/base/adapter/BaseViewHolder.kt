package com.example.hrkotlin.base.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


class BaseViewHolder<B : ViewDataBinding>: RecyclerView.ViewHolder {
    private val mBinding: B

    constructor(binding: B) : super(binding.root) {
        mBinding = binding
    }

    constructor(view: View) : super(view) {
        mBinding = DataBindingUtil.bind(view)!!
    }

    fun getBinding(): B {
        return mBinding
    }
}