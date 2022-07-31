package com.androiddevs.mvvmnewsapp.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.FragmentArticleBinding
import com.androiddevs.mvvmnewsapp.presentation.NewsActivity
import com.androiddevs.mvvmnewsapp.presentation.NewsViewModel
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
            loadUrl(article.url.toString())
            val progressBar=ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
            progressBar.visibility=View.VISIBLE
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    progressBar.visibility=View.INVISIBLE

                }
            }

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

