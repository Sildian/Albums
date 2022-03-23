package com.sildian.apps.albums.repositories

import com.sildian.apps.albums.dataSource.AlbumQueries
import com.sildian.apps.albums.model.Song
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*************************************************************************************************
 * Repository allowing to get Albums data
 ************************************************************************************************/

@ViewModelScoped
class AlbumsRepository @Inject constructor (
    private val albumsQueries: AlbumQueries,
    private val dispatcher: CoroutineDispatcher
)
{

    suspend fun loadAllSongs(): List<Song> =
        withContext(this.dispatcher) {
            albumsQueries.getAllSongs()
                .sortedBy { it.id }
                .sortedBy { it.albumId }
        }
}