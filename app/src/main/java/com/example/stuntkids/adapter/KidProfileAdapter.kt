package com.example.stuntkids.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stuntkids.R
import com.example.stuntkids.databinding.ItemAddKidBinding
import com.example.stuntkids.databinding.ItemKidGridBinding
import com.example.stuntkids.model.Kid

class KidProfileAdapter(
    private val kids: List<Kid>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onKidClicked: ((Kid) -> Unit)? = null
    var onAddKidClicked: (() -> Unit)? = null

    private val TYPE_KID = 0
    private val TYPE_ADD_KID = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == kids.size) TYPE_ADD_KID else TYPE_KID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_KID -> {
                val binding =
                    ItemKidGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                KidViewHolder(binding)
            }

            TYPE_ADD_KID -> {
                val binding =
                    ItemAddKidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AddKidViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_KID -> {
                val kid = kids[position]
                val kidViewHolder = holder as KidViewHolder
                kidViewHolder.bind(kid)
            }

            TYPE_ADD_KID -> {
                val addKidViewHolder = holder as AddKidViewHolder
                addKidViewHolder.binding.addKidButton.setOnClickListener {
                    onAddKidClicked?.invoke()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return kids.size + 1
    }

    inner class KidViewHolder(val binding: ItemKidGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(kid: Kid) {
            binding.kidLogoImage.setImageResource(kid.logoResId)
            binding.kidName.text = kid.name
            binding.kidAge.text = StringBuilder("Age : ${kid.age} Months")

            binding.existingKidBox.setOnClickListener {
                onKidClicked?.invoke(kid)
            }
        }
    }

    inner class AddKidViewHolder(val binding: ItemAddKidBinding) :
        RecyclerView.ViewHolder(binding.root)
}
