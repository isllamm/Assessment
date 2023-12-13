package com.technical.assessment.featuremain.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.technical.assessment.databinding.ItemAlbumBinding
import com.technical.assessment.featuremain.domain.model.Album
import com.technical.assessment.featuremain.presentation.adapters.interfaces.AlbumEvent


class AlbumsAdapter(private val onEvent: AlbumEvent) :
    ListAdapter<Album, AlbumsAdapter.PlateViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Album, newItem: Album) =
                oldItem == newItem
        }
    }

    class PlateViewHolder(private val binding: ItemAlbumBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(album: Album) {
            binding.apply {
                nameTv.text = album.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            holder.itemView.setOnClickListener {
                onEvent.onAlbumClickListener(currentItem)
            }

        }
    }
}