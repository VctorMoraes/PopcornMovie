package com.victor.popcornmovie.view.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.victor.core.common.result.fold
import com.victor.core.model.MovieModel
import com.victor.domain.genres.GetGenresUseCase
import com.victor.domain.movies.GetMoviesUseCase
import com.victor.popcornmovie.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val initialHomeUiState: HomeUiState by lazy { HomeUiState() }
    private val _homeUiState: MutableLiveData<HomeUiState> = MutableLiveData(initialHomeUiState)

    val homeUiState: LiveData<HomeUiState> //Todo: Refactor to Flow
        get() = _homeUiState

    val uiActions: (UiAction) -> Unit

    val moviesPagingDataFlow: Flow<PagingData<MovieModel>>

    init {
        val lastSelectedGenre: Int? = savedStateHandle[LAST_SELECTED_GENRE_ID]

        loadGenres()

        val actionStateFlow = MutableSharedFlow<UiAction>()
        val selectedGenresFlow =
            actionStateFlow
                .filterIsInstance<UiAction.GenreSelected>()
                .distinctUntilChanged()
                .onStart { emit(UiAction.GenreSelected(genreId = lastSelectedGenre)) }

        moviesPagingDataFlow = selectedGenresFlow.flatMapLatest { genreSelected ->
            genreSelected.genreName?.let { genreName ->
                _homeUiState.value = _homeUiState.value?.copy(
                    selectedGenreName = genreName
                )
            }
            getMoviesUseCase(genreId = genreSelected.genreId)
        }.cachedIn(viewModelScope)

        uiActions = { uiAction ->
            viewModelScope.launch {
                actionStateFlow.emit(uiAction)
            }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase().collectLatest {
                it.fold({ genresList ->
                    _homeUiState.value = _homeUiState.value?.copy(
                        genreList = genresList
                    )
                }, {
                    TODO()
                })
            }
        }
    }

    companion object {
        private const val LAST_SELECTED_GENRE_ID = "last_selected_genre_id"
    }
}

sealed class UiAction {
    data class GenreSelected(val genreId: Int? = null, val genreName: String? = null) : UiAction()
}
