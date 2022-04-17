package com.victor.popcornmovie.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.popcornmovie.R
import com.victor.popcornmovie.databinding.ActivityMainBinding
import com.victor.popcornmovie.model.GenreModel
import com.victor.popcornmovie.model.MovieModel
import com.victor.popcornmovie.view.base.BaseActivity
import com.victor.popcornmovie.view.main.adapter.MovieAdapter
import com.victor.popcornmovie.view.main.fragment.MovieFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter(this::openFragment) }
    private val fragment = MovieFragment()
    private lateinit var toggle: ActionBarDrawerToggle

    private var genreSelected: GenreModel = GenreModel(0, "Trending")

    var loadMore = false
    var clickedMovie: MovieModel? = null
    var hasFragmentOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupView()
        initiateObservers()
        initiateNavigationListeners()
        setupRecyclerView()
        viewModel.loadGenres()
        viewModel.loadMovies()
    }

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

    private fun initiateObservers() {
        observe(viewModel.genreList) {
            createGenreMenu(it)
        }
        observe(viewModel.movieList) {
            movieAdapter.configure(it, loadMore)
        }
    }

    private fun createGenreMenu(genreList: ArrayList<GenreModel>) {
        val menu = viewBinding.navigationView.menu

        genreList.forEachIndexed { index, genreModel ->
            menu.addSubMenu(index + 1, genreModel.id, genreModel.id, genreModel.name)
            menu.add(index + 1, genreModel.id, genreModel.id, genreModel.name)
        }
    }

    private fun setupRecyclerView() {
        viewBinding.movieRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = movieAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val manager = layoutManager as GridLayoutManager

                    if (manager.findLastCompletelyVisibleItemPosition() == (adapter as MovieAdapter).itemCount - 1) {
                        loadMore = true
                        viewModel.loadMovies(genreSelected.id, loadMore)
                    }
                }
            })
        }
    }

    private fun setupView() {
        viewBinding.apply {
            setSupportActionBar(toolbar.appBar)
            supportActionBar?.title = genreSelected.name

            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                toolbar.appBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    private fun initiateNavigationListeners() {
        viewBinding.apply {
            navigationView.setNavigationItemSelectedListener { item ->
                genreSelected = GenreModel(item.itemId, item.title.toString())
                loadMore = false

                supportActionBar?.title = genreSelected.name
                viewModel.loadMovies(genreSelected.id)

                val linearManager = movieRecyclerView.layoutManager
                linearManager?.smoothScrollToPosition(movieRecyclerView, RecyclerView.State(), 0)
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