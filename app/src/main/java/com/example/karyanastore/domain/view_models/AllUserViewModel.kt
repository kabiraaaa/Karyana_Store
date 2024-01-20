package com.example.karyanastore.domain.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karyanastore.data.repository.UsersRepository
import com.example.karyanastore.domain.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllUserViewModel(private val repository: UsersRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUser()
        }
    }

    val users: LiveData<List<Users>>
        get() = repository.users
}