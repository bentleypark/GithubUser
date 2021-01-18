package com.bentley.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bentley.githubuser.databinding.ActivityMainBinding
import com.bentley.githubuser.ui.main.SectionsPagerAdapter
import com.bentley.githubuser.utils.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            val sectionsPagerAdapter = SectionsPagerAdapter(this@MainActivity, supportFragmentManager)
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }
}