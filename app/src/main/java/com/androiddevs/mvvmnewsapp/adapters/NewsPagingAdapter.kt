package com.androiddevs.mvvmnewsapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.ItemArticlePreviewBinding
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.utils.Constants.TIME_PATTERN
import com.bumptech.glide.Glide
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class NewsPagingAdapter :
    PagingDataAdapter<Article, NewsPagingAdapter.ArticleViewHolder>(differCallback) {

    private var onItemClickListener: ((Article) -> Unit)? = null

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemArticlePreviewBinding = ItemArticlePreviewBinding.bind(itemView)

        fun bind(article: Article?) {
            Glide.with(itemView).load(article?.urlToImage).into(binding.ivArticleImage)
            binding.tvSource.text = article?.source?.name
            binding.tvTitle.text = article?.title
            binding.tvDescription.text = article?.upper()
            binding.tvPublishedAt.text = ZonedDateTime.parse(article?.publishedAt)
                .format(DateTimeFormatter.ofPattern(TIME_PATTERN, Locale("ru")))
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    if (article != null) {
                        it(article)
                    }
                }
            }
        }
    }

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_preview, parent, false)
        )


    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

}