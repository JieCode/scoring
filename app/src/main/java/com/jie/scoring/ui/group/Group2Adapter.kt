package com.jie.scoring.ui.group

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jie.scoring.R
import com.jie.scoring.data.Member
import com.jie.scoring.databinding.ItemGroupBinding

/**
 * @CreateDate: 2024/8/30
 * @author: jingjie
 * @desc:
 */
class Group2Adapter(var context: Context, var lstMember: List<GroupItem>) : RecyclerView.Adapter<Group2Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group, parent, false))
    }

    override fun getItemCount(): Int {
        return if (lstMember.isEmpty()) 0 else lstMember.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < lstMember.size) {
            val item = lstMember[position]
            holder.binding.tvGroupName.text = "第${position + 1}组:"

            holder.binding.tvMember1.text = item.member1.username
            holder.binding.tvMember1.setTextColor(
                if (TextUtils.equals(item.member1.gender, "F")) {
                    context.getColor(R.color.color_90_red)
                } else {
                    context.getColor(R.color.color_90_blue)
                }
            )

            holder.binding.tvMember2.text = item.member2.username
            holder.binding.tvMember2.setTextColor(
                if (TextUtils.equals(item.member2.gender, "F")) {
                    context.getColor(R.color.color_90_red)
                } else {
                    context.getColor(R.color.color_90_blue)
                }
            )
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemGroupBinding

        init {
            binding = ItemGroupBinding.bind(itemView)
        }
    }

    class GroupItem {
        lateinit var member1: Member
        lateinit var member2: Member
    }
}