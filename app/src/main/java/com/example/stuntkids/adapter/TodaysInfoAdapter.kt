package com.example.stuntkids.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.stuntkids.R
import com.example.stuntkids.model.TodaysInfo
import com.bumptech.glide.Glide
import com.example.stuntkids.model.Article

class TodaysInfoAdapter(private val articles: List<Article>) : RecyclerView.Adapter<TodaysInfoAdapter.TodaysInfoViewHolder>() {

    // ViewHolder untuk item RecyclerView
    class TodaysInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.infoTitle)
        val descriptionText: TextView = view.findViewById(R.id.infoDescription)
        val infoImage: ImageView = view.findViewById(R.id.infoImage)  // Untuk ImageView
    }

    // Menginflate layout item dan mengikat data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodaysInfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todays_information, parent, false)
        return TodaysInfoViewHolder(itemView)
    }

    // Mengikat data dengan tampilan item
    override fun onBindViewHolder(holder: TodaysInfoViewHolder, position: Int) {
        val article = articles[position]
        holder.titleText.text = article.title
        holder.descriptionText.text = article.content

        // Menggunakan Glide untuk memuat gambar dari URL atau sumber lainnya
        Glide.with(holder.itemView.context)
            .load(article.imageUrl) // Pastikan ada properti imageUrl di model
            .placeholder(R.drawable.ic_news_articles)
            .error(R.drawable.ic_error)
            .into(holder.infoImage)

        holder.itemView.setOnClickListener {
            val url = article.url
            if (url.isNotEmpty() && Uri.parse(url).isAbsolute) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                holder.itemView.context.startActivity(intent)
            } else {
                Toast.makeText(holder.itemView.context, "URL tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}