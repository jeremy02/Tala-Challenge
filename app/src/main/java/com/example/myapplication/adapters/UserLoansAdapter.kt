package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.remote.responses.UserLoan
import com.example.myapplication.databinding.UserLoanItemLayoutBinding
import com.example.myapplication.model.Loan

class UserLoansAdapter(val onUserLoanSelected: (userLoan: UserLoan, position: Int) -> Unit) : RecyclerView.Adapter<UserLoansAdapter.UserLoansViewHolder>() {

    private val userLoanItems: ArrayList<UserLoan> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserLoansViewHolder {
        var binding = UserLoanItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserLoansViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserLoansViewHolder, position: Int) {
        holder.bind(userLoanItems[position], position, holder.itemView.context)
    }

    override fun getItemCount() = userLoanItems.size

    fun updateItems(userLoansList: List<UserLoan>) {
        userLoanItems.clear()
        userLoanItems.addAll(userLoansList)
        notifyDataSetChanged()
    }

    inner class UserLoansViewHolder(private val itemBinding: UserLoanItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(userLoan: UserLoan, position: Int, context: Context) {
            itemBinding.apply {
                userLoanItem = userLoan

                itemPosition = position

                // Setting Visibility of Layouts for
                loanApplyLayoutVisible = getLoanApplyVisibility(userLoan.loan)
                loanApprovedLayoutVisible = getLoanApprovedVisibility(userLoan.loan)
                loanPaidLayoutVisible = getLoanPaidVisibility(userLoan.loan)
                loanDueLayoutVisible = getLoanDueVisibility(userLoan.loan)
            }
        }
    }

    fun getLoanApplyVisibility(userLoan: Loan?): Boolean {
        return userLoan == null && userLoan?.status.isNullOrEmpty()
    }

    fun getLoanApprovedVisibility(userLoan: Loan?): Boolean {
        if (userLoan != null && !userLoan.status.isNullOrEmpty()) {
            if(userLoan.status == "approved")
                return true
        }
        return false
    }

    fun getLoanPaidVisibility(userLoan: Loan?): Boolean {
        if (userLoan != null && !userLoan.status.isNullOrEmpty()) {
            if(userLoan.status == "paid")
                return true
        }
        return false
    }

    fun getLoanDueVisibility(userLoan: Loan?): Boolean {
        if (userLoan != null && !userLoan.status.isNullOrEmpty()) {
            if(userLoan.status == "due")
                return true
        }
        return false
    }
}
