package com.androiddevs.mvvmnewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.FragmentArticleBinding
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

@SuppressLint("SetJavaScriptEnabled")
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: NewsViewModel
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        _binding = FragmentArticleBinding.bind(view)
        val article = args.article

        (activity as NewsActivity).supportActionBar?.apply {
            title = article.source.name.uppercase()
            setDisplayHomeAsUpEnabled(false)
        }

        binding.webView.apply {
            isLongClickable = true
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            registerForContextMenu(this)
            val webSettings: WebSettings = settings
            webSettings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = true
                displayZoomControls = false
                setSupportZoom(true)
                defaultTextEncodingName = "utf-8"
            }

            webViewClient = WebViewClient()
            loadUrl(article.url.toString())
        }
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,getString(R.string.ArticleSaveMessage), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

