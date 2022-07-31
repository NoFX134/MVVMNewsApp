package com.androiddevs.mvvmnewsapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.presentation.adapters.NewsLoaderStateAdapter
import com.androiddevs.mvvmnewsapp.presentation.adapters.NewsPagingAdapter
import com.androiddevs.mvvmnewsapp.databinding.FragmentSavedNewsBinding
import com.androiddevs.mvvmnewsapp.presentation.NewsActivity
import com.androiddevs.mvvmnewsapp.presentation.NewsViewModel
import com.google.android.material.snackbar.Snackbar
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
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = newsAdapter.snapshot().items[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, getString(R.string.ArticleDeleteMessage), Snackbar.LENGTH_LONG)
                    .apply {
                        setAction(getString(R.string.Undo)) {
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvSavedNews)
        lifecycleScope.launch {
            viewModel.getSavedNews().collectLatest { pagingData ->
                newsAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsPagingAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter.withLoadStateFooter(NewsLoaderStateAdapter())
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
