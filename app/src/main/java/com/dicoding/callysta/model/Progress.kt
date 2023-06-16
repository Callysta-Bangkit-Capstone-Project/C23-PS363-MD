package com.dicoding.callysta.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Progress(
    val levelRead: Int,
    val subLevelRead: Int,
    val levelWrite: Int,
    val subLevelWrite: Int,
) : Parcelable
