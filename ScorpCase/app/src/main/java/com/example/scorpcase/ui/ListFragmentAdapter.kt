package com.example.scorpcase.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scorpcase.R
import com.example.scorpcase.data.models.Person
import com.example.scorpcase.databinding.PersonItemBinding

class ListFragmentAdapter :
    RecyclerView.Adapter<ListFragmentAdapter.PersonViewHolder>() {
    private val personList = ArrayList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemBinding = DataBindingUtil.inflate<PersonItemBinding>(
            LayoutInflater.from(parent.context), R.layout.person_item, parent, false)
        return PersonViewHolder(itemBinding)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int = personList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateOrderList(list: List<Person>) {
        val diffResult = DiffUtil.calculateDiff(OrdersDiffCallback(list, personList))
        personList.clear()
        personList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    inner class PersonViewHolder(private val binding: PersonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(per: Person) {
            with(binding) {
                person = per
                executePendingBindings()

            }
        }
    }

    inner class OrdersDiffCallback(private val newList: List<Person>,
                                   private val oldList: List<Person>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id


        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}

