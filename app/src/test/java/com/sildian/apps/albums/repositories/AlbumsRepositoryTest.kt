package com.sildian.apps.albums.repositories

import com.sildian.apps.albums.testUtils.FakeAlbumQueries
import com.sildian.apps.albums.testUtils.ServerSimulator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/*************************************************************************************************
 * Tests for AlbumsRepository
 ************************************************************************************************/

@ExperimentalCoroutinesApi
class AlbumsRepositoryTest {

    private val dispatcher = TestCoroutineDispatcher()
    private lateinit var albumsRepository: AlbumsRepository

    @Before
    fun init() {
        Dispatchers.setMain(this.dispatcher)
        this.albumsRepository = AlbumsRepository(FakeAlbumQueries(), this.dispatcher)
    }

    @After
    fun finish() {
        ServerSimulator.isServerReachable = true
        Dispatchers.resetMain()
    }

    @Test
    fun given_serverUnreachable_when_getAllSongs_then_checkExceptionIsRaised() {
        runBlocking {
            ServerSimulator.isServerReachable = false
            val songs = try {
                albumsRepository.loadAllSongs()
            } catch (e: Exception) {
                null
            }
            assertNull(songs)
        }
    }

    @Test
    fun given_everythingOk_when_getAllSongs_then_checkSongsArePresentAndSorted() {
        runBlocking {
            val songs = albumsRepository.loadAllSongs()
            assertEquals(5, songs.size)
            assertEquals("Metallica 1", songs[0].title)
            assertEquals("Metallica 2", songs[1].title)
            assertEquals("Metallica 3", songs[2].title)
            assertEquals("Iron Savior 1", songs[3].title)
            assertEquals("Iron Savior 2", songs[4].title)
        }
    }
}