package com.bentley.githubuser.ui.local

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bentley.githubuser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalFragment : Fragment() {

    companion object {
        fun newInstance() = LocalFragment()
    }

    private lateinit var viewModel: LocalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LocalViewModel::class.java)
        // TODO: Use the ViewModel
    }

}