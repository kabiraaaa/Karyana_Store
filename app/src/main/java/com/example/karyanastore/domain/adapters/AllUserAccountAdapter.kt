package com.example.karyanastore.domain.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.karyanastore.databinding.RvAccountItemBinding
import com.example.karyanastore.domain.models.Users

class AllUserAccountAdapter(private var dataSet: List<Users>, private val onClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AllUserAccountAdapter.ViewHolder>() {

    class ViewHolder(binding: RvAccountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvUserName: AppCompatTextView = binding.tvUserName
        val tvUserNumber: AppCompatTextView = binding.tvUserNumber
        val tvUserBalance: AppCompatTextView = binding.tvUserBalance
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvAccountItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = dataSet[position]
        viewHolder.also {
            it.tvUserName.text = user.userName
            it.tvUserNumber.text = user.usernumber
            it.tvUserBalance.text = "₹ ${user.amount}"
            it.itemView.setOnClickListener {
                onClickListener.onItemClick(position)
            }
        }
    }

    fun getItem(position: Int): Users {
        return dataSet[position]
    }

    override fun getItemCount() = dataSet.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
