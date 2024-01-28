package com.victor.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.victor.core.common.result.fold
import com.victor.core.model.MovieModel
import com.victor.domain.genres.GetGenresUseCase
import com.victor.domain.movies.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val actionStateFlow = MutableSharedFlow<UiAction>()

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState>
        get() = _homeUiState.asStateFlow()

    val uiActions: (UiAction) -> Unit

    lateinit var moviesPagingDataFlow: Flow<PagingData<MovieModel>>

    init {
        collectGenres()
        collectMoviesByGenre()

        uiActions = { uiAction ->
            viewModelScope.launch {
                actionStateFlow.emit(uiAction)
            }
        }
    }

    private fun selectGenreFlow(): Flow<UiAction.GenreSelected> {
        val lastSelectedGenreId: Int? = savedStateHandle[LAST_SELECTED_GENRE_ID]
        val lastSelectedGenreName: String? = savedStateHandle[LAST_SELECTED_GENRE_NAME]

        return actionStateFlow
            .filterIsInstance<UiAction.GenreSelected>()
            .distinctUntilChanged()
            .onStart {
                emit(
                    UiAction.GenreSelected(
                        genreId = lastSelectedGenreId,
                        genreName = lastSelectedGenreName ?: ""
                    )
                )
            }
    }

    private fun collectMoviesByGenre() {
        moviesPagingDataFlow = selectGenreFlow().flatMapLatest { genreSelected ->
            _homeUiState.update { uiState ->
                when (uiState) {
                    is HomeUiState.Success -> {
                        uiState.copy(selectedGenreName = genreSelected.genreName)
                    }

                    HomeUiState.Loading,
                    HomeUiState.Error -> {
                        HomeUiState.Success(selectedGenreName = genreSelected.genreName)
                    }
                }
            }

            Log.d("MainViewModel", "Searching ${genreSelected.genreName} movies")
            getMoviesUseCase(
                genreId = genreSelected.genreId.let { genreId ->
                    when (genreId) {
                        0 -> null
                        else -> genreId
                    }
                }
            )
        }.cachedIn(viewModelScope)
    }

    private fun collectGenres() {
        viewModelScope.launch {
            getGenresUseCase().collectLatest { resultGenreList ->
                resultGenreList.fold({ genresList ->
                    _homeUiState.update { uiState ->
                        when (uiState) {
                            is HomeUiState.Success -> {
                                uiState.copy(genreList = genresList)
                            }

                            HomeUiState.Loading,
                            HomeUiState.Error -> {
                                HomeUiState.Success(genreList = genresList)
                            }
                        }
                    }
                }, {
                    _homeUiState.update {
                        HomeUiState.Error
                    }
                })
            }
        }
    }

    companion object {
        private const val LAST_SELECTED_GENRE_ID = "last_selected_genre_id"
        private const val LAST_SELECTED_GENRE_NAME = "last_selected_genre_name"
    }
}

sealed class UiAction {
    data class GenreSelected(val genreId: Int?, val genreName: String) : UiAction()
}
