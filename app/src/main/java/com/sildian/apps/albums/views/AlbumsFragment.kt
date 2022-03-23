package com.sildian.apps.albums.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sildian.apps.albums.R
import com.sildian.apps.albums.databinding.FragmentAlbumsBinding
import com.sildian.apps.albums.model.Song
import com.sildian.apps.albums.utils.Utils
import com.sildian.apps.albums.viewModels.AlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint

/*************************************************************************************************
 * Fragment showing the list of songs by album
 ************************************************************************************************/

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    companion object {
        private const val TAG = "AlbumsFragment"
    }

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
        return this.binding.root
    }

    override fun onStart() {
        super.onStart()
        if (this.albumsViewModel.songs.value.isNullOrEmpty()) {
            loadData(true)
        }
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

    private fun loadData(showProgressBar: Boolean) {
        context?.let { context ->
            if (Utils.isNetworkAvailable(context)) {
                if (showProgressBar) {
                    updateProgressBarVisibility(true)
                }
                this.albumsViewModel.loadAllSongs()
            } else {
                hideSwipe()
                updatePlaceHolderTextVisibility()
                showMessage(R.string.text_message_songs_load_failure_network_unavailable)
            }
        }
    }

    private fun onLoadDataSuccess(songs: List<Song>) {
        Log.d(TAG, "${songs.size} songs successfully loaded")
        updateProgressBarVisibility(false)
        hideSwipe()
        updateSongsList(songs)
        updatePlaceHolderTextVisibility()
    }

    private fun onLoadDataFailure(e: Throwable) {
        Log.e(TAG, "Error while loading songs : ${e.message}")
        updateProgressBarVisibility(false)
        hideSwipe()
        updatePlaceHolderTextVisibility()
        showMessage(R.string.text_message_songs_load_failure_other)
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

    private fun showMessage(@StringRes messageResourceId: Int) {
        view?.let { view ->
            Snackbar
                .make(view, messageResourceId, Snackbar.LENGTH_LONG)
                .setAction(R.string.button_retry) { loadData(true) }
                .show()
        }
    }
}