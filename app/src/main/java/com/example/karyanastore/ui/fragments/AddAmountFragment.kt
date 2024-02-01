package com.example.karyanastore.ui.fragments

import android.content.Intent
import android.net.Uri
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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
                viewModel.updateAmountToDb(amount, userNumber, "sub")
                val message = "Your account is subtracted with ₹$amount"
                shareOnWhatsapp(userNumber, message)
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
                viewModel.updateAmountToDb(amount, userNumber, "add")
                val message = "Your account is added with ₹$amount"
                shareOnWhatsapp(userNumber, message)
            }
        }

        binding.btnUpi.setOnClickListener {

        }
    }

    private fun shareOnWhatsapp(userNumber: String, message: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.share_on_wa))
                .setMessage(resources.getString(R.string.supporting_text))
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.share)) { dialog, which ->
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                java.lang.String.format(
                                    "https://api.whatsapp.com/send?phone=%s&text=%s",
                                    "+91$userNumber",
                                    message
                                )
                            )
                        )
                    )
                }
                .show()
        }
    }
}