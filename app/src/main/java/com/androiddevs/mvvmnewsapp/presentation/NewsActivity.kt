package com.androiddevs.mvvmnewsapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.ActivityNewsBinding
import com.androiddevs.mvvmnewsapp.data.remote.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.data.repository.NewsRepositoryImpl
import com.androiddevs.mvvmnewsapp.domain.usecase.GetBreakingNewsUseCase
import com.google.android.material.bottomnavigation.BottomNavigationView


class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsRepository = NewsRepositoryImpl(ArticleDatabase(this))
        val getBreakingNewsUseCase= GetBreakingNewsUseCase(newsRepository)
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository,getBreakingNewsUseCase)
        viewModel= ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.breakingNewsFragment,
                R.id.saveNewsFragment,
                R.id.searchNewsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}
