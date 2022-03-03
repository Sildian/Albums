package com.sildian.apps.albums.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sildian.apps.albums.R
import com.sildian.apps.albums.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/*************************************************************************************************
 * Main Activity used in the app
 ************************************************************************************************/

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
        initUI()
        if (savedInstanceState == null) {
            showFragment()
        }
    }

    private fun initUI() {
        setSupportActionBar(this.binding.activityMainToolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    private fun showFragment() {
        supportFragmentManager.beginTransaction()
            .replace(this.binding.activityMainContainerFragment.id, AlbumsFragment())
            .commit()
    }
}