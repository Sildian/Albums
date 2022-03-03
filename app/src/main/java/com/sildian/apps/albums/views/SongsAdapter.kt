package com.sildian.apps.albums.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sildian.apps.albums.R
import com.sildian.apps.albums.databinding.ItemListSongBinding
import com.sildian.apps.albums.model.Song

/*************************************************************************************************
 * Adapter showing the list of songs by album
 ************************************************************************************************/

class SongsAdapter(private val songs: List<Song>)
    : RecyclerView.Adapter<SongsAdapter.SongsViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val binding = ItemListSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(this.songs[position])
    }

    override fun getItemCount(): Int = this.songs.size

    /**ViewHolder showing a song**/

    class SongsViewHolder(private val binding: ItemListSongBinding)
        : RecyclerView.ViewHolder(binding.root)
    {

        private var song: Song? = null

        fun bind(song: Song) {
            this.song = song
            updateImage()
            updateTitleText()
        }

        private fun updateImage() {
            this.song?.url?.let { url ->
                Glide.with(itemView)
                    .load("$url.png")
                    .centerCrop()
                    .placeholder(AppCompatResources.getDrawable(itemView.context, R.drawable.ic_album))
                    .into(this.binding.itemListSongsImage)
            }?: this.binding.itemListSongsImage.setImageDrawable(
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_album)
            )
        }

        private fun updateTitleText() {
            this.binding.itemListSongsTitle.text = this.song?.title?: ""
        }
    }
}