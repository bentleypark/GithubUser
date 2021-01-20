package com.bentley.githubuser.ui.local

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bentley.githubuser.R
import com.bentley.githubuser.databinding.FragmentLocalBinding
import com.bentley.githubuser.domain.User
import com.bentley.githubuser.domain.state.DataState
import com.bentley.githubuser.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LocalFragment : Fragment() {

    private val viewModel: LocalViewModel by viewModels()
    private var binding: FragmentLocalBinding by viewLifecycle()
    private lateinit var favoriteUserListAdapter: FavoriteUserListAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObserve()
    }

    private fun setUpViews() {
        binding.apply {

            favoriteUserListAdapter = FavoriteUserListAdapter(mutableListOf(), viewModel)
            favoriteUserList.apply {
                adapter = favoriteUserListAdapter
                setHasFixedSize(true)
            }

            searchLayout.apply {

                btnSearch.setOnClickListener {
                    performSearch()
                }

                etSearch.apply {
                    setOnEditorActionListener { _, actionId, _ ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            performSearch()
                        }
                        true
                    }

                    addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            favoriteUserList.makeGone()
                            progressCircular.makeVisible()
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }
                    })
                }
            }
        }
    }

    private fun setUpObserve() {
        viewModel.apply {
            dataState.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is DataState.Success<List<User>> -> {
                        binding.progressCircular.makeGone()

                        if (result.data.isNotEmpty()) {
                            binding.favoriteUserList.makeVisible()
                            favoriteUserListAdapter.addAll(sortList(result.data))
                        } else {
                            makeToast(getString(R.string.no_search_result))
                        }
                    }
                    is DataState.Loading -> {
                        binding.apply {
                            favoriteUserList.makeGone()
                            progressCircular.makeVisible()
                        }
                    }
                    else -> {
                        binding.progressCircular.makeGone()
                        makeToast(result.toString())
                    }
                }
            })
        }
    }

    private fun sortList(original: List<User>): List<User> {
        val nameList = original.map { it.name }
            .sortedWith(OrderingByKoreanEnglishNumbuerSpecial.getComparator())

        val newList = mutableListOf<User>()

        nameList.forEach { name ->
            newList.add(original.first { it.name == name })
        }

        return newList
    }

    private fun performSearch() {
        binding.apply {
            val query = SpannableStringBuilder(searchLayout.etSearch.text).toString().trim()
            if (query.isNotEmpty()) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {

                    searchLayout.etSearch.apply {
                        clearFocus()
                        hideKeyboard()
                    }
                    viewModel.search(query)
                }
            } else {
                favoriteUserList.makeSnackBar(getString(R.string.no_search_query))
            }
        }
    }
}