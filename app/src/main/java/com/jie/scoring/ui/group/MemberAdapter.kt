package com.jie.scoring.ui.group

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.text.LineBreaker
import android.os.Build
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jie.scoring.R
import com.jie.scoring.data.Member
import com.jie.scoring.databinding.ItemMemberBinding

/**
 * @CreateDate: 2024/9/3
 * @author: jingjie
 * @desc:
 */
class MemberAdapter(
    private val mContext: Context, private val mList: MutableList<Member>,
    private val mSelectedList: MutableList<Member>, private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_member, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val member = mList[position]
        holder.binding.tvName.text = member.username
        if (member.gender == "F") {
            if (mSelectedList.contains(member)) {
                holder.binding.tvName.setBackgroundResource(R.drawable.shape_radius_red90)
                holder.binding.tvName.setTextColor(mContext.getColor(R.color.white))
            } else {
                holder.binding.tvName.setBackgroundResource(R.drawable.shape_radius_red90_stroke)
                holder.binding.tvName.setTextColor(mContext.getColor(R.color.color_90_red))
            }
        } else if (member.gender == "M") {
            if (mSelectedList.contains(member)) {
                holder.binding.tvName.setBackgroundResource(R.drawable.shape_radius_blue90)
                holder.binding.tvName.setTextColor(mContext.getColor(R.color.white))
            } else {
                holder.binding.tvName.setBackgroundResource(R.drawable.shape_radius_blue90_stroke)
                holder.binding.tvName.setTextColor(mContext.getColor(R.color.color_90_blue))
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickListener.onChoose(position, member)
        }
        holder.itemView.setOnLongClickListener {
            onItemClickListener.deleteMember(position, member)
            true
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemMemberBinding

        init {
            binding = ItemMemberBinding.bind(itemView)
        }
    }

    interface OnItemClickListener {
        fun onChoose(position: Int, member: Member)
        fun deleteMember(position: Int, member: Member)
    }
}