package com.example.app1.internet

import com.example.app1.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
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
    ): Deferred<MemberInfo>


    @GET("req_list.php")
    fun getrequestListAsync():
            Deferred<RequestInfo>

    @FormUrlEncoded
    @POST("register.php")
    fun signUpMemberAsync(
        @Field("name") name: String,
        @Field("ph_no") phNo: String,
        @Field("gender") gender: String,
        @Field("weight") weight: String,
        @Field("birth_date") birthDate: String,
        @Field("blood_type") bloodType: String,
        @Field("last_donate_date") lastDonateDate: String
    ): Deferred<PostInfo>


    @FormUrlEncoded
    @POST("admin_login.php")
    fun adminLoginAsync(
        @Field("phone_number") phNo: String,
        @Field("password") password: String
    ): Deferred<PostInfo>

    @FormUrlEncoded
    @POST("req_blood.php")
    fun requestBloodAsync(
        @Field("name") name: String,
        @Field("ph_no") phNo: String,
        @Field("hospital_name") hospitalName: String,
        @Field("blood_type") bloodType: String,
        @Field("time") time: String

    ): Deferred<PostInfo>

    @FormUrlEncoded
    @POST("delete_member.php")
    fun deleteMemberAsync(
        @Field("ph_no") phNo: String
    ): Deferred<PostInfo>

    @FormUrlEncoded
    @POST("deleteRequest.php")
    fun deleteRequestAsync(
        @Field("ph_no") phNo: String
    ): Deferred<PostInfo>
}

object API {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}