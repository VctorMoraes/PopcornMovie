package com.victor.feature.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.victor.core.common.base.BaseActivity
import com.victor.core.model.GenreModel
import com.victor.feature.home.databinding.ActivityMainBinding
import com.victor.feature.home.viewmodel.MainViewModel
import com.victor.feature.home.viewmodel.UiAction
import com.victor.feature.home.viewmodel.uiState.HomeUiState
import com.victor.feature.home.viewmodel.uiState.MovieDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var genreSelected: GenreModel

    var clickedMovie: MovieDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        genreSelected = GenreModel(0, getString(R.string.trending))

        setupView()
        setupUi()
        initiateNavigationListeners()
        setupMovieList()
    }

    private fun setupView() {
        viewBinding.apply {
            setupActionBar()
            setupActionBarDrawer()
        }
    }

    private fun ActivityMainBinding.setupActionBar() {
        setSupportActionBar(toolbar.appBar)
    }

    private fun ActivityMainBinding.setupActionBarDrawer() {
        toggle = ActionBarDrawerToggle(
            this@HomeActivity,
            drawerLayout,
            toolbar.appBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupMovieList() {
        viewBinding.composeView.setContent {
            val moviesListItems: LazyPagingItems<MovieDetails> =
                viewModel.moviesPagingDataFlow.collectAsLazyPagingItems()

            when (moviesListItems.loadState.refresh) {
                LoadState.Loading -> Log.d("HomeActivity", "Loading movies")
                is LoadState.Error -> Log.d("HomeActivity", "Loading movies error")

                else -> {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 16.dp
                        ),
                        columns = GridCells.Fixed(2),
                        content = {
                            items(moviesListItems.itemCount) {
                                moviesListItems[it]?.let { movieDetails ->
                                    MovieCard(movieDetails = movieDetails)
                                }
                            }
                        })
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    private fun MovieCard(movieDetails: MovieDetails) {
        var showBottomSheet by remember { mutableStateOf(false) }

        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray,
            ),
            onClick = {
                showBottomSheet = true
            }
        ) {
            AsyncImage(
                model = movieDetails.posterPath,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                text = movieDetails.title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                windowInsets = WindowInsets.safeDrawing,
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("Put movies details here")
                }
            }
        }
    }

    private fun setupUi() {
        lifecycleScope.launch {
            viewModel.homeUiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged().collect { uiState ->
                    when (uiState) {
                        is HomeUiState.Success -> updateUi(uiState)
                        HomeUiState.Loading -> {
                            // TODO()
                        }

                        HomeUiState.Error -> {
                            // TODO()
                        }
                    }
                }
        }
    }

    private fun updateUi(stateUi: HomeUiState.Success) {
        supportActionBar?.title = stateUi.selectedGenreName
        createGenreMenu(stateUi.genreList)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        viewBinding.apply {
            when {
                drawerLayout.isOpen -> {
                    drawerLayout.close()
                }

                else -> super.onBackPressed()
            }
        }
    }

    private fun createGenreMenu(genreList: List<GenreModel>) {
        val menu = viewBinding.navigationView.menu

        genreList.forEachIndexed { index, genreModel ->
            menu.addSubMenu(index + 1, genreModel.id, Menu.NONE, genreModel.name)
            menu.add(index + 1, genreModel.id, Menu.NONE, genreModel.name)
        }
    }

    private fun initiateNavigationListeners() {
        viewBinding.apply {
            navigationView.setNavigationItemSelectedListener { item ->
                viewModel.uiActions(UiAction.GenreSelected(item.itemId, item.title.toString()))
                drawerLayout.close()
                true
            }
        }
    }
}