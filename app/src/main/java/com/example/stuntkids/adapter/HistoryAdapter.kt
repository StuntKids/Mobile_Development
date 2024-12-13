package com.example.stuntkids.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stuntkids.R
import com.example.stuntkids.data.model.History
import com.example.stuntkids.data.model.HistoryData
import com.example.stuntkids.data.model.PredictModel
import com.example.stuntkids.model.Article

class HistoryAdapter(
    private val history: List<HistoryData>
) : RecyclerView.Adapter<HistoryAdapter.ArticleViewHolder>() {
    var onHistoryClick: ((HistoryData) -> Unit)? = null

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_row, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = history[position]
        val model = PredictModel.fromRequestText(item.history.result)
        val title = holder.itemView.findViewById<TextView>(R.id.tv_name)
        val status = holder.itemView.findViewById<TextView>(R.id.tv_status)
        val date = holder.itemView.findViewById<TextView>(R.id.tv_date)

        title.text = model?.name ?: ""
        status.text = model?.result ?: ""
        when (PredictModel.fromRequestText(item.history.result)?.result) {
            "Stunted" -> {
                status.backgroundTintList = ContextCompat.getColorStateList(
                    holder.itemView.context,
                    R.color.red
                )
            }
            "Normal" -> {
                status.backgroundTintList = ContextCompat.getColorStateList(
                    holder.itemView.context,
                    R.color.green
                )
            }
            "Severly Stunted" -> {
                status.backgroundTintList = ContextCompat.getColorStateList(
                    holder.itemView.context,
                    R.color.red
                )
            }
            "Tinggi" -> {
                status.backgroundTintList = ContextCompat.getColorStateList(
                    holder.itemView.context,
                    R.color.green
                )
            }
            else -> {
                status.backgroundTintList = ContextCompat.getColorStateList(
                    holder.itemView.context,
                    R.color.gray
                )
            }
        }
        date.text = item.history.createdAt
        holder.itemView.setOnClickListener {
            onHistoryClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return history.size
    }
}
