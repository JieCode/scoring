package com.jie.scoring.ui.group

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.WindowManager
import com.jie.scoring.R
import com.jie.scoring.data.Member
import com.jie.scoring.databinding.DialogAddMemberBinding

/**
 * @CreateDate: 2024/9/3
 * @author: jingjie
 * @desc:
 */
class AddMemberDialog {
    companion object {
        fun showDialog(mContext: Context, onAddMemberListener: OnAddMemberListener) {
            var view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_member, null)
            var dialog = Dialog(mContext, R.style.dialog)
            var binding = DialogAddMemberBinding.bind(view)
            // 设置 Dialog 的宽度
            dialog.window?.setLayout(
                Resources.getSystem().displayMetrics.widthPixels / 2, // 调整宽度为屏幕的一半
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.setContentView(view)
            dialog.show()
            binding.tvAdd.setOnClickListener {
                dialog.dismiss()
                val member = Member(username = "", gender = "U")
                member.username = binding.etName.text.toString()
                if (binding.rgSex.checkedRadioButtonId == R.id.rb_female) {
                    member.gender = "F"
                } else {
                    member.gender = "M"
                }
                onAddMemberListener.onAddMember(member)
            }
            binding.ivBack.setOnClickListener {
                dialog.dismiss()
            }
        }

        fun showDialog(mContext: Context, member: Member, onAddMemberListener: OnAddMemberListener) {
            var view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_member, null)
            var dialog = Dialog(mContext, R.style.dialog)
            var binding = DialogAddMemberBinding.bind(view)
            // 设置 Dialog 的宽度
            dialog.window?.setLayout(
                Resources.getSystem().displayMetrics.widthPixels / 2, // 调整宽度为屏幕的一半
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.setContentView(view)
            dialog.show()
            binding.etName.setText(member.username)
            if (member.gender == "F") {
                binding.rbFemale.isChecked = true
            } else {
                binding.rbMale.isChecked = true
            }
            binding.tvAdd.text = "修改"
            binding.tvAdd.setOnClickListener {
                dialog.dismiss()
                member.username = binding.etName.text.toString()
                if (binding.rgSex.checkedRadioButtonId == R.id.rb_female) {
                    member.gender = "F"
                } else {
                    member.gender = "M"
                }
                onAddMemberListener.onAddMember(member)
            }
            binding.ivBack.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    interface OnAddMemberListener {
        fun onAddMember(member: Member)
    }
}