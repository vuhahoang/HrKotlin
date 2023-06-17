package com.example.hrkotlin.base

interface BaseView<P : BasePresenter?> {
    fun showProgress()
    fun hideProgress()
    fun onPrepareLayout()
    fun showAlertDialog(message: String?)
    val presenter: P
    fun onCreatePresenter(): P
    fun onRequestError(errorCode: String?, errorMessage: String?)
    fun onRequestSuccess()
    val viewContext: BaseActivity<P?>
    fun showMessage(message: String?)
}