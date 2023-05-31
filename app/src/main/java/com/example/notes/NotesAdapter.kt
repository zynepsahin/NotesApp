package com.example.notes

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.RecyclerRowBinding

class NotesAdapter(val notesList: ArrayList<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesHolder>() {
    class NotesHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return NotesHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.binding.recyclerViewTextView.text = notesList.get(position).title
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
            intent.putExtra("info", "old")
            intent.putExtra("id", notesList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }
}
