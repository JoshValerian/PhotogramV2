package com.jmgames.photogramv2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jmgames.photogramv2.R

class CommentAdapter(var comments: List<String>) : RecyclerView.Adapter<CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvComment: TextView = itemView.findViewById(R.id.tvComent)

    fun bind(comment: String) {
        tvComment.text = comment
    }
}
