package com.taufiqarinta.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.taufiqarinta.githubuser.R
import com.taufiqarinta.githubuser.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityDetailUserBinding
    private  lateinit var viewModel: DetailViewModel
    private lateinit var progressBar: ProgressBar

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        if (username != null) {
            viewModel.setUserDetail(username)
            showLoading(true)
        }

        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvNameProfile.text = it.name
                    tvUsernameProfile.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
                showLoading(false)
            }

        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.saklarfavorite.isChecked = true
                        _isChecked = true
                    }else {
                        binding.saklarfavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.saklarfavorite.setOnClickListener {
            _isChecked = !_isChecked
            if(_isChecked){
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addFavorite(username,id, avatarUrl)
                    }
                }

            }else {
                viewModel.removedFromFavorite(id)
            }
            binding.saklarfavorite.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(state : Boolean){
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }
}