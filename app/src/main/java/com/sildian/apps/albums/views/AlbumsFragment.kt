package com.sildian.apps.albums.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sildian.apps.albums.databinding.FragmentAlbumsBinding
import com.sildian.apps.albums.viewModels.AlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint

/*************************************************************************************************
 * Fragment showing the list of songs
 ************************************************************************************************/

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    /**Data**/

    private val albumsViewModel: AlbumsViewModel by viewModels()

    /**UI**/

    private lateinit var binding: FragmentAlbumsBinding

    /**Life cycle**/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        if (savedInstanceState == null) {
            initData()
        }
        if (this.albumsViewModel.songs.value.isNullOrEmpty()) {
            loadData()
        }
        return this.binding.root
    }

    /**Data monitoring**/

    private fun initData() {
        Log.d("AlbumsFragment", "Init data")
        this.albumsViewModel.songs.observe(viewLifecycleOwner) { songs ->
            Log.d("AlbumsFragment", "${songs.size} songs loaded")
        }
        this.albumsViewModel.error.observe(viewLifecycleOwner) { e ->
            Log.e("AlbumsFragment", "Error while loading songs : ${e?.message}")
        }
    }

    private fun loadData() {
        Log.d("AlbumsFragment", "Load data")
        this.albumsViewModel.loadAllSongs()
    }
}