package com.example.karyanastore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.karyanastore.R
import com.example.karyanastore.data.repository.UsersRepository
import com.example.karyanastore.data.source.room.UsersDatabase
import com.example.karyanastore.databinding.FragmentHomeBinding
import com.example.karyanastore.domain.adapters.AllUserAccountAdapter
import com.example.karyanastore.domain.view_models.AllUserViewModel
import com.example.karyanastore.domain.view_models.factory.AllUserViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home), AllUserAccountAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var allUserAccountAdapter: AllUserAccountAdapter
    private lateinit var allUserViewModel: AllUserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        fabClick()
//        addAmount()
        itemDivider()
        setUpRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun fabClick() {
        binding.extendedFab.setOnClickListener {
            AddUserFragment().show(childFragmentManager, "AddUserBottomSheet")
        }
    }

    /*private fun addAmount() {
        binding.btnAddAmount.setOnClickListener{
            AddAmountFragment().show(childFragmentManager, "AddAmountBottomSheet")
        }
    }*/

    private fun setUpRecyclerView() {
        val database = UsersDatabase.getIUsersDatabase(requireActivity().applicationContext)
        val userRepository = UsersRepository(database)
        allUserViewModel =
            ViewModelProvider(this, AllUserViewModelFactory(userRepository))[AllUserViewModel::class.java]
    }

    private fun itemDivider() {
        binding.rvAllAccounts.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    override fun onResume() {
        allUserViewModel.users.observeForever {
            if (it.isNotEmpty()){
                allUserAccountAdapter = AllUserAccountAdapter(it,this)
//                allUserAccountAdapter.notifyDataSetChanged()
                binding.rvAllAccounts.adapter = allUserAccountAdapter
            }
        }
        /*allUserViewModel.users.observe(requireActivity()) {
            if (it.isNotEmpty()){
                allUserAccountAdapter = AllUserAccountAdapter(it,this)
                allUserAccountAdapter.notifyDataSetChanged()
                binding.rvAllAccounts.adapter = allUserAccountAdapter
            }
        }*/
        super.onResume()
    }

    override fun onItemClick(position: Int) {
//        val userName = allUserAccountAdapter.getItem(position).userName
        val userNumber = allUserAccountAdapter.getItem(position).usernumber
        AddAmountFragment(userNumber).show(childFragmentManager, "AddAmountBottomSheet")
    }
}