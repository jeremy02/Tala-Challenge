package com.example.myapplication.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.remote.responses.UserLoan
import com.example.myapplication.databinding.FragmentNewsDetailBinding
import com.example.myapplication.ui.home.HomeFragment
import java.util.*

class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>() {

    private val _TAG = HomeFragment::class.simpleName

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsDetailBinding
        get() = FragmentNewsDetailBinding::inflate

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userLoan = arguments?.getParcelable<UserLoan>("USER_LOAN_TO_LOAD")
        if (userLoan == null) {
            findNavController().popBackStack()
            return
        }

        setupViews(userLoan)
    }

    private fun setupViews(userLoan: UserLoan) {
        val locale = if(userLoan.userLoanLocale?.localeCode.isNullOrEmpty()) "KE" else userLoan.userLoanLocale?.localeCode.toString()
            .toUpperCase(Locale.getDefault())

        binding.newsDetailLocaleTag.text = locale

        // set the image
        if (userLoan.userLoanLocale?.localeCode != null) {
            when (userLoan.userLoanLocale?.localeCode) {
                "ke" -> binding.newsDetailImage.setImageResource(R.drawable.img_story_ke)
                "mx" -> binding.newsDetailImage.setImageResource(R.drawable.img_story_mx)
                "ph" -> binding.newsDetailImage.setImageResource(R.drawable.img_story_ph)
                else -> binding.newsDetailImage.setImageResource(R.drawable.img_story_ke)
            }
        } else {
            binding.newsDetailImage.setImageResource(R.drawable.img_story_ke)
        }
    }
}