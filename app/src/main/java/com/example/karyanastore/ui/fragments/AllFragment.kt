package com.example.karyanastore.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.karyanastore.R
import com.example.karyanastore.data.repository.UsersRepository
import com.example.karyanastore.data.source.room.UsersDatabase
import com.example.karyanastore.databinding.FragmentAllBinding
import com.example.karyanastore.domain.adapters.AllUserAccountAdapter
import com.example.karyanastore.domain.view_models.AllUserViewModel
import com.example.karyanastore.domain.view_models.factory.AllUserViewModelFactory


class AllFragment : Fragment(R.layout.fragment_all), AllUserAccountAdapter.OnItemClickListener  {
    private lateinit var binding: FragmentAllBinding
    private lateinit var allUserAccountAdapter: AllUserAccountAdapter
    private lateinit var allUserViewModel: AllUserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAllBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        itemDivider()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val database = UsersDatabase.getIUsersDatabase(requireActivity().applicationContext)
        val userRepository = UsersRepository(database)
        allUserViewModel =
            ViewModelProvider(this, AllUserViewModelFactory(userRepository))[AllUserViewModel::class.java]

        allUserViewModel.users.observe(requireActivity()) {
            if (it.isNotEmpty()){
                allUserAccountAdapter = AllUserAccountAdapter(it, this)
                allUserAccountAdapter.notifyDataSetChanged()
                binding.rvAllAccounts.adapter = allUserAccountAdapter
            }
        }
    }

    private fun itemDivider() {
        binding.rvAllAccounts.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    override fun onItemClick(position: Int) {
        val userName = allUserAccountAdapter.getItem(position).userName
        Toast.makeText(activity, "Clicked on user: $userName", Toast.LENGTH_SHORT).show()
    }
}