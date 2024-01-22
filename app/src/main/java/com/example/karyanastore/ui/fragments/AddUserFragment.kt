package com.example.karyanastore.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.karyanastore.R
import com.example.karyanastore.data.repository.UsersRepository
import com.example.karyanastore.data.source.room.UsersDatabase
import com.example.karyanastore.databinding.FragmentAddUserBinding
import com.example.karyanastore.domain.view_models.AddUserViewModel
import com.example.karyanastore.domain.view_models.factory.AddUserViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddUserFragment : BottomSheetDialogFragment(R.layout.fragment_add_user) {

    private lateinit var binding: FragmentAddUserBinding
    private lateinit var viewModel: AddUserViewModel
    private lateinit var userRepository: UsersRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddUserBinding.bind(view)
        initRepoAndDB()
        initViewModel()
        initClicks()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRepoAndDB() {
        val database = UsersDatabase.getIUsersDatabase(requireActivity().applicationContext)
        userRepository = UsersRepository(database)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            AddUserViewModelFactory(userRepository)
        )[AddUserViewModel::class.java]

        viewModel.userAddedEvent.observe(viewLifecycleOwner) { userAdded ->
            if (userAdded) {
                dialog?.dismiss()
            }
        }
    }

    private fun initClicks() {
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        binding.btnAdd.setOnClickListener {
            val inputName = binding.tfUserName.editText?.text.toString()
            val inputNumber = binding.tfUserNumber.editText?.text.toString()
            if (inputName.isBlank() || inputNumber.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Name and Number cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.addUserToDb(inputName, inputNumber)
            }
        }
    }
}