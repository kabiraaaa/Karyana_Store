package com.example.karyanastore.domain.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karyanastore.data.repository.UsersRepository
import com.example.karyanastore.domain.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUserViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _userAddedEvent = MutableLiveData<Boolean>()
    val userAddedEvent: LiveData<Boolean>
        get() = _userAddedEvent

    fun addUserToDb(name: String, number: String) {
        // Perform any validation if needed before adding to the database
        val user = Users(userName = name, usernumber = number)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
            _userAddedEvent.postValue(true)
        }
    }
}