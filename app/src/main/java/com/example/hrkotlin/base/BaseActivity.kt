package com.example.hrkotlin.base

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Process
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import butterknife.ButterKnife

import butterknife.Unbinder
import com.example.hrkotlin.base.log.Logger


abstract class BaseActivity<T : BasePresenter?> : AppCompatActivity(),
    BaseView<T?> {

    override var presenter: T? = null
    private var mUnbinder: Unbinder? = null
    var isBinding = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val currentPID = Process.myPid().toString() + ""

//            if (currentPID != savedInstanceState.getString(PID)) {
//
//                // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
//                val intent = Intent(viewContext, SplashActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
//                finish()
//            }
        }
        if (!isBinding) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
            setContentView(layoutId)
            mUnbinder = ButterKnife.bind(this)
        }
        presenter = onCreatePresenter()
        onPrepareLayout()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(PID, Process.myPid().toString() + "")
        outState.putSerializable("android:support:fragments", null)
    }

    override fun onPause() {
        super.onPause()
        isForeGround = false
    }

    override fun onResume() {
        super.onResume()
        //        NotificationManager.getCount(getViewContext());
        isForeGround = true
    }

    override fun onPrepareLayout() {}
    override fun showAlertDialog(message: String?) {}
    override fun showProgress() {
//        DialogUtils.showProgressDialog(this)
    }



    override fun hideProgress() {
//        DialogUtils.dismissProgressDialog(this)
    }

    override fun onRequestError(errorCode: String?, errorMessage: String?) {
        hideProgress()
    }

    override fun onRequestSuccess() {
        hideProgress()
    }

    override val viewContext: BaseActivity<T?> get() = (this as? BaseActivity<T?>)!!

    /**
     * Return layout resource id for activity
     */
    protected abstract val layoutId: Int

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    fun showKeyboard(textView: TextView) {
        textView.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    fun addFragment(
        containerId: Int,
        fragment: Fragment,
        args: Bundle?,
        addToBackStack: Boolean,
        tag: String?
    ) {
        if (args != null) {
            fragment.arguments = args
        }
        addFragment(containerId, fragment, addToBackStack, tag)
    }

    fun addFragment(containerId: Int, fragment: Fragment, addToBackStack: Boolean, tag: String?) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            //        transaction.setCustomAnimations(R.anim.screen_enter,
//                R.anim.screen_exit, R.anim.slide_none,
//                R.anim.screen_pop_exit);
            transaction.add(containerId, fragment, tag)
            if (addToBackStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            Logger.info(e)
        }
    }

    fun addFragmentNoTransaction(
        containerId: Int,
        fragment: BaseFragment<T,BaseActivity<T>>,
        addToBackStack: Boolean,
        tag: String?
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(containerId, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commitAllowingStateLoss()
    }

    fun addFragment(containerId: Int, fragment: BaseFragment<T,BaseActivity<T>>, addToBackStack: Boolean) {
        addFragment(containerId, fragment, addToBackStack, fragment.javaClass.simpleName)
    }

    fun addFragmentNoTransaction(
        containerId: Int,
        fragment: BaseFragment<T,BaseActivity<T>>,
        addToBackStack: Boolean
    ) {
        addFragmentNoTransaction(
            containerId,
            fragment,
            addToBackStack,
            fragment.javaClass.simpleName
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val manager = supportFragmentManager
        if (manager != null) {
            for (fragment in manager.fragments) {
                fragment?.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun setScreenMarginForGridView(view: View) {
        // This to set margin screen with RecyclerView.
//        val margin: Int = CompatibilityUtil.getScreenMargin(this)
//        view.setPadding(margin, 0, margin, margin)
    }

    fun setHideKeyboard(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideKeyboard()
                false
            }
        }

        // If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setHideKeyboard(innerView)
            }
        }
    }

    override fun onDestroy() {
        mUnbinder?.unbind()
        super.onDestroy()
    }

    fun initToolbar(toolbar: Toolbar?) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun showMessage(message: String?) {
//        DialogUtils.showError(viewContext, message)
    }

    /**
     * This method to get bundle size
     * @param bundle is Bundle in which to place your saved state.
     * @return a long value is bundle size
     */
    fun getBundleSize(bundle: Bundle?): Long {
        val dataSize: Long
        val obtain = Parcel.obtain()
        dataSize = try {
            obtain.writeBundle(bundle)
            obtain.dataSize().toLong()
        } finally {
            obtain.recycle()
        }
        return dataSize
    }

    companion object {
        var isForeGround = false
        const val PID = "PID"
    }
}