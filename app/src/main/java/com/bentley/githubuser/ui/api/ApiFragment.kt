package com.bentley.githubuser.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bentley.githubuser.R
import com.bentley.githubuser.databinding.FragmentApiBinding
import com.bentley.githubuser.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiFragment : Fragment() {

    private val viewModel: ApiFragmentViewModel by viewModels()
    private var binding: FragmentApiBinding by viewLifecycle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchUsers()
    }
}