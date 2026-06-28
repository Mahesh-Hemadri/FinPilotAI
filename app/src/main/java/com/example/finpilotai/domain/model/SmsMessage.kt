package com.example.finpilotai.domain.model

data class SmsMessage(

    val id: Long,

    val address: String,

    val body: String,

    val date: Long

)