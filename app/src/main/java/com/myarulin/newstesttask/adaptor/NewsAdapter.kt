package com.myarulin.newstesttask.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myarulin.newstesttask.R
import com.myarulin.newstesttask.adaptor.NewsAdapter.ArticleViewHolder
import com.myarulin.newstesttask.model.ArticleModel

class NewsAdapter : RecyclerView.Adapter<ArticleViewHolder>()  {

    private var article: List<ArticleModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_copy, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = article[position]
        holder.itemView.apply {
            Glide.with(this).load(item.imageURL).into(holder.image)
            holder.title.text = item.title
            holder.description.text = item.description
            holder.website.text = item.website
        }
    }

    override fun getItemCount(): Int = article.size

    fun setProducts(article: List<ArticleModel>) {
        val result = DiffUtil.calculateDiff(ArticlesDiffUtilCallback(this.article, article))
        result.dispatchUpdatesTo(this)
        this.article = article

    }


    class ArticleViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        var image: ImageView
        var title: TextView
        var description: TextView
        var website: TextView

        init {
            image = view.findViewById(R.id.ivNews)
            title = view.findViewById(R.id.tvTitle)
            description = view.findViewById(R.id.tvDescription)
            website = view.findViewById(R.id.tvWebsite)
        }
    }


    private class ArticlesDiffUtilCallback(
        private var oldItems: List<ArticleModel>,
        private var newItems: List<ArticleModel>
    ) : DiffUtil.Callback() {


        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size


        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].newsId == newItems[newItemPosition].newsId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }
}

