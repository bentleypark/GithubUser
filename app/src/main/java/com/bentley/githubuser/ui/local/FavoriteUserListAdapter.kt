package com.bentley.githubuser.ui.local

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bentley.githubuser.databinding.ItemLocalUserListBinding
import com.bentley.githubuser.domain.User
import com.bentley.githubuser.utils.CharUtil

class FavoriteUserListAdapter(
    private val userList: MutableList<User>,
    private val viewModel: LocalViewModel
) : RecyclerView.Adapter<FavoriteUserListAdapter.FavoriteUserListViewHolder>() {

    private lateinit var binding: ItemLocalUserListBinding

    inner class FavoriteUserListViewHolder(binding: ItemLocalUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.apply {
                tvName.text = item.name
                /**
                 *  사용자 이름의 추성 가져오는 방법
                 *  1) 한국어 일 경우: CharUtil.getInitialSound 함수를 호출하여 초성값 획득
                 *  2) 나머지: 문자열의 첫번째 문자값으로 반환
                 */
                tvFirstChar.text = if (CharUtil.isKorean(item.name[0])) {
                    CharUtil.getInitialSound(item.name)
                } else {
                    item.name[0].toString()
                }
                userItem.setOnClickListener {
                    viewModel.delete(item)
                    delete(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemLocalUserListBinding.inflate(layoutInflater)
        return FavoriteUserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserListViewHolder, position: Int) {
        val item = userList[position]
        holder.bind(item)
    }

    override fun getItemCount() = userList.size

    fun addAll(newList: List<User>) {
        userList.clear()
        userList.addAll(newList)
        notifyDataSetChanged()
    }

    fun delete(item: User) {
        val position = userList.indexOf(item)
        userList.remove(item)
        notifyItemRemoved(position)
    }
}