package com.sildian.apps.albums.testUtils

import com.sildian.apps.albums.dataSource.AlbumQueries
import com.sildian.apps.albums.model.Song

/*************************************************************************************************
 * Fake data to avoid real queries on the server during tests
 ************************************************************************************************/

class FakeAlbumQueries: AlbumQueries {

    private val songs = arrayListOf(
        Song(2, 2, "Iron Savior 2"),
        Song(1, 3, "Metallica 3"),
        Song(1, 1, "Metallica 1"),
        Song(2, 1, "Iron Savior 1"),
        Song(1, 2, "Metallica 2")
    )

    override suspend fun getAllSongs(): List<Song> {
        if (!ServerSimulator.isServerReachable) {
            throw Exception("Server is unreachable")
        }
        return this.songs
    }
}