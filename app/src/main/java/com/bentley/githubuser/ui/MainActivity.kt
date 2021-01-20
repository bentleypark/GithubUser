package com.bentley.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.bentley.githubuser.R
import com.bentley.githubuser.databinding.ActivityMainBinding
import com.bentley.githubuser.utils.viewBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        binding.apply {
            val navController = findNavController(R.id.nav_host_fragment)
            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        FRAGMENT_API_INDEX -> {
                            navController.navigate(R.id.apiFragment, null,
                                // fragment backStack 관리를 위해서 navOptions 추가
                                navOptions {
                                    popUpTo = R.id.apiFragment
                                    launchSingleTop = true
                                }
                            )
                        }
                        FRAGMENT_LOCAL_INDEX -> {
                            navController.navigate(R.id.localFragment, null,
                                navOptions {
                                    popUpTo = R.id.localFragment
                                    launchSingleTop = true
                                })
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            })
        }
    }

    companion object {
        private const val FRAGMENT_API_INDEX = 0
        private const val FRAGMENT_LOCAL_INDEX = 1
    }
}