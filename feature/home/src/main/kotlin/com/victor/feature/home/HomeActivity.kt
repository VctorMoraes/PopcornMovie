package com.victor.feature.home

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.core.common.base.BaseActivity
import com.victor.core.model.GenreModel
import com.victor.core.model.MovieModel
import com.victor.feature.home.adapter.MovieAdapter
import com.victor.feature.home.databinding.ActivityMainBinding
import com.victor.feature.home.fragment.MovieFragment
import com.victor.feature.home.viewmodel.HomeUiState
import com.victor.feature.home.viewmodel.MainViewModel
import com.victor.feature.home.viewmodel.UiAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter(this::openFragment) }

    private val fragment = MovieFragment()
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var genreSelected: GenreModel

    var clickedMovie: MovieModel? = null
    private var hasFragmentOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        genreSelected = GenreModel(0, getString(R.string.trending))

        setupView()
        setupUi()
        initiateNavigationListeners()
        setupRecyclerView()
        setupPaging()
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

    private fun setupRecyclerView() {
        viewBinding.movieRecyclerView.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = movieAdapter
        }
    }

    private fun setupPaging() {
        lifecycleScope.launch {
            viewModel.moviesPagingDataFlow.collectLatest(movieAdapter::submitData)
        }
    }

    private fun setupUi() {
        lifecycleScope.launch {
            viewModel.homeUiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { uiState ->
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

                hasFragmentOpen -> {
                    returnFromFragment()
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
                movieRecyclerView.smoothScrollToPosition(0)
                drawerLayout.close()
                true
            }
        }
    }

    private fun openFragment(movie: MovieModel) {
        viewBinding.movieFragmentContainer.isClickable = true

        clickedMovie = movie
        supportActionBar?.title = movie.title
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .add(fragment, movie.title)
            .replace(R.id.movie_fragment_container, fragment)
            .commit()

        setDrawerEnabled(false)
        hasFragmentOpen = true
    }

    private fun returnFromFragment() {
        viewBinding.movieFragmentContainer.isClickable = false

        supportActionBar?.title = genreSelected.name
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .remove(fragment)
            .commit()

        setDrawerEnabled(true)
        hasFragmentOpen = false
    }

    private fun setDrawerEnabled(enabled: Boolean) {
        val lockMode =
            if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        viewBinding.drawerLayout.apply {
            setDrawerLockMode(lockMode)
            toggle.isDrawerIndicatorEnabled = enabled
        }
    }
}