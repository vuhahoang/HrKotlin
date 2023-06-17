package com.example.hrkotlin.base

interface BasePresenter {
    val view: BaseView<BasePresenter>
    val viewContext: BaseActivity<BasePresenter>
}