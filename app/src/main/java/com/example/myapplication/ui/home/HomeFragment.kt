package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.UserLoansAdapter
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val _TAG = HomeFragment::class.simpleName

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var userLoansAdapter: UserLoansAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initLoadData()

        setupViews()

        initDataObservations()
    }

    private fun setupViews() {
        context?.let { _ ->
            // Loans RecyclerView click listeners
            userLoansAdapter = UserLoansAdapter(UserLoansAdapter.OnUserLoanSelected { userLoan, position, view ->
                val username = if (!userLoan.username.isNullOrEmpty()) userLoan.username else R.string.app_name
                val currency = if (!userLoan.userLoanLocale?.currency.isNullOrEmpty()) userLoan.userLoanLocale?.currency else "Kshs"
                var loanLimit = if (userLoan.userLoanLocale?.loanLimit != null) userLoan.userLoanLocale?.loanLimit else 0
                var dueLoanAmount = if (userLoan.loan?.due != null) userLoan.loan?.due else 0
                var level = if (!userLoan.loan?.level.isNullOrEmpty()) userLoan.loan?.level else "new"
                var status = if (!userLoan.loan?.status.isNullOrEmpty()) userLoan.loan?.status else "new"
                var approvedAmount = if (userLoan.loan?.approved != null) userLoan.loan?.due else 0

                when (view.id) {
                    R.id.apply_loan_button -> {
                        val textMsg = String.format(
                            getString(R.string.message_load_user_loans_apply_str),
                            username, currency, loanLimit.toString())

                        showToast( textMsg,true)
                    }
                    R.id.invite_friends_layout -> {
                        val textMsg = String.format(
                            getString(R.string.message_load_user_loans_apply_str),
                            username, currency, loanLimit.toString())

                        view.showSnack( textMsg,"OK")
                    }
                }
            })

            userLoansAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            binding.recyclerLoans.adapter = userLoansAdapter

            // NestedScrollView
            binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    viewModel.loadMoreUserLoansData()
                }
            }
        }
    }

    private fun initLoadData() {
        viewModel.fetchAllLocales(requireContext()) // Get or Load all the Locales
    }

    private fun initDataObservations() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    binding.recyclerLoans.gone()
                    binding.loadingProgress.visible()
                    binding.loadingMoreProgress.gone()
                }

                is LoadingNextPageState -> {
                    binding.loadingProgress.gone()
                    binding.loadingMoreProgress.visible()
                    showToast(getString(R.string.message_load_user_loans_str), true)
                }

                is ContentState -> {
                    binding.recyclerLoans.visible()
                    binding.loadingProgress.gone()
                    binding.loadingMoreProgress.gone()
                }

                is ErrorState -> {
                    binding.loadingProgress.gone()
                    binding.loadingMoreProgress.gone()
                    binding.nestedScrollView.showSnack(state.message, getString(R.string.action_retry_str)) {
                        viewModel.retryLoadLoans()
                    }
                }

                is ErrorNextPageState -> {
                    binding.nestedScrollView.showSnack(state.message, getString(R.string.action_retry_str)) {
                        viewModel.retryLoadLoans()
                    }
                }
                else -> {
                    // Do Nothing
                }
            }
        }

        // Observe User Loans Data
        viewModel.userLoansPaginatedListLiveData.observe(viewLifecycleOwner) { userLoans ->
            userLoansAdapter.updateItems(userLoans)
        }
    }
}