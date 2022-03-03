package com.sildian.apps.albums.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sildian.apps.albums.databinding.FragmentAlbumsBinding
import com.sildian.apps.albums.model.Song
import com.sildian.apps.albums.viewModels.AlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint

/*************************************************************************************************
 * Fragment showing the list of songs by album
 ************************************************************************************************/

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    /**Data**/

    private val albumsViewModel: AlbumsViewModel by viewModels()
    private val songs = arrayListOf<Song>()

    /**UI**/

    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var songsAdapter: SongsAdapter

    /**Life cycle**/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        initUI()
        initData()
        if (this.albumsViewModel.songs.value.isNullOrEmpty()) {
            loadData(true)
        }
        return this.binding.root
    }

    /**Data monitoring**/

    private fun initData() {
        this.albumsViewModel.songs.observe(viewLifecycleOwner) { songs ->
            onLoadDataSuccess(songs)
        }
        this.albumsViewModel.error.observe(viewLifecycleOwner) { e ->
            e?.let {
                onLoadDataFailure(e)
            }
        }
    }

    private fun loadData(isFirstLoad: Boolean) {
        if (isFirstLoad) {
            updateProgressBarVisibility(true)
        }
        this.albumsViewModel.loadAllSongs()
    }

    private fun onLoadDataSuccess(songs: List<Song>) {
        updateProgressBarVisibility(false)
        hideSwipe()
        updateSongsList(songs)
        updatePlaceHolderTextVisibility()
    }

    private fun onLoadDataFailure(e: Throwable) {
        updateProgressBarVisibility(false)
        hideSwipe()
        updatePlaceHolderTextVisibility()
    }

    /**UI monitoring**/

    private fun initUI() {
        this.songsAdapter = SongsAdapter(this.songs)
        this.binding.fragmentAlbumsListSongs.adapter = this.songsAdapter
        this.binding.fragmentAlbumsSwipe.setOnRefreshListener {
            loadData(false)
        }
    }

    private fun updateSongsList(songs: List<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
        this.songsAdapter.notifyDataSetChanged()
    }

    private fun updateProgressBarVisibility(isVisible: Boolean) {
        this.binding.fragmentAlbumsProgressBar.visibility =
            takeIf { isVisible }?.let { View.VISIBLE }?: View.GONE
    }

    private fun hideSwipe() {
        this.binding.fragmentAlbumsSwipe.isRefreshing = false
    }

    private fun updatePlaceHolderTextVisibility() {
        this.binding.fragmentAlbumsTextPlaceHolder.visibility =
            takeIf { this.songs.isEmpty() }?.let { View.VISIBLE }?: View.GONE
    }
}