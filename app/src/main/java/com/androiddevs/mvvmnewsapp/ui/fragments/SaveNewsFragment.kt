package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsPagingAdapter
import com.androiddevs.mvvmnewsapp.databinding.FragmentSavedNewsBinding
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SaveNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsPagingAdapter
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedNewsBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_saveNewsFragment_to_articleFragment,
                bundle
            )
        }

        lifecycleScope.launch {
            viewModel.getSavedNews().collectLatest { pagingData ->
                newsAdapter.submitData(pagingData)
            }
        }

    }

    private fun setupRecyclerView() {
        newsAdapter = NewsPagingAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
