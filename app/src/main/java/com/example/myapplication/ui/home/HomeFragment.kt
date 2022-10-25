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
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.showSnack
import com.example.myapplication.utils.showToast
import com.example.myapplication.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val _TAG = HomeFragment::class.simpleName

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    lateinit var userLoansAdapter: UserLoansAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()

        initLoadData()

        initDataObservations()
    }

    private fun setupViews() {
        context?.let { _ ->
            // Loans RecyclerView
            userLoansAdapter = UserLoansAdapter() { userLoan, _ ->
                Log.e(_TAG, "User Loan Clicked: $userLoan")
            }

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
                    showToast(getString(R.string.message_load_user_loans_str))
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