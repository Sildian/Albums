package com.sildian.apps.albums.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sildian.apps.albums.repositories.AlbumsRepository
import com.sildian.apps.albums.testUtils.FakeAlbumQueries
import com.sildian.apps.albums.testUtils.ServerSimulator
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/*************************************************************************************************
 * Tests for AlbumsViewModel
 ************************************************************************************************/

@ExperimentalCoroutinesApi
class AlbumsViewModelTest {

    class MainCoroutineRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()):
        TestWatcher(),
        TestCoroutineScope by TestCoroutineScope(dispatcher) {
        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(dispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            cleanupTestCoroutines()
            Dispatchers.resetMain()
        }
    }

    private val dispatcher = TestCoroutineDispatcher()
    private lateinit var albumsViewModel: AlbumsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {
        this.albumsViewModel = AlbumsViewModel(AlbumsRepository(FakeAlbumQueries(), this.dispatcher))
    }

    @After
    fun finish() {
        ServerSimulator.isServerReachable = true
    }

    @Test
    fun given_serverUnreachable_when_loadAllSongs_then_checkExceptionIsRaised() {
        runBlocking {
            launch(Dispatchers.Main) {
                ServerSimulator.isServerReachable = false
                albumsViewModel.loadAllSongs()
                assertNull(albumsViewModel.songs.value)
                assertNotNull(albumsViewModel.error.value)
            }
        }
    }

    @Test
    fun given_everythingOk_when_loadAllSongs_then_checkSongsArePresentAndSorted() {
        runBlocking {
            launch(Dispatchers.Main) {
                albumsViewModel.loadAllSongs()
                assertEquals(5, albumsViewModel.songs.value?.size)
                assertNull(albumsViewModel.error.value)
            }
        }
    }
}