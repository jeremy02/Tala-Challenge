package com.example.myapplication.ui.web_views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentWebViewBinding
import com.example.myapplication.utils.showSnack
import com.example.myapplication.utils.showToast

class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    private val _TAG = WebViewFragment::class.simpleName

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWebViewBinding
        get() = FragmentWebViewBinding::inflate

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val urlToLoad = arguments?.getString("URL_TO_LOAD", null)
        if (urlToLoad == null) {
            findNavController().popBackStack()
            return
        }

        setupViews()

        loadWebView(urlToLoad)
    }

    private fun setupViews() {
    }

    private fun loadWebView(url: String) {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, weburl: String) {
                showToast("WebView is Loaded....", true)
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                view.showSnack("Page loading : $newProgress%")
                if (newProgress == 100) {
                    view.showSnack("Page Loaded....", )
                }
            }
        }
        binding.webView.loadUrl(url)

        // Enable the javascript settings
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
    }
}