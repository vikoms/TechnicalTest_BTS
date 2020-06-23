package com.example.technicaltest_bts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.example.technicaltest_bts.api.ApiHelper
import com.example.technicaltest_bts.api.RetrofitClient
import com.example.technicaltest_bts.entity.Checklist
import kotlinx.android.synthetic.main.activity_form_checklist.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormChecklistActivity : AppCompatActivity() {

    private val token = LoginActivity.token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_form_checklist)

        btn_add.setOnClickListener {
            addData()
        }
    }

    private fun addData() {
        val title = edt_title.text.toString()
        val checkList = Checklist(title)
        val apiHelper = RetrofitClient.getClient().create(ApiHelper::class.java)
        val header = HashMap<String,String>()
        header["Authorization"] = "Bearer $token"
        header["Content-Type"] = "application/json"
        header["Accept"] = "application/json"
        val call = apiHelper.createCheckList(header,checkList)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                Log.d("FormChecklistActivity", "onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseObject = JSONObject(response.body()?.string())

                    Toast.makeText(this@FormChecklistActivity, "${responseObject.getString("message")}", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.d("ChecklistActivity", "onResponse: ${response.errorBody()?.string()} & ${response.code()}")
                }
            }

        })
    }
}