package com.dicoding.callysta.model

import com.google.gson.annotations.SerializedName

data class ImageCheckRequest(

    @field:SerializedName("actual-answer")
    val actualAnswer: String,

    val image: String

)