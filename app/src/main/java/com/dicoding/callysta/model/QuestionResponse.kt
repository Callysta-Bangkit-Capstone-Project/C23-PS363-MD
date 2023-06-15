package com.dicoding.callysta.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class QuestionResponse(

	@field:SerializedName("read")
	val read: List<ReadItem>,

	@field:SerializedName("write")
	val write: List<WriteItem>
)

data class WriteItem(

	@field:SerializedName("level")
	val level: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("sublevel")
	val sublevel: List<SublevelItem>
)

@Parcelize
data class SublevelItem(

	@field:SerializedName("answer")
	val answer: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("gifLink")
	val gifLink: String
) : Parcelable

data class ReadItem(

	@field:SerializedName("level")
	val level: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("sublevel")
	val sublevel: List<Any>
)
