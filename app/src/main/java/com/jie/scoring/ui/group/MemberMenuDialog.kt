package com.jie.scoring.ui.group

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.jie.scoring.R
import com.jie.scoring.data.Member
import com.jie.scoring.databinding.DialogMemberMenuBinding

/**
 * @CreateDate: 2024/9/5
 * @author: jingjie
 * @desc: 修改、删除成员对话框
 */
class MemberMenuDialog {
    companion object{
        fun createDialog(mContext: Context, member: Member, listener: OnMemberMenuClickListener): Dialog{
            val dialog= Dialog(mContext, R.style.dialog)
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_menu, null)
            val binding = DialogMemberMenuBinding.bind(view)
            dialog.setContentView(view)
            binding.tvMemberModify.setOnClickListener {
                dialog.dismiss()
                listener.onModifyClick(member)
            }
            binding.tvMemberDelete.setOnClickListener {
                dialog.dismiss()
                listener.onDeleteClick(member)
            }
            return dialog
        }
    }

    interface OnMemberMenuClickListener{
        fun onModifyClick(member: Member)
        fun onDeleteClick(member: Member)
    }
}