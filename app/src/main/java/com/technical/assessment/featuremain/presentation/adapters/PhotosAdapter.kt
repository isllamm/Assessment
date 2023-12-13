package com.technical.assessment.featuremain.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.technical.assessment.databinding.ItemImageBinding
import com.technical.assessment.featuremain.domain.model.Photo
import com.technical.assessment.featuremain.presentation.adapters.interfaces.PhotoEvent


class PhotosAdapter(private val onEvent: PhotoEvent) :
    ListAdapter<Photo, PhotosAdapter.PlateViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem
        }
    }

    class PlateViewHolder(private val binding: ItemImageBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(photo: Photo) {
            binding.apply {
                imageIv.load(photo.url){
                    //error(photo.thumbnailUrl!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            holder.itemView.setOnClickListener {
                onEvent.onPhotoClickListener(currentItem)
            }

        }
    }
}