package com.example.hrkotlin.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment<P : BasePresenter?, A : BaseActivity<P>> : Fragment(), BaseView<P?> {
    protected var mRootView: View? = null
    protected var mIsInitialized = false
    override var presenter: P? = null
    private var mUnbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) presenter = onCreatePresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!mIsInitialized) {
            mRootView = inflater.inflate(layoutId, container, false)
            mUnbinder = mRootView?.let { ButterKnife.bind(this,it) }
            onPrepareLayout()
            mIsInitialized = true
        }
        mRootView!!.setOnClickListener { }
        return mRootView
    }


    override fun onResume() {
        super.onResume()
        //        NotificationManager.getCount(getViewContext());
    }

    override fun showProgress() {
        baseActivity?.showProgress()
    }

    override fun hideProgress() {
        baseActivity?.hideProgress()
    }

    /**
     * Return layout resource id for activity
     */
    protected abstract val layoutId: Int

    private val rootView: View?
        get() = mRootView




    protected val baseActivity: A?
        get() = activity as A?

    override val viewContext: BaseActivity<P?> get() = (baseActivity as? BaseActivity<P?>)!!

    override fun onRequestError(errorCode: String?, errorMessage: String?) {
        if (!baseActivity?.isFinishing()!!) {
            baseActivity?.onRequestError(errorCode, errorMessage)
        }
    }

    override fun onRequestSuccess() {
        baseActivity?.onRequestSuccess()
    }

    override fun showAlertDialog(message: String?) {
        baseActivity?.showAlertDialog(message)
    }

    protected fun setScreenMarginForGridView() {
//        baseActivity?.setScreenMarginForGridView(mRootView)
    }

    override fun onDestroy() {
        if (mUnbinder != null) {
            mUnbinder!!.unbind()
        }
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun showMessage(message: String?) {
//        DialogUtils.showError(baseActivity, message)
    }

    fun onRemoveHistory() {}
    override fun onConfigurationChanged(newConfig: Configuration) {
//        LocaleUtils.initialize(viewContext, PrefManager.getLanguageSelected(viewContext))
        super.onConfigurationChanged(newConfig)
    }

    fun onClickRemoveHistory(tag: String?) {
//        val removeHistoryDialog = RemoveHistoryDialog()
//        removeHistoryDialog.setListener { onRemoveHistory() }
//        removeHistoryDialog.showPopup(childFragmentManager, tag)
    }
}

