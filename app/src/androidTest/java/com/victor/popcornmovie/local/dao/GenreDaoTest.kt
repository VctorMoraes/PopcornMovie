package com.victor.popcornmovie.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.victor.core.network.database.PopcornMovieDatabase
import com.victor.data.genres.datasource.local.entities.Genre
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.*
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class GenreDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_database")
    lateinit var database: com.victor.core.network.database.PopcornMovieDatabase

    private lateinit var genreDao: com.victor.core.database.dao.GenreDao

    @Before
    fun setup() {
        hiltRule.inject()
        genreDao = database.genreDao()
    }

    @Test
    fun insertAll() = runTest {
        val genre = com.victor.data.genres.datasource.local.entities.Genre(
            id = 1,
            name = "Action"
        )
        genreDao.insertAll(genre)
        val genres = genreDao.getAll()
        Assert.assertEquals(genres.contains(genre), true)
    }

    @After
    fun tearDown() {
        database.close()
    }
}