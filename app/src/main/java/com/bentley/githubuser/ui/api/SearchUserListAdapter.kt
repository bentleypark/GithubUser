package com.bentley.githubuser.ui.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bentley.githubuser.databinding.ItemApiUserListBinding
import com.bentley.githubuser.domain.User

class SearchUserListAdapter(
    private val userList: MutableList<User>,
    private val viewModel: ApiFragmentViewModel
) : RecyclerView.Adapter<SearchUserListAdapter.SearchUserListViewHolder>() {

    private lateinit var binding: ItemApiUserListBinding

    inner class SearchUserListViewHolder(private val binding: ItemApiUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.apply {
                tvName.text = item.name
                btnFavorite.isSelected = item.isFavorite
                userItem.setOnClickListener {
                    if (!btnFavorite.isSelected) {
                        viewModel.insert(item)
                    } else {
                        viewModel.delete(item)
                    }
                    btnFavorite.isSelected = !btnFavorite.isSelected
                }
                btnFavorite.setOnClickListener {
                    if (!btnFavorite.isSelected) {
                        viewModel.insert(item)
                    } else {
                        viewModel.delete(item)
                    }
                    btnFavorite.isSelected = !btnFavorite.isSelected
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemApiUserListBinding.inflate(layoutInflater)
        return SearchUserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchUserListViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount() = userList.size

    override fun getItemId(position: Int) = userList[position].id.toLong()

    fun addAll(newList: List<User>) {
        userList.clear()
        userList.addAll(newList)
        notifyDataSetChanged()
    }

    fun add(newList: List<User>) {
        userList.addAll(newList)
        notifyDataSetChanged()
    }
}
