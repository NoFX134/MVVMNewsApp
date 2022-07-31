package com.androiddevs.mvvmnewsapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.presentation.adapters.NewsLoaderStateAdapter
import com.androiddevs.mvvmnewsapp.presentation.adapters.NewsPagingAdapter
import com.androiddevs.mvvmnewsapp.databinding.FragmentSearchNewsBinding
import com.androiddevs.mvvmnewsapp.presentation.NewsActivity
import com.androiddevs.mvvmnewsapp.presentation.NewsViewModel
import com.androiddevs.mvvmnewsapp.utils.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchNewsFragment: Fragment(R.layout.fragment_search_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsPagingAdapter
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    private var job: Job? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchNewsBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle =Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

//      binding.etSearch.addTextChangedListener {editable ->
//            job?.cancel()
//            job= MainScope().launch {
//                delay(Constants.SEARCH_NEWS_TIME_DELAY)
//                editable?.let {
//                    if(editable.toString().isNotEmpty()){
//                        viewModel.setQuery(editable.toString())
//                        viewModel.searchNews.collectLatest { pagingData ->
//                            newsAdapter.submitData(pagingData)
//                        }
//                    }
//                }
//            }
//
//        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsPagingAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter.withLoadStateFooter(NewsLoaderStateAdapter())
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
