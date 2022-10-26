package com.example.myapplication.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentNewsDetailBinding
import com.example.myapplication.ui.home.HomeFragment

class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>() {

    private val _TAG = HomeFragment::class.simpleName

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsDetailBinding
        get() = FragmentNewsDetailBinding::inflate

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

    }
}