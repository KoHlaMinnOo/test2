package com.example.app1

data class PostInfo(
    val message: String,
    val error: Boolean
)
data class MemberInfo(
    val error: Boolean,
    val message: String,
    val data:List<MemberData>
)
data class MemberData(
    var name: String,
    var ph_no: String,
    var gender: String,
    var weight: String,
    var birth_date: String,
    var blood_type: String,
    var last_donate_date: String
)
data class RequestInfo(
    val error: Boolean,
    val message: String,
    val data:List<OrderData>
)
data class OrderData(
    val name: String,
    val ph_no: String,
    val hospital_name: String,
    val blood_type: String
)