package com.example.app1.internet

import com.example.app1.screens.login.AdminLoginInfo
import com.example.app1.screens.order.OrderListItem
import com.example.app1.screens.search.MemberListItem
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val baseURL = "https://tumtla-blooddonor.000webhostapp.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .baseUrl(baseURL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface ApiService {

    @FormUrlEncoded
    @POST("search.php")
    fun getMemberAsync(
        @Field("blood_type") bloodType: String
    ): Deferred<List<MemberListItem>>


    @GET("req_list.php")
    fun getRequestListAsync():
            Deferred<List<OrderListItem>>

    @FormUrlEncoded
    @POST("register.php")
    fun postMemberAsync(
        @Field("name") name: String,
        @Field("ph_no") phNo: String,
        @Field("gender") gender: String,
        @Field("weight") weight: String,
        @Field("birth_date") birthDate: String,
        @Field("blood_type") bloodType: String,
        @Field("last_donate_date") lastDonateDate: String
    ): Deferred<ResponseBody>


    @FormUrlEncoded
    @POST("admin_login.php")
    fun adminLoginAsync(
        @Field("phone_number") phNo: String,
        @Field("password") password: String
    ): Deferred<AdminLoginInfo>

    @FormUrlEncoded
    @POST("req_blood.php")
    fun postRequestBloodAsync(
        @Field("name") name: String,
        @Field("ph_no") phNo: String,
        @Field("hospital_name") hospitalName: String,
        @Field("blood_type") bloodType: String

    ): Deferred<ResponseBody>
}

object API {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}