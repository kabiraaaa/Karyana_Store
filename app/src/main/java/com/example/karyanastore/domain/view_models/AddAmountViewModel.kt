package com.example.karyanastore.domain.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karyanastore.data.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAmountViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _amountAddedEvent = MutableLiveData<Boolean>()
    val amountUpdatedEvent: LiveData<Boolean>
        get() = _amountAddedEvent

    fun updateAmountToDb(amount: Int, number: String, operation:String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAmountAndModifiedAt(number, amount, operation)
            _amountAddedEvent.postValue(true)
        }
    }
}