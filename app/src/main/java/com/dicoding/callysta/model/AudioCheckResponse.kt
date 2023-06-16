package com.dicoding.callysta.model

import com.google.gson.annotations.SerializedName

data class AudioCheckResponse(

	@field:SerializedName("transcriptions")
	val transcriptions: List<String>
)
