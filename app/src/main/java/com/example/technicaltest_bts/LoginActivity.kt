package com.example.technicaltest_bts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.technicaltest_bts.api.ApiHelper
import com.example.technicaltest_bts.api.RetrofitClient
import com.example.technicaltest_bts.entity.Login
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var apiHelper: ApiHelper

    companion object {
        lateinit var token: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        apiHelper = RetrofitClient.getClient().create(ApiHelper::class.java)

        btn_login.setOnClickListener {
            login()
        }

    }

    private fun login() {
        val username = edt_username.text.toString()
        val password = edt_password.text.toString()
        val login =Login(password,username)
        val header = HashMap<String,String>()
        header["Content-Type"] = "application/json"
        header["Accept"] = "application/json"

        Log.d("LoginActivity", "onFailure: $username & $password")

        val call = apiHelper.login(header,login)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("LoginActivity", "onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseObject = JSONObject(response.body()?.string())
                    val responseData = responseObject.getJSONObject("data")
                    token = responseData.getString("token")
                    val moveToChecklistList = Intent(this@LoginActivity, ChecklistActivity::class.java)
                    startActivity(moveToChecklistList)
                    finish()

                } else {
                    Log.d("LoginActivity", "onResponse: ${response.code()}")
                }
            }

        })
    }
}