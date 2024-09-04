package com.jie.scoring.ui.group

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.jie.scoring.R
import com.jie.scoring.data.Member
import com.jie.scoring.data.MemberRoomDatabase
import com.jie.scoring.data.MemberViewModel
import com.jie.scoring.databinding.DialogDeleteMemberBinding

/**
 * @CreateDate: 2024/7/8
 * @author: jingjie
 * @desc:
 */
class DeleteMemberDialog {
    companion object {
        fun createDialog(mContext: Context, member: Member, onMemberDeleteListener: OnDeleteMemberListener): Dialog {
            var view = LayoutInflater.from(mContext).inflate(R.layout.dialog_delete_member, null)
            var dialog = Dialog(mContext, R.style.dialog)
            var binding = DialogDeleteMemberBinding.bind(view)
            binding.tvTips.text = "确定删除${member.username}吗？"
            binding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            binding.btnSubmit.setOnClickListener {
                dialog.dismiss()
                val viewModel: MemberViewModel by lazy { MemberViewModel(MemberRoomDatabase.getDatabase(mContext).memberDao()) }
                viewModel.deleteMember(member)
                onMemberDeleteListener.onMemberDelete()
            }
            dialog.setContentView(view)
            return dialog
        }
    }

    interface OnDeleteMemberListener {
        fun onMemberDelete()
    }
}