package com.example.technicaltest_bts.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest_bts.api.ApiHelper
import com.example.technicaltest_bts.api.RetrofitClient
import com.example.technicaltest_bts.entity.Checklist
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChecklistViewModel: ViewModel() {

    private val apiHelper = RetrofitClient.getClient().create(ApiHelper::class.java)
    private val checkLists = MutableLiveData<ArrayList<Checklist>>()



    fun setData(token: String) {
        val dataChecklist = ArrayList<Checklist>()

        val header = HashMap<String,String>()
        header["Authorization"] = "Bearer $token"
        header["Content-Type"] = "application/json"
        header["Accept"] = "application/json"
        val call = apiHelper.getChecklist(header)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ChecklistActivity", "onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    val responseObject = JSONObject(response?.body()?.string())
                    val list = responseObject.getJSONArray("data")
                    for (i in 0 until list.length()) {
                        val check = list.getJSONObject(i)
                        val checkItem = Checklist()
                        checkItem.name = check.getString("name")
                        dataChecklist.add(checkItem)
                    }
                    checkLists.postValue(dataChecklist)
                }else {
                    Log.d("ChecklistActivity", "onResponse: ${response.errorBody()?.string()} & ${response.code()}")
                }
            }

        })

    }

    fun getData(): LiveData<ArrayList<Checklist>> = checkLists

}