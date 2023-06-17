package com.example.hrkotlin.ui.home

import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import com.example.hrkotlin.MainActivity
import com.example.hrkotlin.R
import com.example.hrkotlin.base.BaseActivity
import com.example.hrkotlin.base.log.Logger
import com.example.hrkotlin.common.pref.SharePrefManager
import com.example.hrkotlin.network.ServiceBuilder
import com.example.hrkotlin.network.dto.AuthRequestBody
import com.example.hrkotlin.network.dto.AuthenData
import com.example.hrkotlin.network.dto.ResponseDTO
import com.example.hrkotlin.network.dto.User
import com.example.hrkotlin.ui.account.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : BaseActivity<HomeActivityPresenter>() {
  override val layoutId: Int
    get() = R.layout.activity_home

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    var btn: Button = findViewById(R.id.btnTest)
    val test = AuthRequestBody().apply {
      email = "superadmin@superadmin.com"
      password = "123123"
    }


    val fragmentManager = supportFragmentManager
    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
    val homeFragment: HomeFragment = HomeFragment()

    try {
      addFragment(R.id.testlayout,homeFragment,true,"test")
    } catch (e : Exception) {
      Logger.info(e)
    }

//    btn.setOnClickListener() {
//      ServiceBuilder.getService()?.getMe()?.enqueue(object : Callback<ResponseDTO<User>> {
//        override fun onResponse(
//          call: Call<ResponseDTO<User>>,
//          response: Response<ResponseDTO<User>>
//        ) {
//          Logger.e("havu",SharePrefManager.getIsLogin(viewContext).toString())
//          Logger.e("havu",SharePrefManager.getIsLogin(MainActivity.getInstance()).toString())
//          Logger.e("havu",response?.body()?.result?.fullName + " ")
//        }
//
//        override fun onFailure(call: Call<ResponseDTO<User>>, t: Throwable) {
//          Logger.e("havu","lỗi rồi nhé")
//        }
//      })
//
//    }
    ServiceBuilder.getService()?.login(test)?.enqueue(object : Callback<ResponseDTO<AuthenData?>?>  {
      override fun onResponse(
        call: Call<ResponseDTO<AuthenData?>?>,
        response: Response<ResponseDTO<AuthenData?>?>
      ) {
        if (response.isSuccessful) {
          SharePrefManager.saveAccessToken(viewContext,response.body()?.result?.accessToken + " ")
          SharePrefManager.saveIsLogin(viewContext,true)
        }
      }

      override fun onFailure(call: Call<ResponseDTO<AuthenData?>?>, t: Throwable) {
        Logger.e("havu","onFailure")
      }
    })
  }

  override fun onCreatePresenter(): HomeActivityPresenter? {
    return null
  }
}


