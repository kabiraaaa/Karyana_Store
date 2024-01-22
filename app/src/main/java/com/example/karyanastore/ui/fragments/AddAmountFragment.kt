package com.example.karyanastore.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.karyanastore.R
import com.example.karyanastore.data.repository.UsersRepository
import com.example.karyanastore.data.source.room.UsersDatabase
import com.example.karyanastore.databinding.FragmentAddAmountBinding
import com.example.karyanastore.domain.view_models.AddAmountViewModel
import com.example.karyanastore.domain.view_models.factory.AddAmountViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddAmountFragment(private val userNumber: String) :
    BottomSheetDialogFragment(R.layout.fragment_add_amount) {

    private lateinit var binding: FragmentAddAmountBinding
    private lateinit var viewModel: AddAmountViewModel
    private lateinit var userRepository: UsersRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddAmountBinding.bind(view)
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
            AddAmountViewModelFactory(userRepository)
        )[AddAmountViewModel::class.java]

        viewModel.amountUpdatedEvent.observe(viewLifecycleOwner) { amountUpdated ->
            if (amountUpdated) {
                dialog?.dismiss()
            }
        }
    }

    private fun initClicks() {
        binding.btnSubtract.setOnClickListener {
            val inputAmount = binding.tfUserAmount.editText?.text.toString()
            if (inputAmount.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Amount cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val amount = Integer.parseInt(inputAmount)
                viewModel.updateAmountToDb(amount, userNumber)
            }
        }

        binding.btnAdd.setOnClickListener {
            val inputAmount = binding.tfUserAmount.editText?.text.toString()
            if (inputAmount.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Amount cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val amount = Integer.parseInt(inputAmount)
                viewModel.updateAmountToDb(amount, userNumber)
            }
        }

        binding.btnUpi.setOnClickListener {

        }
    }
}