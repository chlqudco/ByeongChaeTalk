package com.chlqudco.develop.byeongchaetalk.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chlqudco.develop.byeongchaetalk.databinding.ItemUserBinding
import com.chlqudco.develop.byeongchaetalk.domain.model.UserModel

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>(){

    private var userList: List<UserModel> = listOf()

    inner class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: UserModel){
            binding.itemUserTextView.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(userList: List<UserModel>){
        this.userList = userList
        notifyDataSetChanged()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.user_id == newItem.user_id
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}