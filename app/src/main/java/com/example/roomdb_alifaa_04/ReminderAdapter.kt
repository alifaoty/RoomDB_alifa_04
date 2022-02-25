package com.example.roomdb_alifaa_04

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb_alifaa_04.room.Reminder
import kotlinx.android.synthetic.main.list_reminder.view.*

class ReminderAdapter (private val reminders: ArrayList<Reminder>, private val listener : OnAdapterListener) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_reminder, parent, false)
        )
    }

    override fun getItemCount() = reminders.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.view.text_title.text = reminder.title
        holder.view.text_title.setOnClickListener {
            listener.onClick(reminder)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(reminder)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(reminder)
        }
    }

    class ReminderViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Reminder>){
        reminders.clear()
        reminders.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(reminder: Reminder)
        fun onUpdate(reminder: Reminder)
        fun onDelete(reminder: Reminder)
    }
}