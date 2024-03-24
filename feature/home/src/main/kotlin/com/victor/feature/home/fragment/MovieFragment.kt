package com.victor.feature.home.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.feature.home.HomeActivity
import com.victor.feature.home.R
import com.victor.feature.home.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale


// Start compose migration in here!!!
@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var viewBinding: FragmentMovieBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMovieBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        setupView()
    }

    private fun getYearFromDate(date: String): Int {
        val calendar = GregorianCalendar()
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        calendar.time = dateFormat.parse(date) ?: Date()
        return calendar.get(Calendar.YEAR)
    }

    private fun glideListener(): RequestListener<Drawable> = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            startPostponedEnterTransition()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            startPostponedEnterTransition()
            return false
        }
    }

    private fun setupView() {
        viewBinding?.apply {
            (activity as HomeActivity).clickedMovie?.let { movie ->
                movieTitle.text = movie.title
                movieYear.text = getYearFromDate(movie.releaseDate).toString()
                movieOverview.text = movie.overview

                Glide.with(root)
                    .load(IMAGE_BASE_URL + movie.posterPath)
                    .centerInside()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .listener(glideListener())
                    .into(moviePoster)
            }
        }
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val DATE_FORMAT = "yyyy-MM-dd"
    }
}