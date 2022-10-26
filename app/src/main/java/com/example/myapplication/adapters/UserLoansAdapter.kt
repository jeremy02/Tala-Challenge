package com.example.myapplication.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.remote.responses.UserLoan
import com.example.myapplication.databinding.UserLoanItemLayoutBinding
import com.example.myapplication.model.Loan

class UserLoansAdapter(private val onUserLoanSelected: OnUserLoanSelected) : RecyclerView.Adapter<UserLoansAdapter.UserLoansViewHolder>() {

    class OnUserLoanSelected(val onUserLoanSelectedListener: (userLoan: UserLoan, position: Int, view: View) -> Unit) {
        fun onUserLoanSelected(userLoan: UserLoan, position: Int, view: View) = onUserLoanSelectedListener(userLoan, position, view)
    }

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

                // Click listeners
                inviteFriendsLayout.setOnClickListener {
                    onUserLoanSelected.onUserLoanSelected(userLoan, position, inviteFriendsLayout)
                }

                loanApplyLayout.applyLoanButton.setOnClickListener {
                    onUserLoanSelected.onUserLoanSelected(userLoan, position, loanApplyLayout.applyLoanButton)
                }

                loanPaidLayout.paidLoanButton.setOnClickListener {
                    onUserLoanSelected.onUserLoanSelected(userLoan, position, loanPaidLayout.paidLoanButton)
                }


                // Click listeners
                loanDueViewsLayout.dueLoanPayButton.setOnClickListener {
                    Log.e("TAG_2", userLoan.toString())
                    onUserLoanSelected.onUserLoanSelected(userLoan, position, loanDueViewsLayout.dueLoanPayButton)
                }
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
