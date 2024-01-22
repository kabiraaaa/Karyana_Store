package com.example.karyanastore.domain.view_models.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.karyanastore.data.repository.UsersRepository
import com.example.karyanastore.domain.view_models.AddAmountViewModel

@Suppress("UNCHECKED_CAST")
class AddAmountViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddAmountViewModel(repository) as T
    }
}