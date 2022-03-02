package com.sildian.apps.albums.repositories

import com.sildian.apps.albums.dataSource.AlbumQueries
import com.sildian.apps.albums.model.Song
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/*************************************************************************************************
 * Tests for AlbumsRepository
 ************************************************************************************************/

class AlbumsRepositoryTest {

    /**Fake data to avoid real queries on the server**/

    private class FakeAlbumsQueries: AlbumQueries {

        private val songs = arrayListOf(
            Song(2, 2, "Iron Savior 2"),
            Song(1, 3, "Metallica 3"),
            Song(1, 1, "Metallica 1"),
            Song(2, 1, "Iron Savior 1"),
            Song(1, 2, "Metallica 2")
        )

        override suspend fun getAllSongs(): List<Song> {
            return this.songs
        }
    }

    /**Tests**/

    private lateinit var albumsRepository: AlbumsRepository

    @Before
    fun init() {
        this.albumsRepository = AlbumsRepository(FakeAlbumsQueries())
    }

    @Test
    fun given_noArgs_when_loadAllSongs_then_checkSongsArePresentAndSorted() {
        runBlocking {
            val songs = albumsRepository.loadAllSongs()
            assertEquals(5, songs.size)
            assertEquals("Metallica 1", songs[0])
            assertEquals("Metallica 2", songs[1])
            assertEquals("Metallica 3", songs[2])
            assertEquals("Iron Savior 1", songs[3])
            assertEquals("Iron Savior 2", songs[4])
        }
    }
}