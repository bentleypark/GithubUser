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
import com.bentley.githubuser.domain.User
import com.bentley.githubuser.domain.state.DataState
import com.bentley.githubuser.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiFragment : Fragment() {

    private val viewModel: ApiFragmentViewModel by viewModels()
    private var binding: FragmentApiBinding by viewLifecycle()
    private lateinit var userListAdapter: SearchUserListAdapter

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
        setUpViews()
        setUpObserve()
    }

    private fun setUpViews() {
        binding.apply {

            userListAdapter = SearchUserListAdapter(mutableListOf(), viewModel)
            searchUserList.apply {
                adapter = userListAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun setUpObserve() {
        viewModel.apply {
            userList.observe(viewLifecycleOwner, { result ->

                when (result) {
                    is DataState.Success<List<User>> -> {
                        if (result.data.isNotEmpty()) {
                            userListAdapter.addAll(result.data)
                        }
                    }
                    is DataState.Loading -> {

                    }
                    else -> {

                    }
                }
            })
        }
    }
}