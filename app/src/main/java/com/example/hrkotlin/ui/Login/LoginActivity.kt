package com.example.hrkotlin.ui.Login

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.hrkotlin.R
import com.example.hrkotlin.base.BaseActivity
import com.example.hrkotlin.ui.Login.Fragment.SignIn
import com.example.hrkotlin.ui.account.HomeFragment

class LoginActivity : BaseActivity<LoginActivityPresenter>() {
    override val layoutId: Int
        get() = R.layout.login_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val signInFragment: SignIn = SignIn()
        addFragment(R.id.frameLayoutLogin,signInFragment,true,"test")
    }

    override fun onCreatePresenter(): LoginActivityPresenter? {
        return null
    }
}