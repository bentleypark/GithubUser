package com.bentley.githubuser.ui.api

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bentley.githubuser.R
import com.bentley.githubuser.databinding.FragmentApiBinding
import com.bentley.githubuser.domain.User
import com.bentley.githubuser.domain.state.DataState
import com.bentley.githubuser.ui.BaseFragment
import com.bentley.githubuser.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApiFragment : BaseFragment<FragmentApiBinding>() {

    private val viewModel: ApiFragmentViewModel by viewModels()
    private lateinit var userListAdapter: SearchUserListAdapter
    private var isLastPage = false
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObserve()
    }

    private fun setUpViews() {
        binding.apply {

            userListAdapter = SearchUserListAdapter(mutableListOf(), viewModel).also {
                it.setHasStableIds(true)
            }
            searchUserList.apply {
                adapter = userListAdapter
                setHasFixedSize(true)

                /**
                 * Paging 처리 하기 위해서 scrollLister 추가
                 */
                addOnScrollListener(object :
                    PaginationScrollListener(this.layoutManager as LinearLayoutManager) {
                    override fun isLastPage(): Boolean {
                        return isLastPage
                    }

                    override fun isLoading(): Boolean {
                        return isLoading
                    }

                    override fun loadMoreItems() {
                        isLoading = true
                        viewModel.fetchNextPage()
                    }
                })
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
                            searchUserList.makeGone()
                            progressCircular.makeVisible()
                            userListAdapter.addAll(mutableListOf())
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }
                    })
                }

                btnSearch.setOnClickListener {
                    performSearch()
                }
            }
        }
    }

    private fun setUpObserve() {
        viewModel.apply {
            userList.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is DataState.Success<List<User>> -> {
                        binding.apply {
                            progressCircular.makeGone()
                            binding.searchUserList.makeVisible()
                        }
                        if (result.data.isNotEmpty()) {
                            userListAdapter.addAll(result.data)
                        }
                    }
                    is DataState.Loading -> {
                        binding.apply {
                            searchUserList.makeGone()
                            progressCircular.makeVisible()
                        }
                    }
                    else -> {
                        binding.progressCircular.makeGone()
                        makeToast(result.toString())
                    }
                }
            })

            nextUserList.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is DataState.Success<List<User>> -> {
                        binding.progressCircular.makeGone()

                        if (result.data.isNotEmpty()) {
                            userListAdapter.add(result.data)
                            isLoading = false
                        } else {
                            isLastPage = true
                        }
                    }
                    else -> {
                        binding.progressCircular.makeVisible()
                    }
                }
            })
        }
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
                    viewModel.searchUsers(query)
                }
            } else {
                searchUserList.makeSnackBar(getString(R.string.no_search_query))
            }
        }
    }
}