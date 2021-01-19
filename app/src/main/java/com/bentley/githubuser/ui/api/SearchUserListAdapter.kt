package com.bentley.githubuser.ui.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
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
//                ivProfile.load(item.profileUrl){
//                    transformations(CircleCropTransformation())
//                }
                userItem.setOnClickListener {
//                    viewModel.insert()
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

    fun addAll(newList: List<User>) {
        userList.clear()
        userList.addAll(newList)
        notifyDataSetChanged()
    }
}
