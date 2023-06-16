package com.dicoding.callysta.utils

import android.content.Context
import android.widget.Toast

object InterfaceUtil {

    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}