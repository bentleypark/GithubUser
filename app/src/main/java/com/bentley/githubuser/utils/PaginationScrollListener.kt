/*
 * COPYRIGHT © SOVORO 2020 ALL RIGHTS RESERVED
 */

package com.bentley.githubuser.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Pagination class to add more items to the list when reach the last item.

 * Supporting only LinearLayoutManager for now.
 *
 * @param layoutManager
 */
abstract class PaginationScrollListener(private var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        val isScrollUp = dy > 0

        if (!isLoading() && !isLastPage() && isScrollUp) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
}