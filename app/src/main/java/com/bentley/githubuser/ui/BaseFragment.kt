package com.bentley.githubuser.ui

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bentley.githubuser.utils.viewLifecycle
import kotlinx.coroutines.Job

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected var binding: T by viewLifecycle()
    protected var searchJob: Job? = null
}