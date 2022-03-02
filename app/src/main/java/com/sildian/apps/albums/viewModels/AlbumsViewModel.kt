package com.sildian.apps.albums.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sildian.apps.albums.model.Song
import com.sildian.apps.albums.repositories.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*************************************************************************************************
 * ViewModel allowing to get and observe Albums data
 ************************************************************************************************/

@HiltViewModel
class AlbumsViewModel: ViewModel() {

    /**Repository**/

    @Inject lateinit var albumsRepository: AlbumsRepository

    /**Data**/

    private val mutableSongs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = mutableSongs
    private val mutableError = MutableLiveData<Throwable?>()
    val error: LiveData<Throwable?> = mutableError

    /**Coroutine exception handler**/

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        mutableError.postValue(throwable)
    }

    /**Requests**/

    fun loadAllSongs() {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                try {
                    mutableSongs.postValue(albumsRepository.loadAllSongs())
                    mutableError.postValue(null)
                } catch (e: Throwable) {
                    mutableError.postValue(e)
                }
            }
        }
    }
}