package com.example.stuntkids.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stuntkids.R
import com.example.stuntkids.model.Article

class FoodAdapter(private var articles: List<Article>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var filteredArticles = articles

    // ViewHolder for RecyclerView items
    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.articleTitle)
        val contentText: TextView = view.findViewById(R.id.articleContent)
        val articleImage: ImageView = view.findViewById(R.id.articleImage)  // For ImageView
    }

    // Inflate the item layout and bind data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_row, parent, false)
        return FoodViewHolder(itemView)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val article = filteredArticles[position]
        holder.titleText.text = article.title
        holder.contentText.text = article.content

        // Use Glide to load image from URL or other sources
        Glide.with(holder.itemView.context)
            .load(article.imageUrl) // Load image from URL
            .placeholder(R.drawable.ic_news_articles)  // Placeholder if image is not loaded
            .error(R.drawable.ic_error)  // Error image if loading fails
            .into(holder.articleImage)  // Display image in ImageView
    }

    // Return the number of items to be displayed (filtered articles count)
    override fun getItemCount(): Int {
        return filteredArticles.size
    }

    // Function to filter articles based on search query
    fun filterArticles(query: String?) {
        filteredArticles = if (query.isNullOrEmpty()) {
            articles // Show all articles if query is empty
        } else {
            articles.filter {
                it.title.contains(query, ignoreCase = true) || // Match article title with query
                        it.content.contains(query, ignoreCase = true) // Match article content with query
            }
        }
        notifyDataSetChanged() // Notify adapter to update the view
    }
}