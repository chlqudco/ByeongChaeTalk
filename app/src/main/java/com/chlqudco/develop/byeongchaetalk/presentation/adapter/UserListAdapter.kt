package com.chlqudco.develop.byeongchaetalk.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chlqudco.develop.byeongchaetalk.databinding.ItemUserBinding
import com.chlqudco.develop.byeongchaetalk.domain.model.UserModel

class UserListAdapter(val clickListener: (UserModel) -> Unit) : RecyclerView.Adapter<UserListAdapter.ViewHolder>(){

    private var userList: List<UserModel> = listOf()

    inner class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: UserModel){

            binding.itemUserTextView.text = user.name

            binding.root.setOnClickListener {
                clickListener(user)
            }
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

}