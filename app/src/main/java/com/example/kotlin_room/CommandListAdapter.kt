package com.example.kotlin_room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommandListAdapter internal constructor(context: Context) : RecyclerView.Adapter<CommandListAdapter.CmdViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var commands = emptyList<Command>() // Cached copy of words

    inner class CmdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cmdItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CmdViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return CmdViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CmdViewHolder, position: Int) {
        val current = commands[position]
        holder.cmdItemView.text = current.cmd
    }

    internal fun setCommands(commands: List<Command>) {
        this.commands = commands
        notifyDataSetChanged()
    }

    override fun getItemCount() = commands.size

}