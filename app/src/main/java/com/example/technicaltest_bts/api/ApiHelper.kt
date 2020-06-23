package com.example.technicaltest_bts.api

import com.example.technicaltest_bts.entity.Checklist
import com.example.technicaltest_bts.entity.Login
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiHelper {

    @POST("login")
    fun login(
        @HeaderMap headers: Map<String, String>,
        @Body login: Login
    ): Call<ResponseBody>

    @GET("checklist")
    fun getChecklist(@HeaderMap headers: Map<String,String>): Call<ResponseBody>


    @POST("checklist")
    fun createCheckList(
        @HeaderMap headers: Map<String, String>,
        @Body checkList: Checklist
    ): Call<ResponseBody>

    @DELETE("checkList/{id}")
    fun deleteTutorial(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: String
    ): Call<ResponseBody>
}