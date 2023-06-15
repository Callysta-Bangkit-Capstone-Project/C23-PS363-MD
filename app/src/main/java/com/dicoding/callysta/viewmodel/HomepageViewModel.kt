package com.dicoding.callysta.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.callysta.core.repository.Repository

class HomepageViewModel(private val repository: Repository) : ViewModel() {
    fun getQuestionList() = repository.getQuestionList()

}