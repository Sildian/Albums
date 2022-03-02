package com.sildian.apps.albums.repositories

import com.sildian.apps.albums.dataSource.AlbumQueries
import com.sildian.apps.albums.model.Song
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/*************************************************************************************************
 * Repository allowing to get Albums data
 ************************************************************************************************/

@ViewModelScoped
class AlbumsRepository @Inject constructor (private val albumsQueries: AlbumQueries) {

    suspend fun loadAllSongs(): List<Song> {
        //TODO implement
        return arrayListOf()
    }
}