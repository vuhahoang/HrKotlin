package com.example.hrkotlin.ui.Login.Fragment

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hrkotlin.R
import com.example.hrkotlin.base.BaseFragment
import com.example.hrkotlin.base.log.Logger
import com.example.hrkotlin.common.pref.SharePrefManager
import com.example.hrkotlin.network.ServiceBuilder
import com.example.hrkotlin.network.dto.AuthRequestBody
import com.example.hrkotlin.network.dto.AuthenData
import com.example.hrkotlin.network.dto.ResponseDTO
import com.example.hrkotlin.ui.Login.LoginActivity
import com.example.hrkotlin.ui.Login.LoginActivityPresenter
import com.example.hrkotlin.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn : BaseFragment<LoginActivityPresenter, LoginActivity>() {
    var edUser: EditText? = null
    var edPass: EditText? = null
    var btnSignIn: TextView? = null
    var hideShowPass: ImageView? = null

    override val layoutId: Int
        get() = R.layout.sign_in_layout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sign_in_layout,container,false)
        edUser = view.findViewById(R.id.edAccount)
        edPass = view.findViewById(R.id.edPass)
        hideShowPass = view.findViewById(R.id.show_hide_pass)
        btnSignIn = view.findViewById(R.id.login_btn)

        hideShowPass?.setOnClickListener() {
            if (edPass?.inputType == 129) {
                if (edPass?.transformationMethod is PasswordTransformationMethod) {
                    edPass?.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    edPass?.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
        }


        btnSignIn?.setOnClickListener() {
            if (TextUtils.isEmpty(edPass?.text.toString()) || TextUtils.isEmpty(edUser?.text.toString())) {
                Toast.makeText(viewContext,"Không được để trống thông tin",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isEmailValid(edUser?.text.toString())) {
                Toast.makeText(viewContext,"Tài khoản phải là email á",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPassValid(edPass?.text.toString())) {
                Toast.makeText(viewContext,"Mật khẩu phải lớn từ 6 ký tự",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            ServiceBuilder.getService()?.login(AuthRequestBody().apply {
                email = edUser?.text.toString()
                password = edPass?.text.toString()
            })?.enqueue(object :
                Callback<ResponseDTO<AuthenData?>?> {
                override fun onResponse(
                    call: Call<ResponseDTO<AuthenData?>?>,
                    response: Response<ResponseDTO<AuthenData?>?>
                ) {
                    if (response.isSuccessful) {
                        if (TextUtils.isEmpty(response.body()?.message)) {
                            if (!TextUtils.isEmpty(response.body()?.result?.accessToken)) {
                                SharePrefManager.saveAccessToken(viewContext,response.body()?.result?.accessToken)
                                SharePrefManager.saveIsLogin(viewContext,true)
                                val intent = Intent(viewContext, HomeActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(viewContext,response.body()?.message,Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Logger.e("havu","đăng nhập sai rồi cu")
                    }
                }

                override fun onFailure(call: Call<ResponseDTO<AuthenData?>?>, t: Throwable) {
                    Logger.e("havu","onFailure")
                }
            })


        }
        return view
    }

    override fun onPrepareLayout() {
    }

    override fun onCreatePresenter(): LoginActivityPresenter? {
        return null
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
        return email.matches(emailRegex)
    }

    fun isPassValid(pass: String): Boolean {
        return pass.length >= 6;
    }
}