package com.jie.scoring.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jie.scoring.R

/**
 * @CreateDate: 2024/8/30
 * @author: jingjie
 * @desc:
 */
class GroupAdapter(var context: Context, var lstMember: List<GroupItem>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group, parent, false))
    }

    override fun getItemCount(): Int {
        return if (lstMember.isEmpty()) 0 else lstMember.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < lstMember.size) {
            val item = lstMember[position]
            holder.tvGroupName?.text = "第${position + 1}组:"
            if (item.member1 > 0) {
                holder.tvMember1?.text = item.member1.toString()
            } else {
                holder.tvMember1?.text = ""
            }
            if (item.member2 > 0) {
                holder.tvMember2?.text = item.member2.toString()
            } else {
                holder.tvMember2?.text = ""
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvGroupName: TextView? = null
        var tvMember1: TextView? = null
        var tvMember2: TextView? = null

        init {
            tvGroupName = itemView.findViewById(R.id.tv_group_name)
            tvMember1 = itemView.findViewById(R.id.tv_member1)
            tvMember2 = itemView.findViewById(R.id.tv_member2)
        }
    }

    class GroupItem {
        var member1: Int = 0
        var member2: Int = 0
    }
}