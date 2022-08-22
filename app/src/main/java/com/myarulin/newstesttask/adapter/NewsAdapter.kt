package com.myarulin.newstesttask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myarulin.newstesttask.R
import com.myarulin.newstesttask.adapter.NewsAdapter.ArticleViewHolder
import com.myarulin.newstesttask.db.Article
import java.net.URL

class NewsAdapter(
    private val onItemClick: (Article) -> Unit,
    private val onShareClick: (Article) -> Unit,
    private val onBookmarkClick: (Article) -> Unit,
) : RecyclerView.Adapter<ArticleViewHolder>()  {

    private var articles: List<Article> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_copy, parent, false)
        return ArticleViewHolder(view, onItemClick, onShareClick, onBookmarkClick)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    fun setProducts(article: List<Article>) {
        val result = DiffUtil.calculateDiff(ArticlesDiffUtilCallback(this.articles, article))
        result.dispatchUpdatesTo(this)
        this.articles = article
    }


    class ArticleViewHolder(
        view: View,
        private val onItemClick: (Article) -> Unit,
        private val onShareClick: (Article) -> Unit,
        private val onBookmarkClick: (Article) -> Unit,
    ) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.ivNews)
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val description: TextView = view.findViewById(R.id.tvDescription)
        private val website: TextView = view.findViewById(R.id.tvWebsite)
        private val ivBookmark: ImageView = view.findViewById(R.id.ivBookmark)
        private val ivShare: ImageView = view.findViewById(R.id.ivShare)
        private val clCardContainer: ConstraintLayout = view.findViewById(R.id.clCardContainer)

        fun bind(item: Article) {
            Glide.with(itemView.context).load(item.urlToImage).into(image)
            title.text = item.title
            description.text = item.description
            val url = URL(item.url)
            website.text = url.host
            ivBookmark.setOnClickListener { onBookmarkClick(item) }
            ivShare.setOnClickListener { onShareClick(item) }
            clCardContainer.setOnClickListener { onItemClick(item) }
        }
    }


    private class ArticlesDiffUtilCallback(
        private var oldItems: List<Article>,
        private var newItems: List<Article>
    ) : DiffUtil.Callback() {


        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size


        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }
}

