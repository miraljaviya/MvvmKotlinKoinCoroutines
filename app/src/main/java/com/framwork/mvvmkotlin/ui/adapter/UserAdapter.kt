package com.framwork.mvvmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.framwork.mvvmkotlin.R
import com.framwork.mvvmkotlin.databinding.RawUserBinding
import com.framwork.mvvmkotlin.rest.voResponse.Datum
import com.squareup.picasso.Picasso

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var data: List<Datum>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RawUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.raw_user, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (::data.isInitialized) data.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {

        }
    }

    class ViewHolder(private val binding: RawUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(datum: Datum) {
            binding.rawTextViewName.text = datum.first_name+" "+datum.last_name
            binding.rawTextViewEmail.text = datum.email
            Picasso.get().load(datum.avatar).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(binding.rawImage)
        }
    }

    fun updateUserList(userList: List<Datum>) {
        this.data = userList
        notifyDataSetChanged()
    }
}